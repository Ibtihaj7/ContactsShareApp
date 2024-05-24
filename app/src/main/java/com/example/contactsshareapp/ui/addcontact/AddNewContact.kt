package com.example.contactsshareapp.ui.addcontact

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.contactsshareapp.databinding.ActivityAddNewContactBinding
import com.example.contactsshareapp.ui.SharedViewModel
import com.example.contactsshareapp.ui.allcontact.AllContactsFragment
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddNewContact : AppCompatActivity() {
    private var contactAddedListener: AllContactsFragment.ContactAddedListener? = AllContactsFragment.contactAddedListener
    private lateinit var binding: ActivityAddNewContactBinding
    private val viewModel: AddNewContactViewModel by viewModels()
    private lateinit var sharedViewModel: SharedViewModel

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var gson: Gson

    companion object {
        const val MY_CONTACTS_KEY = "MyContacts"
        const val ERROR_FIRST_NAME_REQUIRED = "First Name is required"
        const val ERROR_USER_EXISTS = "User with the same name already exists"
        const val ERROR_LAST_NAME_REQUIRED = "Last Name is required"
        const val ERROR_PHONE_NUMBER_REQUIRED = "Phone Number is required"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        setupSaveButton()
    }

    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {
            addNewContact()
        }
    }

    private fun addNewContact() {
        val firstName = binding.firstNameEditText.text.toString()
        val lastName = binding.lastNameEditText.text.toString()
        val phoneNumber = binding.phoneNumberEditText.text.toString()

        val isValid = viewModel.validateInput(firstName, lastName, phoneNumber)

        if (isValid == true) {
            viewModel.addNewContact(firstName, lastName, phoneNumber)
            contactAddedListener?.onContactAdded()
//            setResult()
            onBackPressed()
        } else {
            handleValidationErrors(firstName, lastName, phoneNumber)
        }
    }

    private fun notifyContactAdded() {
        sharedViewModel.notifyContactAdded()
    }

    private fun handleValidationErrors(firstName: String, lastName: String, phoneNumber: String) {
        binding.firstNameTextInputLayout.error = if (firstName.isNullOrEmpty()) ERROR_FIRST_NAME_REQUIRED
        else if (viewModel.userExists(firstName, lastName)) ERROR_USER_EXISTS else null

        binding.lastNameTextInputLayout.error = if (lastName.isNullOrEmpty()) ERROR_LAST_NAME_REQUIRED else null
        binding.phoneNumberTextInputLayout.error = if (phoneNumber.isNullOrEmpty()) ERROR_PHONE_NUMBER_REQUIRED else null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}