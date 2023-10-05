package com.example.contactsshareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.contactsshareapp.adapter.ViewPagerAdapter
import com.example.contactsshareapp.fragment.FavoriteContactsFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var myAdapter: ViewPagerAdapter
    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager : ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        tabLayout = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.main_pager)

        myAdapter = ViewPagerAdapter(this)
        viewPager.adapter = myAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
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
                val intent = Intent(this,AddNewContact::class.java)
                startActivity(intent)
            }
        }
        return true
    }
}