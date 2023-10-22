package com.example.contactsshareapp.ui.allcontact

import DataRepo
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsshareapp.R
import com.example.contactsshareapp.ui.adapter.RecyclerviewAdapter
import com.example.contactsshareapp.model.UserInformation
import androidx.appcompat.widget.SearchView
import com.example.contactsshareapp.databinding.FragmentAllContactsBinding
import com.example.contactsshareapp.callbacks.ContactAddedListener
import com.example.contactsshareapp.callbacks.ContactsInterface
import com.example.contactsshareapp.callbacks.FavoriteChangeListener
import com.example.contactsshareapp.ui.favoritecontact.FavoriteContactsFragment
import com.example.contactsshareapp.ui.addcontact.AddNewContact
import com.example.contactsshareapp.ui.home.MainActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AllContactsFragment : Fragment(), FavoriteChangeListener, ContactAddedListener {
    private lateinit var binding:FragmentAllContactsBinding
    private lateinit var recyclerview:RecyclerView
    private lateinit var allContactsAdapter: RecyclerviewAdapter
    private lateinit var userList :ArrayList<UserInformation>
    private var allContactInterface: ContactsInterface? = null


    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var gson: Gson

    companion object{
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
        binding = FragmentAllContactsBinding.inflate(inflater)

        val view = binding.root
        recyclerview = binding.allContactRv
        recyclerview.layoutManager = LinearLayoutManager(requireActivity())

        userList = DataRepo.getAllUserList()
        userList.addAll(loadContactsFromSharedPreferences())

        allContactsAdapter = RecyclerviewAdapter(requireContext(), userList, this)
        recyclerview.adapter = allContactsAdapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
        val json = sharedPreferences.getString(AddNewContact.MY_CONTACTS_KEY, null)

        return if (json != null) {
            val type = object : TypeToken<List<UserInformation>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    private fun filter(query: String?) {
        val filteredList = userList.filter { user ->
            query.isNullOrBlank() || user.getFirstName().contains(query, ignoreCase = true)
        }

        val noResultsTextView = view?.findViewById<TextView>(R.id.noResultsTextView)
        noResultsTextView?.visibility = if (filteredList.isEmpty()) View.VISIBLE else View.GONE

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