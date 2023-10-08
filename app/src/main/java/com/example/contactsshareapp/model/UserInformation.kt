package com.example.contactsshareapp.model

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.contactsshareapp.AddNewContact
import com.example.contactsshareapp.fragment.AllContactsFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserInformation(private var firstName:String, private var lastName:String, private var phoneNumber:String, private var isFavorite:Boolean = false) {
    fun getFirstName() : String = firstName
    fun setFirstName(firstName:String){
        this.firstName = firstName
    }

    fun getLastName() : String = lastName
    fun setLastName(lastName:String){
        this.lastName = lastName
    }

    fun getPhoneNumber() : String = phoneNumber
    fun setPhoneNumber(phoneNumber:String){
        this.phoneNumber = phoneNumber
    }

    fun getFavoriteState() : Boolean = isFavorite
    fun setFavoriteState(newState: Boolean, context: Context) {
        isFavorite = newState

        saveFavoriteStateToSharedPreferences(context)
    }

    private fun saveFavoriteStateToSharedPreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences(AddNewContact.MY_CONTACTS_KEY, MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString(AddNewContact.MY_CONTACTS_KEY, null)

        val contactList = mutableListOf<UserInformation>()

        json?.let {
            val type = object : TypeToken<List<UserInformation>>() {}.type
            contactList.addAll(gson.fromJson(it, type))
        }

        val updatedContactList = contactList.map { contact ->
            if (contact.getFirstName() == this.firstName && contact.getLastName() == this.lastName) {
                contact.copy(isFavorite = this.isFavorite)
            } else {
                contact
            }
        }

        val updatedJson = gson.toJson(updatedContactList)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(AddNewContact.MY_CONTACTS_KEY, updatedJson)
        editor.apply()
    }

    private fun copy(
        firstName: String = this.firstName,
        lastName: String = this.lastName,
        phoneNumber: String = this.phoneNumber,
        isFavorite: Boolean = this.isFavorite
    ): UserInformation {
        return UserInformation(firstName, lastName, phoneNumber, isFavorite)
    }

}