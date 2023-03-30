package com.example.loginfirebase.ui.navigation.training

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.loginfirebase.R
import com.example.loginfirebase.model.Workout

class TrainingAdapter : RecyclerView.Adapter<TrainingAdapter.TrainingHolder>() {

    private lateinit var listener: OnItemClickListener
    private var listData = mutableListOf<Workout>()

    interface OnItemClickListener {
        fun onItemClick(workout: Workout)
    }

    fun setListData(data: MutableList<Workout>) {
        listData = data
    }

    fun setItemListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_workout, parent, false)

        return TrainingHolder(view)
    }

    override fun onBindViewHolder(holder: TrainingHolder, position: Int) {
        val workoutCard = listData[position]

        holder.bindView(workoutCard)
    }

    override fun getItemCount(): Int {
        return if (listData.size > 0) {
            listData.size
        } else {
            0
        }
    }

    inner class TrainingHolder(itemView: View):ViewHolder(itemView) {
        fun bindView(workout: Workout) {
            val name = itemView.findViewById<TextView>(R.id.tvNameWorkout)
            name.text = workout.name

            val numberOfExercises = itemView.findViewById<TextView>(R.id.tvNumberOfExercises)
            numberOfExercises.text = workout.number_of_exercises

            val image = itemView.findViewById<ImageView>(R.id.ivItemMuscle)
            Glide.with(image.context).load(workout.image).into(image)

            itemView.setOnClickListener {
                listener.onItemClick(workout)
            }
        }

    }
}
