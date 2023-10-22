package com.example.contactsshareapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.contactsshareapp.R
import com.example.contactsshareapp.ui.adapter.ViewPagerAdapter
import com.example.contactsshareapp.databinding.ActivityMainBinding
import com.example.contactsshareapp.callbacks.ContactsInterface
import com.example.contactsshareapp.ui.addcontact.AddNewContact
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ContactsInterface {
    private lateinit var binding:ActivityMainBinding

    private lateinit var myAdapter: ViewPagerAdapter
    var allContactsInterface:ContactsInterface? = null
    var favoriteContactsInterface : ContactsInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        myAdapter = ViewPagerAdapter(this)
        binding.mainPager.adapter = myAdapter

        TabLayoutMediator(binding.tabs, binding.mainPager) { tab, position ->
            when (position) {
                0 -> tab.text = "All"
                1 -> tab.text = "Favorites"
            }
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add_contact -> {
                val intent = Intent(this, AddNewContact::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    override fun onFavoriteChanged() {
        allContactsInterface?.onFavoriteChanged()
        favoriteContactsInterface?.onFavoriteChanged()
    }
}