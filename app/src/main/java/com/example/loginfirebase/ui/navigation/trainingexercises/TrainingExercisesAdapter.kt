package com.example.loginfirebase.ui.navigation.trainingexercises

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginfirebase.R
import com.example.loginfirebase.model.Exercise

class TrainingExercisesAdapter : RecyclerView.Adapter<TrainingExercisesAdapter.TrainingExercisesHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingExercisesHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_exercise, parent, false)

        return TrainingExercisesHolder(view)
    }

    override fun onBindViewHolder(holder: TrainingExercisesHolder, position: Int) {
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

    inner class TrainingExercisesHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(exercise: Exercise) {
            val name = itemView.findViewById<TextView>(R.id.tvNameExercise)
            name.text = exercise.name

            val sets = itemView.findViewById<TextView>(R.id.tvSetsExercise)
            sets.text = exercise.sets

            val equipment = itemView.findViewById<TextView>(R.id.tvEquipment)
            equipment.text = exercise.equipment

            val image = itemView.findViewById<ImageView>(R.id.ivItemExercise)
            Glide.with(image.context).load(exercise.image).into(image)

            itemView.setOnClickListener {
                listener.onItemClick(exercise)
            }
        }
    }
}