package com.example.loginfirebase.ui.navigation.custom.newcustom.allexercises

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginfirebase.R
import com.example.loginfirebase.model.Exercise

class ExerciseAdapter : RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder>()  {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_exercise, parent, false)

        return ExerciseHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        val exerciseCard = listData[position]

        holder.bindView(exerciseCard)
    }

    override fun getItemCount(): Int {
        return if (listData.size > 0) {
            listData.size
        } else {
            0
        }
    }

    inner class ExerciseHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(exercise: Exercise) {
            val name = itemView.findViewById<TextView>(R.id.tvNameExercise)
            name.text = exercise.name

            val equipment = itemView.findViewById<TextView>(R.id.tvEquipmentOfExercise)
            equipment.text = exercise.equipment

            val focusArea = itemView.findViewById<TextView>(R.id.tvFocusAreaExercise)
            focusArea.text = exercise.focus_area

            val image = itemView.findViewById<ImageView>(R.id.ivItemExercise)
            Glide.with(image.context).load(exercise.image).into(image)

            itemView.setOnClickListener {
                listener.onItemClick(exercise)
            }

        }
    }
}