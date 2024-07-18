package com.example.listadecontatos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.listadecontatos.databinding.MainActivityBinding

class MainActivity : ComponentActivity() {

    private lateinit var binding: MainActivityBinding
    private var contactsList: MutableList<Contact> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getExtras()

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            buttonSave.setOnClickListener { saveContact() }
            buttonClear.setOnClickListener { clearContacts() }
            buttonList.setOnClickListener { goToContactList() }
        }
    }

    private fun saveContact() {
        val name = binding.editTextName.text.toString()
        val phone = binding.editTextPhone.text.toString()

        Log.d("debug", "saveContact()")

        if (name.isNotEmpty() && phone.isNotEmpty()) {
            if (contactsList.size >= 3) {
                Toast.makeText(this, R.string.exceedThreeContacts, Toast.LENGTH_SHORT).show()
            } else if (phone.length > 11) {
                Toast.makeText(this, R.string.exceedPhoneNumber, Toast.LENGTH_SHORT).show()
            } else if (name.length > 30) {
                Toast.makeText(this, R.string.exceedName, Toast.LENGTH_SHORT).show()
            }
            else {
                try {
                    phone.toBigInteger()
                    contactsList.add(Contact(name, phone))
                    Toast.makeText(this, R.string.addContact, Toast.LENGTH_SHORT).show()
                } catch (_: NumberFormatException) {
                    Toast.makeText(this, R.string.onlyNumbers, Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, R.string.setAllContacts, Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearContacts() {
        if (contactsList.isEmpty()) {
            Toast.makeText(this, R.string.alreadyEmptyContacts, Toast.LENGTH_SHORT).show()
        } else {
            contactsList.clear()
            Toast.makeText(this, R.string.emptyContacts, Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPhoneValidation(phoneNumber: String): Boolean {
        if (phoneNumber.length > 11) {
            Toast.makeText(this, R.string.exceedPhoneNumber, Toast.LENGTH_SHORT).show()
        } else {
            try {
                phoneNumber.toInt()
            } catch (_: NumberFormatException) {
                Toast.makeText(this, R.string.onlyNumbers, Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

    private fun getExtras() {
        val bundle = intent.getBundleExtra("bundle")

        if(bundle != null)
            contactsList = (bundle.getParcelableArrayList<Contact>("contactlist")?.toList() as MutableList<Contact>?)!!
    }

    private fun goToContactList() {
        if (contactsList.isEmpty())
            Toast.makeText(this, R.string.addAtLeastOneContact, Toast.LENGTH_SHORT)
                .show()
        else {
            val intent = Intent(this, ContactListActivity::class.java).apply {
                val bundle = Bundle().apply {
                    putParcelableArrayList("contactlist", contactsList.toCollection(ArrayList()))
                }
                putExtra("bundle", bundle)
            }
            startActivity(intent)
        }
    }
}