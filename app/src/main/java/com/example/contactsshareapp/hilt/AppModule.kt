package com.example.contactsshareapp.hilt

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.contactsshareapp.repo.DataRepo
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.example.contactsshareapp.ui.addcontact.AddNewContact

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(AddNewContact.MY_CONTACTS_KEY, MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideDataRepo(
        sharedPreferences: SharedPreferences,
        gson: Gson
    ): DataRepo {
        return DataRepo(sharedPreferences, gson)
    }
}
