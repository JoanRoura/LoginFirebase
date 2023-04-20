package com.example.loginfirebase.ui.navigation.custom.newcustom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginfirebase.R
import com.example.loginfirebase.model.Exercise

class NewCustomAdapter(private val selectedItems: MutableList<Exercise>) : RecyclerView.Adapter<NewCustomAdapter.NewCustomHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewCustomHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_exercise_training, parent, false)
        return NewCustomHolder(view)
    }

    override fun onBindViewHolder(holder: NewCustomHolder, position: Int) {
        val exerciseCard = selectedItems[position]

        holder.bindView(exerciseCard)
    }

    override fun getItemCount() = selectedItems.size

    class NewCustomHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(exercise: Exercise) {
            val name = itemView.findViewById<TextView>(R.id.tvNameExerciseTraining)
            name.text = exercise.name

            val sets = itemView.findViewById<TextView>(R.id.tvSetsExercise)
            sets.text = exercise.sets

            val reps = itemView.findViewById<TextView>(R.id.tvRepsExercise)
            reps.text = exercise.reps

            val equipment = itemView.findViewById<TextView>(R.id.tvEquipment)
            equipment.text = exercise.equipment

            val image = itemView.findViewById<ImageView>(R.id.ivItemExercise)
            Glide.with(image.context).load(exercise.image).into(image)
        }
    }



}