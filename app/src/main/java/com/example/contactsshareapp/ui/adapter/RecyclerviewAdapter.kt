package com.example.contactsshareapp.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.contactsshareapp.R
import com.example.contactsshareapp.callbacks.FavoriteChangeListener
import com.example.contactsshareapp.databinding.CustomContactsBinding
import com.example.contactsshareapp.model.UserInformation
import com.google.gson.Gson

class RecyclerviewAdapter(
    private val context: Context,
    private var userInfoList: List<UserInformation>,
    private val favoriteChangeListener: FavoriteChangeListener
) : RecyclerView.Adapter<RecyclerviewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CustomContactsBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    fun updateData(newData: List<UserInformation>) {
        userInfoList = newData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUser = userInfoList[position]
        holder.bind(currentUser)
    }

    override fun getItemCount(): Int {
        return userInfoList.size
    }

    inner class MyViewHolder(private val binding: CustomContactsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userInformation: UserInformation) {
            binding.userInformation = userInformation

            binding.userName.text = "${userInformation.getFirstName()} ${userInformation.getLastName()}"

            binding.heartBtn.setImageResource(
                if (userInformation.getFavoriteState()) R.drawable.fill_heart
                else R.drawable.empty_heart
            )

            binding.shareBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"

                val gson = Gson()
                val json = gson.toJson(userInformation)
                intent.putExtra("user_information", json)

                context.startActivity(intent)
            }

            binding.heartBtn.setOnClickListener {
                userInformation.setFavoriteState(!userInformation.getFavoriteState(), context)

                favoriteChangeListener.onFavoriteChanged(userInformation)
            }

            Glide.with(context)
                .load(userInformation.getPhoto())
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .error(R.drawable.error_img)
                .circleCrop()
                .into(binding.imageView)
        }
    }
}
