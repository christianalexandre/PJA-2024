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
                Toast.makeText(
                    this,
                    "Você só pode adicionar até 3 contatos na sua lista!",
                    Toast.LENGTH_SHORT
                ).show()
            else {
                contactsList.add(Contact(name.toString(), phone.toString()))
                Toast.makeText(this, "Contato adicionado!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(
                this,
                "Por favor, insira todos os dados do contato para salvá-lo!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun clearContacts() {
        if (contactsList.isEmpty()) {
            Toast.makeText(this, "Os contatos já estão esvaziados!", Toast.LENGTH_SHORT).show()
        } else {
            contactsList.clear()
            Toast.makeText(this, "Lista de contatos limpa!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToContactList() {
        if (contactsList.isEmpty())
            Toast.makeText(this, "Por favor, adicionar pelo menos um contato!", Toast.LENGTH_SHORT)
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