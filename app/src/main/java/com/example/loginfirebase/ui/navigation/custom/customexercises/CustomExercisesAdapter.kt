package com.example.loginfirebase.ui.navigation.custom.customexercises

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginfirebase.R
import com.example.loginfirebase.model.Exercise

class CustomExercisesAdapter : RecyclerView.Adapter<CustomExercisesAdapter.CustomExercisesHolder>() {

    private lateinit var listener: OnItemClickListener
    private var listData = mutableListOf<Exercise>()

    interface OnItemClickListener {
        fun onItemClick(exercise: Exercise)
    }

    fun setListData(data: MutableList<Exercise>) {
        listData = data
    }

    fun setItemListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomExercisesHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_exercise_training, parent, false)

        return CustomExercisesHolder(view)
    }

    override fun onBindViewHolder(holder: CustomExercisesHolder, position: Int) {
        val customCard = listData[position]

        holder.bindView(customCard)

    }


    inner class CustomExercisesHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(exercise: Exercise) {
            val name = itemView.findViewById<TextView>(R.id.tvNameExercise)
            name.text = exercise.name

            val sets = itemView.findViewById<TextView>(R.id.tvSetsExercise)


        }

    }



}