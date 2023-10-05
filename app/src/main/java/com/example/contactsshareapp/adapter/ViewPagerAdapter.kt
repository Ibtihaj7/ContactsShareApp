package com.example.contactsshareapp.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.contactsshareapp.MainActivity
import com.example.contactsshareapp.fragment.AllContactsFragment
import com.example.contactsshareapp.fragment.FavoriteContactsFragment

class ViewPagerAdapter(mainActivity: MainActivity) : FragmentStateAdapter(mainActivity) {
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