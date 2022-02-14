package com.example.userstask.ui.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.userstask.R
import com.example.userstask.data.entity.UserData

class MainAdapter(private val listener: ItemClickListener) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var userDataList: List<UserData> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = userDataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userDataList[position]
        Glide.with(holder.picture.context).load(user.picture?.medium).circleCrop()
            .into(holder.picture)
        holder.picture.context
        holder.firstName.text = user.name?.first
        holder.lastName.text = user.name?.last
        holder.gender.text = user.gender
        holder.phone.text = user.phone
        holder.address.text = user.location?.city
        holder.itemView.setOnClickListener { listener.onClick(user) }
    }

    fun setData(list: List<UserData>) {
        userDataList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val picture: ImageView = view.findViewById(R.id.picture)
        val firstName: TextView = view.findViewById(R.id.first_name)
        val lastName: TextView = view.findViewById(R.id.last_name)
        val gender: TextView = view.findViewById(R.id.gender)
        val phone: TextView = view.findViewById(R.id.phone)
        val address: TextView = view.findViewById(R.id.address)
    }

    interface ItemClickListener {
        fun onClick(user: UserData)
    }
}
