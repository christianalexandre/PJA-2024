package com.example.contactdefinitive

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contactdefinitive.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val contatoList: MutableList<Contato> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fun clearEditText() {
            binding.editNome?.text?.clear()
            binding.editTelefone?.text?.clear()
        }

        binding.buttonSalvarContato?.setOnClickListener {

            if (binding.editNome?.text.toString() == "" || binding.editTelefone?.text.toString() == "") {
                Toast.makeText(this, "Os campos não podem estar vazios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (contatoList.size >= 3) {
                Toast.makeText(this, "A lista de contato está cheia", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Toast.makeText(this, "Contato salvo com sucesso", Toast.LENGTH_SHORT).show()
            contatoList.add(
                Contato(
                    binding.editNome?.text.toString(),
                    binding.editTelefone?.text.toString()
                )
            )
            clearEditText()
        }

        binding.buttonLimparContatos?.setOnClickListener {
            contatoList.removeAll(contatoList)
            Toast.makeText(this, "A lista de conatato está vazia", Toast.LENGTH_SHORT).show()
        }

        fun openSecondActivity() {
            val intent = Intent(this, SecondActivity::class.java).apply {
                val bundle = Bundle().apply {
                    putParcelableArrayList("contact", contatoList.toCollection(ArrayList()))

                }
                putExtra("bundle", bundle)
            }
            startActivity(intent)
        }

        binding.buttonListaContatos?.setOnClickListener {
            openSecondActivity()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.apply {
            putParcelableArrayList("list", contatoList.toCollection(ArrayList()))
        }
        super.onSaveInstanceState(outState)
    }
}