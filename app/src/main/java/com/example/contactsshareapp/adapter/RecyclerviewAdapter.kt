package com.example.contactsshareapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsshareapp.R
import com.example.contactsshareapp.fragment.FavoriteContactsFragment
import com.example.contactsshareapp.interfaces.OnItemClickListener
import com.example.contactsshareapp.model.UserInformation

class RecyclerviewAdapter(
    private var userInfoList: ArrayList<UserInformation>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerviewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_contacts, parent, false)
        return MyViewHolder(itemView)
    }
    fun filterList(filteredData: List<UserInformation>) {
        userInfoList = ArrayList(filteredData)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userInfoList[position]
        holder.userName.text = "${currentItem.getFirstName()} ${currentItem.getLastName()}"

        holder.favoriteButton.setImageResource(
            if (currentItem.getFavoriteState()) R.drawable.fill_heart
            else R.drawable.empty_heart
        )

        holder.favoriteButton.setOnClickListener {
            Log.d("RecyclerviewAdapter", "heart_btn clicked at position $position")
            currentItem.setFavoriteState(!currentItem.getFavoriteState())
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return userInfoList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val favoriteButton: ImageButton = itemView.findViewById(R.id.heart_btn)

        init {
            itemView.setOnClickListener {
                val currentItem = userInfoList[adapterPosition]
                listener.onItemClick(currentItem)
            }
        }
    }
}
