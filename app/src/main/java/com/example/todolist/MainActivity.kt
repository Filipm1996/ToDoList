package com.example.todolist


import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.data.db.entities.Activity
import com.example.todolist.data.repositories.ToDoRepository
import com.example.todolist.ui.RecyclerAdapter
import com.example.todolist.ui.ToDoViewModel
import com.example.todolist.ui.ToDoViewModelFactory
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

private lateinit var binding: ActivityMainBinding
@DelicateCoroutinesApi
@RequiresApi(Build.VERSION_CODES.P)
class MainActivity: AppCompatActivity() {
    private lateinit var viewModel : ToDoViewModel
    private  var recyclerAdapter : RecyclerAdapter? = null
    private val listOfActivities = mutableListOf<Activity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        recyclerAdapter = RecyclerAdapter()
        viewModel = ViewModelProvider(this,ToDoViewModelFactory(ToDoRepository()) )[ToDoViewModel::class.java]
        setOnClickListeners()
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

            viewModel.getAllActivitiesFromFirebase().observe(this){list ->
                recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                recyclerAdapter?.addItem(list)
                list.forEach{listOfActivities.add(it)}
                recyclerView.adapter = recyclerAdapter
            }

    }

    private fun setOnClickListeners() {
        recyclerAdapter!!.setOnClickDeleteItem {
                deleteActivity(it.description)
                recyclerAdapter!!.notifyDataSetChanged()
        }

        binding.confirmButton.setOnClickListener {
            val text: String = binding.editText.text.toString()
            if (text != "") {
                viewModel.saveActivityToFirebase(Activity(description = text))
                binding.editText.text!!.clear()
            }
            else{Toast.makeText(this,"Please write activity", Toast.LENGTH_LONG).show()}
        }
        recyclerAdapter!!.setOnIsCheckedItem {
                isCheckedChange(it.done, it.description)
        }
        binding.deleteAll.setOnClickListener {
            deleteAll()
            recyclerAdapter!!.notifyDataSetChanged()
        }

    }

    private fun isCheckedChange(done: String, description: String) {
        val isChecked : Boolean = done.toBoolean()
        if (isChecked){
            viewModel.isCheckedChangeInFirebase(false.toString(),description)
        } else{
            viewModel.isCheckedChangeInFirebase(true.toString(), description)
        }
    }

   fun deleteActivity(description :String) {
           val buildier = AlertDialog.Builder(this)
           buildier.setMessage("Do you want to delete activity?")
           buildier.setCancelable(true)
           buildier.setPositiveButton("Yes") { dialog, _ ->
                   viewModel.deleteActivityFromFirebase(description)
                   dialog.dismiss()
           }
           buildier.setNegativeButton("No") { dialog, _ ->
               dialog.dismiss()
           }
            val alert = buildier.create()
            alert.show()
    }

    fun deleteAll(){
        val buildier = AlertDialog.Builder(this)
        buildier.setMessage("Do you want to delete all activities?")
        buildier.setCancelable(true)
        buildier.setPositiveButton("Yes") { dialog, _ ->
            viewModel.deleteAllActivities(listOfActivities)
            dialog.dismiss()
        }
        buildier.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = buildier.create()
        alert.show()
    }
}
