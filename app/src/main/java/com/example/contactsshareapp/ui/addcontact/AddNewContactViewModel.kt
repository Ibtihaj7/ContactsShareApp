package com.example.contactsshareapp.ui.addcontact

import DataRepo
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.contactsshareapp.model.UserInformation
import com.example.contactsshareapp.ui.addcontact.AddNewContact
import com.example.contactsshareapp.ui.allcontact.AllContactsFragment
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddNewContactViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : ViewModel() {


    private var userList: ArrayList<UserInformation> = DataRepo.getAllUserList()

    fun addNewContact(
        firstName: String,
        lastName: String,
        phoneNumber: String
    ) {
        val userInformation = UserInformation(
            firstName,
            lastName,
            phoneNumber
        )
        userList.add(userInformation)
        saveContactsToSharedPreferences(userList)
    }

    private fun saveContactsToSharedPreferences(userInformationList: List<UserInformation>) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val json = gson.toJson(userInformationList)

        editor.putString(AddNewContact.MY_CONTACTS_KEY, json)
        editor.apply()
    }

    fun validateInput(firstName: String, lastName: String, phoneNumber: String): Any {
        if(firstName.isNullOrEmpty() || lastName.isNullOrEmpty() || phoneNumber.isNullOrEmpty())
            return false

        if (userExists(firstName,lastName)) {
           return false
        }
        return true
    }

    fun userExists(firstName: String, lastName: String): Boolean {
        return userList.any { user ->
            user.getFirstName().equals(firstName, ignoreCase = true) &&
                    user.getLastName().equals(lastName, ignoreCase = true)
        }
    }
}
