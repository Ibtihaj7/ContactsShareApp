package com.example.contactsshareapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.example.contactsshareapp.fragment.AllContactsFragment
import com.example.contactsshareapp.model.UserInformation
import java.util.regex.Pattern

class AddNewContact : AppCompatActivity() {
    private lateinit var saveBtn : Button
    private lateinit var firstName : EditText
    private lateinit var lastName : EditText
    private lateinit var phoneNumber : EditText
    private lateinit var toolbar : Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_contact)

        saveBtn = findViewById(R.id.saveButton)
        firstName = findViewById(R.id.firstNameEditText)
        lastName = findViewById(R.id.lastNameEditText)
        phoneNumber = findViewById(R.id.phoneNumberEditText)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        saveBtn.setOnClickListener {addNewContact()}
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
            onBackPressed()
        }
    }

    private fun validateInput(): Boolean {

        return true
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