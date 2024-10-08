package com.example.contactdefinitive

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.contactdefinitive.databinding.ActivitySecondBinding

class ListContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private lateinit var contactList: List<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getExtras()
        setupListeners()
        setupView()
    }

    private fun getExtras() {
        val bundle = intent.getBundleExtra("bundle")
        contactList = bundle?.getParcelableArrayList<Contact>("contact")?.toList() ?: emptyList()
    }

    private fun setupListeners() {
        binding.buttonReturn.setOnClickListener {
            finish()
        }
    }

    private fun setupView() {

        with(binding) {
            for ((index, contact) in contactList.withIndex()) {

                when (index) {
                    0 -> {
                        bar1.visibility = View.VISIBLE
                        nameContact1.text = contact.name
                        phone1.text = contact.num
                    }
                    1 -> {
                        bar2.visibility = View.VISIBLE
                        nameContact2.text = contact.name
                        phone2.text = contact.num
                    }
                    2 -> {
                        bar3.visibility = View.VISIBLE
                        nameContact3.text = contact.name
                        phone3.text = contact.num
                    }
                }
            }
        }
    }
}

