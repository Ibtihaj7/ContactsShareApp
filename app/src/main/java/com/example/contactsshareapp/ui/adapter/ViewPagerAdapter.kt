package com.example.contactsshareapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.contactsshareapp.ui.home.MainActivity
import com.example.contactsshareapp.ui.allcontact.AllContactsFragment
import com.example.contactsshareapp.ui.favoritecontact.FavoriteContactsFragment

class ViewPagerAdapter (private val mainActivity: MainActivity) : FragmentStateAdapter(mainActivity) {
    private val numberOfTabs = 2
    override fun getItemCount(): Int {
        return numberOfTabs
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> AllContactsFragment()
            else -> FavoriteContactsFragment()
        }
    }
}