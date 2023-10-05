package com.example.contactsshareapp.interfaces

import com.example.contactsshareapp.model.UserInformation

interface OnItemClickListener {
    fun onItemClick(userInfo: UserInformation):Unit
}