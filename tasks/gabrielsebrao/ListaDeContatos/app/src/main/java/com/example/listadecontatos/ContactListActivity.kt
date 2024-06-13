package com.example.listadecontatos

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import com.example.listadecontatos.databinding.ContactListActivityBinding

private lateinit var binding: ContactListActivityBinding

class ContactListActivity : ComponentActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContactListActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bundle = intent.getBundleExtra("bundle")
        val contactList = bundle?.getParcelableArrayList<Contact>("contactlist")?.toList()

        when(contactList?.size) {
            1 -> {
                binding.contact1.visibility = View.VISIBLE
                binding.contact1.text = contactList[0].name + '\n' + contactList[0].phone
            }
            2 -> {
                binding.contact1.visibility = View.VISIBLE
                binding.contact1.text = contactList[0].name + '\n' + contactList[0].phone

                binding.contact2.visibility = View.VISIBLE
                binding.contact2.text = contactList[1].name + '\n' + contactList[1].phone
            }
            3 -> {
                binding.contact1.visibility = View.VISIBLE
                binding.contact1.text = contactList[0].name + '\n' + contactList[0].phone

                binding.contact2.visibility = View.VISIBLE
                binding.contact2.text = contactList[1].name + '\n' + contactList[1].phone

                binding.contact3.visibility = View.VISIBLE
                binding.contact3.text = contactList[2].name + '\n' + contactList[2].phone
            }
        }
    }

}