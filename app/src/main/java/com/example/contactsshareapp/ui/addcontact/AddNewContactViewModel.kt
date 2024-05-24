package com.example.contactsshareapp.ui.addcontact

import com.example.contactsshareapp.repo.DataRepo
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.contactsshareapp.model.UserInformation
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddNewContactViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson,
    private val dataRepo: DataRepo
) : ViewModel() {

    private var userList: List<UserInformation> = dataRepo.getAllUsers()
    fun addNewContact(firstName: String, lastName: String, phoneNumber: String) {
        val userInformation = UserInformation(firstName, lastName, phoneNumber)
        dataRepo.addUser(userInformation)
        saveContactsToSharedPreferences(userList)
    }

    private fun saveContactsToSharedPreferences(userInformationList: List<UserInformation>) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val json = gson.toJson(userInformationList)

        editor.putString(AddNewContact.MY_CONTACTS_KEY, json)
        editor.apply()
    }

    fun validateInput(firstName: String, lastName: String, phoneNumber: String): Any {
        return !(firstName.isNullOrEmpty() ||
                lastName.isNullOrEmpty() ||
                phoneNumber.isNullOrEmpty() ||
                userExists(firstName,lastName))
    }

    fun userExists(firstName: String, lastName: String): Boolean {
        return userList.any { user ->
            user.getFirstName().equals(firstName, ignoreCase = true) &&
                    user.getLastName().equals(lastName, ignoreCase = true)
        }
    }
}