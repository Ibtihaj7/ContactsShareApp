package com.example.contactsshareapp.interfaces

import com.example.contactsshareapp.model.UserInformation

interface FavoriteChangeListener {
    fun onFavoriteChanged(userInformation: UserInformation)
}
