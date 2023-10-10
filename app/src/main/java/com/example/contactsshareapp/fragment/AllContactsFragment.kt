package com.example.contactsshareapp.fragment

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsshareapp.R
import com.example.contactsshareapp.adapter.RecyclerviewAdapter
import com.example.contactsshareapp.model.UserInformation
import androidx.appcompat.widget.SearchView
import com.example.contactsshareapp.AddNewContact
import com.example.contactsshareapp.MainActivity
import com.example.contactsshareapp.interfaces.ContactAddedListener
import com.example.contactsshareapp.interfaces.ContactsInterface
import com.example.contactsshareapp.interfaces.FavoriteChangeListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AllContactsFragment : Fragment(), FavoriteChangeListener, ContactAddedListener {
    private lateinit var recyclerview:RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var allContactsAdapter: RecyclerviewAdapter
    private var allContactInterface: ContactsInterface? = null

    companion object{
        var USER_INFO_LIST = ArrayList<UserInformation>()
        var contactAddedListener: ContactAddedListener? = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is ContactsInterface){
            allContactInterface = context
        }
        if(context is MainActivity){
            context.allContactsInterface = object:ContactsInterface{
                override fun onFavoriteChanged() {
                    allContactsAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_contacts, container, false)
        recyclerview = view.findViewById(R.id.all_contact_rv)
        recyclerview.layoutManager = LinearLayoutManager(activity)

        USER_INFO_LIST.addAll(loadContactsFromSharedPreferences())

        allContactsAdapter = RecyclerviewAdapter(requireContext(),USER_INFO_LIST,this)
        recyclerview.adapter = allContactsAdapter

        searchView = view.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText)
                return true
            }
        })

        contactAddedListener = object : ContactAddedListener {
            override fun onContactAdded() {
                allContactsAdapter.notifyDataSetChanged()
            }
        }
        return view
    }

    private fun loadContactsFromSharedPreferences(): List<UserInformation> {
        val sharedPreferences = requireActivity().getSharedPreferences(AddNewContact.MY_CONTACTS_KEY, MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString(AddNewContact.MY_CONTACTS_KEY, null)

        return if (json != null) {
            val type = object : TypeToken<List<UserInformation>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    private fun filter(query: String?) {
        val filteredList = ArrayList<UserInformation>()
        val noResultsTextView = view?.findViewById<TextView>(R.id.noResultsTextView)
        var flag = false

        query?.let {
            if (it.isEmpty()) {
                filteredList.addAll(USER_INFO_LIST)
                noResultsTextView?.visibility = View.GONE
            } else {
                for (user in USER_INFO_LIST) {
                    if (user.getFirstName().lowercase().contains(it.lowercase())) {
                        filteredList.add(user)
                        flag = true
                        noResultsTextView?.visibility = View.GONE
                    }
                }
                if (!flag) noResultsTextView?.visibility = View.VISIBLE
            }
        }
        allContactsAdapter.filterList(filteredList)
    }

    override fun onFavoriteChanged(user: UserInformation) {
        if (user.getFavoriteState()) {
            FavoriteContactsFragment.FILTERED_DATA.add(user)
        } else {
            FavoriteContactsFragment.FILTERED_DATA.remove(user)
        }

        allContactInterface?.onFavoriteChanged()

        allContactsAdapter?.notifyDataSetChanged()
    }

    override fun onContactAdded() {
        allContactsAdapter.notifyDataSetChanged()
    }
}
