package com.example.listadecontatos

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.activity.ComponentActivity
import com.example.listadecontatos.databinding.MainActivityBinding
import kotlin.math.log

private lateinit var binding: MainActivityBinding
private var contactsList: MutableList<Contact> = mutableListOf<Contact>()

class Contact(val name: Editable, val phone: Editable);

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onResume() {
        super.onResume()

        binding.buttonSave.setOnClickListener { saveContact() }
    }

    private fun saveContact() {
        val name = binding.editTextName.text
        val phone = binding.editTextPhone.text

        Log.d("debug", "saveContact()")

        if(name.isNotEmpty() || name.isNotEmpty()) {
            contactsList.add(Contact(name, phone))
            Log.d("debug", "Contato registrado!")
        }
    }
}