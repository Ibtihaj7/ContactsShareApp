package com.example.contactsshareapp

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.contactsshareapp.fragment.AllContactsFragment
import com.example.contactsshareapp.model.UserInformation
import com.google.gson.Gson
import com.google.android.material.textfield.TextInputLayout

class AddNewContact : AppCompatActivity() {
    private lateinit var saveBtn: Button
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var toolbar: Toolbar

    private lateinit var firstNameLayout: TextInputLayout
    private lateinit var lastNameLayout: TextInputLayout
    private lateinit var phoneNumberLayout: TextInputLayout

    companion object {
        const val MY_CONTACTS_KEY = "MyContacts"
        const val ERROR_FIRST_NAME_REQUIRED = "First Name is required"
        const val ERROR_USER_EXISTS = "User with the same name already exists"
        const val ERROR_LAST_NAME_REQUIRED = "Last Name is required"
        const val ERROR_PHONE_NUMBER_REQUIRED = "Phone Number is required"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_contact)

        saveBtn = findViewById(R.id.saveButton)
        firstName = findViewById(R.id.firstNameEditText)
        lastName = findViewById(R.id.lastNameEditText)
        phoneNumber = findViewById(R.id.phoneNumberEditText)

        firstNameLayout = findViewById(R.id.firstNameTextInputLayout)
        lastNameLayout = findViewById(R.id.lastNameTextInputLayout)
        phoneNumberLayout = findViewById(R.id.phoneNumberTextInputLayout)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        saveBtn.setOnClickListener { addNewContact() }
    }

    private fun addNewContact() {
        if (validateInput()) {
            val userInformation = UserInformation(
                firstName.text.toString(),
                lastName.text.toString(),
                phoneNumber.text.toString()
            )
            AllContactsFragment.USER_INFO_LIST.add(userInformation)
            AllContactsFragment.ALL_CONTACTS_ADAPTER.notifyDataSetChanged()
            saveContactToSharedPreferences(userInformation)
            onBackPressed()
        }
    }

    private fun saveContactToSharedPreferences(userInformation: UserInformation) {
        val sharedPreferences = getSharedPreferences(MY_CONTACTS_KEY, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        val gson = Gson()
        val json = gson.toJson(userInformation)

        val timestamp = System.currentTimeMillis().toString()
        editor.putString(timestamp, json)
        editor.apply()
    }

    private fun validateInput(): Boolean {
        var isValid = true

        if (firstName.text.isNullOrEmpty()) {
            firstNameLayout.error = ERROR_FIRST_NAME_REQUIRED
            isValid = false
        } else {
            firstNameLayout.error = null

            val enteredFirstName = firstName.text.toString()
            val enteredLastName = lastName.text.toString()

            val userExists = AllContactsFragment.USER_INFO_LIST.any { user ->
                user.getFirstName().equals(enteredFirstName, ignoreCase = true) &&
                        user.getLastName().equals(enteredLastName, ignoreCase = true)
            }

            if (userExists) {
                firstNameLayout.error = ERROR_USER_EXISTS
                isValid = false
            }
        }

        if (lastName.text.isNullOrEmpty()) {
            lastNameLayout.error = ERROR_LAST_NAME_REQUIRED
            isValid = false
        } else {
            lastNameLayout.error = null
        }

        if (phoneNumber.text.isNullOrEmpty()) {
            phoneNumberLayout.error = ERROR_PHONE_NUMBER_REQUIRED
            isValid = false
        } else {
            phoneNumberLayout.error = null
        }
        return isValid
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
