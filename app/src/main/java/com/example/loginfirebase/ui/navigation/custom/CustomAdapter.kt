package com.example.loginfirebase.ui.navigation.custom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginfirebase.R
import com.example.loginfirebase.model.CustomWorkout

class CustomAdapter : RecyclerView.Adapter<CustomAdapter.CustomHolder>() {

    private lateinit var listener: OnItemClickListener
    private var listData = mutableListOf<CustomWorkout>()

    interface OnItemClickListener {
        fun onItemClick(customWorkout: CustomWorkout)
    }

    fun setListData(data: MutableList<CustomWorkout>) {
        listData = data
    }

    fun setItemListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_workout, parent, false)

        return CustomHolder(view)
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        val customCard = listData[position]

        holder.bindView(customCard)
    }

    override fun getItemCount(): Int {
        return if (listData.size > 0) {
            listData.size
        } else {
            0
        }
    }

    inner class CustomHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(customWorkout: CustomWorkout) {
            val name = itemView.findViewById<TextView>(R.id.tvNameWorkout)
            name.text = customWorkout.name

            itemView.setOnClickListener {
                listener.onItemClick(customWorkout)
            }
        }
    }
}