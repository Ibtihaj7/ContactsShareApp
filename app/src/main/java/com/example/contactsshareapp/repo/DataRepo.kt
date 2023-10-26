package com.example.contactsshareapp.repo

import android.content.SharedPreferences
import com.example.contactsshareapp.model.UserInformation
import com.example.contactsshareapp.ui.addcontact.AddNewContact
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class DataRepo @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
)
{
    private var allUserList: ArrayList<UserInformation> = loadContactsFromSharedPreferences()
    private var favoriteUserList: ArrayList<UserInformation> = getFavoriteUser()

    fun getAllUsers(): List<UserInformation> = allUserList
    fun getFavoriteUsers(): List<UserInformation> = favoriteUserList

    fun addUser(user: UserInformation) {
        allUserList.add(user)
    }

    fun addToFavorites(user: UserInformation) {
        favoriteUserList.add(user)
    }

    fun removeFromFavorites(user: UserInformation) {
        favoriteUserList.remove(user)
    }

    private fun getFavoriteUser(): ArrayList<UserInformation> {
        return ArrayList(
            getAllUsers().filter {
                it.getFavoriteState()
            }
        )
    }

    private fun loadContactsFromSharedPreferences(): ArrayList<UserInformation> {
        val json = sharedPreferences.getString(AddNewContact.MY_CONTACTS_KEY, null)

        return if (json != null) {
            val type = object : TypeToken<ArrayList<UserInformation>>() {}.type
            gson.fromJson(json, type)
        } else {
            ArrayList()
        }
    }
}