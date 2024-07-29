package com.example.contactdefinitive

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.contactdefinitive.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val contatoList: MutableList<Contact> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupListener()
    }

    private fun setupListener() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSalvarContato.setOnClickListener {
            if (binding.editNome.text.toString() == "" || binding.editTelefone.text.toString() == "") {
                Toast.makeText(this, getString(R.string.null_fields_toast), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (contatoList.size >= 3) {
                Toast.makeText(this, getString(R.string.full_fields_toast), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            contatoList.add(Contact(
                    binding.editNome.text.toString(),
                    binding.editTelefone.text.toString()
                )
            )
            clearEditText()
            Toast.makeText(this, getString(R.string.save_fields_toast), Toast.LENGTH_SHORT).show()

        }

        binding.buttonLimparContatos.setOnClickListener {
            contatoList.removeAll(contatoList)
            Toast.makeText(this, getString(R.string.null_list_toast), Toast.LENGTH_SHORT).show()
        }

        binding.buttonListaContatos.setOnClickListener {
            openSecondActivity()
        }
    }

    fun openSecondActivity() {
        val intent = Intent(this, ContactListActivity::class.java).apply {
            val bundle = Bundle().apply {
                putParcelableArrayList("contact", contatoList.toCollection(ArrayList()))

            }
            putExtra("bundle", bundle)
        }
        startActivity(intent)
    }

    fun clearEditText() {
        with(binding) {
            editNome.text.clear()
            editTelefone.text.clear()
        }
    }
}