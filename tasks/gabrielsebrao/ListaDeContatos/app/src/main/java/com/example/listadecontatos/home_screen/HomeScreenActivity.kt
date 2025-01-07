package com.example.listadecontatos.home_screen

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.listadecontatos.Contact
import com.example.listadecontatos.contacts_screen.ContactListActivity
import com.example.listadecontatos.R
import com.example.listadecontatos.databinding.ActivityMainBinding

class HomeScreenActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var homeScreenViewModel: HomeScreenViewModel
    private lateinit var contactList: MutableList<Contact>

    companion object {
        private const val RESIDENTIAL_PHONE_NUMBER_LENGTH = 12
        private const val PERSONAL_PHONE_NUMBER_LENGTH = 13
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.dark_gray)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.dark_gray)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeScreenViewModel = ViewModelProvider(this)[HomeScreenViewModel::class.java]
        contactList = mutableListOf()

        getExtras()

        // FIX: When the user enters a contact and goes to the ContactList activity, then returns, the MutableList is unexpectedly converted to a SingletonList
        if(contactList::class.simpleName == resources.getString(R.string.singleton_list)) contactList = mutableListOf(contactList[0])

        setupListeners()

        // for some reason textWatcher doesn't work on first change, so i put the first change here. now all changes will be watched by textWatcher.
        binding.editTextName.setText("")
        binding.editTextPhone.setText("")

    }

    private fun getExtras() {

        val parcelableArrayList = intent.getParcelableArrayListExtra(resources.getString(R.string.bundle_contact_list), Contact::class.java) ?: return
        contactList = parcelableArrayList.toList() as MutableList<Contact>

    }

    private fun setupListeners() {

        setupButtonSaveListeners()
        setupButtonListListeners()
        setupButtonClearListeners()
        setupEditTextNameListeners()
        setupEditTextPhoneListeners()
        setupRootListeners()

    }

    private fun setupButtonSaveListeners() {

        binding.buttonSave.setOnClickListener {

            val name = binding.editTextName.text.toString()
            val phone = binding.editTextPhone.text.toString()

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, R.string.set_all_data, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (contactList.size >= 3) {
                Toast.makeText(this, R.string.exceed_three_contacts, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (phone.length != RESIDENTIAL_PHONE_NUMBER_LENGTH && phone.length != PERSONAL_PHONE_NUMBER_LENGTH) {
                Toast.makeText(this, R.string.not_complete_phone_number, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            saveContact(name, phone)

        }

    }

    private fun saveContact(name: String, phone: String) {

        contactList.add(Contact(name, phone))
        Toast.makeText(this, R.string.add_contact, Toast.LENGTH_SHORT).show()

    }

    private fun setupButtonListListeners() {

        binding.buttonList.setOnClickListener {

            if (contactList.isEmpty()) {
                Toast.makeText(this@HomeScreenActivity, R.string.add_at_least_one_contact, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            goToContactList()

        }

    }

    private fun setupButtonClearListeners() {

        binding.buttonClear.setOnClickListener {

            contactList.clear()
            Toast.makeText(this, R.string.empty_contacts, Toast.LENGTH_SHORT).show()

        }

    }

    private fun setupEditTextNameListeners() {

        binding.editTextName.addTextChangedListener( homeScreenViewModel.filterTextChangedForName(binding.editTextName) )

    }

    private fun setupEditTextPhoneListeners() {

        binding.editTextPhone.addTextChangedListener( homeScreenViewModel.filterTextChangedForTelephoneNumber(binding.editTextPhone) )
        binding.editTextPhone.addTextChangedListener( homeScreenViewModel.maskTextChangedForTelephoneNumber(binding.editTextPhone) )

    }

    private fun setupRootListeners() {

        binding.root.viewTreeObserver.addOnGlobalLayoutListener {

            val rect = Rect()
            binding.root.getWindowVisibleDisplayFrame(rect)

            val screenHeight = binding.root.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            val isKeyboardVisible = keypadHeight > (screenHeight * 0.15)

            if(!isKeyboardVisible) {
                binding.editTextName.clearFocus()
                binding.editTextPhone.clearFocus()
                return@addOnGlobalLayoutListener
            }

            if(!binding.editTextName.isFocused && !binding.editTextPhone.isFocused) binding.editTextName.requestFocus()
            if(!binding.editTextPhone.isFocused && !binding.editTextName.isFocused) binding.editTextPhone.requestFocus()

        }

    }

    private fun goToContactList() {

        startActivity(Intent(this, ContactListActivity::class.java).apply {
            putParcelableArrayListExtra(resources.getString(R.string.bundle_contact_list), contactList.toCollection(ArrayList()))
        })

    }
}