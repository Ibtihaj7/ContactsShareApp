package com.example.contactsshareapp.model

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
    fun setFavoriteState(isFavorite:Boolean){
        this.isFavorite = isFavorite
    }
}