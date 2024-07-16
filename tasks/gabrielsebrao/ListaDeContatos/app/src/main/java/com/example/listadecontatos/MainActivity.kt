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
        val name = binding.editTextName.text
        val phone = binding.editTextPhone.text

        Log.d("debug", "saveContact()")

        if (name.isNotEmpty() && phone.isNotEmpty()) {
            if (contactsList.size == 3)
                Toast.makeText(this, R.string.exceedThreeContacts, Toast.LENGTH_SHORT).show()
            else {
                contactsList.add(Contact(name.toString(), phone.toString()))
                Toast.makeText(this, R.string.addContact, Toast.LENGTH_SHORT).show()
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