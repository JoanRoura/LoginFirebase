package com.example.loginfirebase.ui.navigation.training

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.loginfirebase.R
import com.example.loginfirebase.model.Workout

class TrainingAdapter: RecyclerView.Adapter<TrainingAdapter.MyViewHolder>() {

    private val workoutList = ArrayList<Workout>()
//    private lateinit var listener: onItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.card_layout,
            parent,false
        )
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

//        val workout = listData[position]
//
//        holder.bindView(workout)

        val currentitem = workoutList[position]

        holder.nameWorkout.text = currentitem.name
        holder.numberOfExercises.text = currentitem.number_of_exercises

        holder.itemView.setOnClickListener {
//            Navigation.findNavController().navigate(R.id.action_trainingFragment_to_trainingExercisesFragment)
        }
    }

    override fun getItemCount(): Int {
        return workoutList.size
    }

    fun updateWorkoutList(workoutList : List<Workout>){

        this.workoutList.clear()
        this.workoutList.addAll(workoutList)
        notifyDataSetChanged()
    }



    inner class  MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val nameWorkout : TextView = itemView.findViewById(R.id.tvNameWorkout)
        val numberOfExercises : TextView = itemView.findViewById(R.id.tvNumberOfExercises)

//        fun bindView(workout: Workout) {
//            val nameWorkout : TextView = itemView.findViewById(R.id.tvNameWorkout)
//            val numberOfExercises : TextView = itemView.findViewById(R.id.tvNumberOfExercises)
//
//            itemView.setOnClickListener {
//                listener.onItemClick(workout)
//            }
//
//        }
    }

//    interface onItemClickListener{
//        fun onItemClick(workout: Workout)
//    }
//
//    private var listData = listOf<Workout>()
//
//    fun setListData(data: List<Workout>) {
//        listData = data
//    }
//
//    fun setItemListener(listener: onItemClickListener){
//        this.listener = listener
//    }
}