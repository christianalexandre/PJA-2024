package com.example.listadecontatos.contacts_screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.listadecontatos.Contact
import com.example.listadecontatos.R
import com.example.listadecontatos.home_screen.HomeScreenActivity
import com.example.listadecontatos.databinding.ActivityContactListBinding

class ContactListActivity : ComponentActivity() {

    private lateinit var binding: ActivityContactListBinding
    private lateinit var contactList: MutableList<Contact>

    companion object {
        private const val FIRST_CONTACT = 0
        private const val SECOND_CONTACT = 1
        private const val THIRD_CONTACT = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.dark_gray)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.dark_gray)
        binding = ActivityContactListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        contactList = mutableListOf()

        getExtras()
        
        setupView()
        setupListeners()

    }

    private fun setupView() {

        if(contactList.isEmpty()) {
            Toast.makeText(this, R.string.no_contact, Toast.LENGTH_SHORT).show()
            return
        }

        setupContactView(FIRST_CONTACT, binding.contact1, binding.nameContact1, binding.phoneContact1)
        if(contactList.size >= 2) setupContactView(SECOND_CONTACT, binding.contact2, binding.nameContact2, binding.phoneContact2)
        if(contactList.size >= 3) setupContactView(THIRD_CONTACT, binding.contact3, binding.nameContact3, binding.phoneContact3)

    }

    private fun setupContactView(contactPosition: Int, contactContainer: ConstraintLayout, contactName: TextView, contactPhone: TextView) {

        contactContainer.visibility = View.VISIBLE
        contactName.text = contactList[contactPosition].name
        contactPhone.text = contactList[contactPosition].phone

    }

    private fun setupListeners() {

        binding.buttonReturn.setOnClickListener { goToMainActivity() }

    }

    private fun getExtras() {

        val parcelableArrayList = intent.getParcelableArrayListExtra(resources.getString(R.string.bundle_contact_list), Contact::class.java) ?: return
        contactList = parcelableArrayList.toList() as MutableList<Contact>

    }

    private fun goToMainActivity() {

        startActivity(Intent(this, HomeScreenActivity::class.java).apply {
            putParcelableArrayListExtra(resources.getString(R.string.bundle_contact_list), contactList.toCollection(ArrayList()))
        })

    }

}
