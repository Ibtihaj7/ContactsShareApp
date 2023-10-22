package com.example.contactsshareapp.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.contactsshareapp.R
import com.example.contactsshareapp.callbacks.FavoriteChangeListener
import com.example.contactsshareapp.model.UserInformation
import com.google.gson.Gson

class RecyclerviewAdapter(
    private val context: Context,
    private var userInfoList: ArrayList<UserInformation>,
    private val favoriteChangeListener: FavoriteChangeListener
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
        val currentUser = userInfoList[position]
        holder.userName.text = "${currentUser.getFirstName()} ${currentUser.getLastName()}"

        holder.favoriteButton.setImageResource(
            if (currentUser.getFavoriteState()) R.drawable.fill_heart
            else R.drawable.empty_heart
        )
        holder.shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"

            val gson = Gson()
            val json = gson.toJson(currentUser)
            intent.putExtra("user_information", json)

            context.startActivity(intent)
        }


        holder.favoriteButton.setOnClickListener {
            currentUser.setFavoriteState(!currentUser.getFavoriteState(),context)
            notifyDataSetChanged()

            favoriteChangeListener.onFavoriteChanged(currentUser)
        }

        Glide.with(context)
            .load(currentUser.getPhoto())
            .placeholder(R.drawable.placeholder)
            .fitCenter()
            .error(R.drawable.error_img)
            .circleCrop()
            .into(holder.userPhoto)

    }

    override fun getItemCount(): Int {
        return userInfoList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val favoriteButton: ImageButton = itemView.findViewById(R.id.heart_btn)
        val shareButton : ImageButton = itemView.findViewById(R.id.share_btn)
        val userPhoto : ImageView = itemView.findViewById(R.id.imageView)
    }
}
