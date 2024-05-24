package com.example.contactsshareapp.callbacks

import com.example.contactsshareapp.model.UserInformation

interface FavoriteChangeListener {
    fun onFavoriteChanged(userInformation: UserInformation)
}
