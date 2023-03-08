package com.example.loginfirebase.ui.navigation.training

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginfirebase.R
import com.example.loginfirebase.model.Workout

class TrainingAdapter: RecyclerView.Adapter<TrainingAdapter.MyViewHolder>() {

    private val workoutList = ArrayList<Workout>()
//    final TrainingAdapter.OnItemClickListener litener;


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.card_layout,
            parent,false
        )
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = workoutList[position]

        holder.nameWorkout.text = currentitem.name
        holder.numberOfExercises.text = currentitem.number_of_exercises

    }

    override fun getItemCount(): Int {
        return workoutList.size
    }

    fun updateWorkoutList(workoutList : List<Workout>){

        this.workoutList.clear()
        this.workoutList.addAll(workoutList)
        notifyDataSetChanged()
    }

    class  MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val nameWorkout : TextView = itemView.findViewById(R.id.tvNameWorkout)
        val numberOfExercises : TextView = itemView.findViewById(R.id.tvNumberOfExercises)

    }

}