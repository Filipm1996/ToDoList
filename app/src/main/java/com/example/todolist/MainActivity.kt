package com.example.todolist


import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.data.db.entities.Activity
import com.example.todolist.data.repositories.toDoRepository
import com.example.todolist.ui.RecyclerAdapter
import com.example.todolist.ui.toDoViewModel
import com.example.todolist.ui.toDoViewModelFactory
import kotlinx.coroutines.*

private lateinit var binding: ActivityMainBinding
@DelicateCoroutinesApi
@RequiresApi(Build.VERSION_CODES.P)
class MainActivity: AppCompatActivity() {
    private lateinit var viewModel : toDoViewModel

    private  var recyclerAdapter : RecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        recyclerAdapter = RecyclerAdapter()
        val factory = toDoViewModelFactory(toDoRepository(this))
        viewModel = ViewModelProvider(this, factory).get(toDoViewModel::class.java)
        setOnClickListeners()
            val recyclerView = findViewById<RecyclerView>(R.id.listView)
            viewModel.getAllActivities().observe(this, Observer {
                val listOfAcivities = it
                recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                recyclerAdapter?.addItem(listOfAcivities)
                recyclerView.adapter = recyclerAdapter

            })

    }

    private fun setOnClickListeners() {
        recyclerAdapter!!.setOnClickDeleteItem {
                deleteActivity(it.id!!)
        }

        binding.confirmButton.setOnClickListener {
            val text: String = binding.editText.text.toString()
            if (text != "") {
                viewModel.insertActivity(Activity(description = text))
                binding.editText.getText()!!.clear()
            }
            else{Toast.makeText(this,"Please write activity", Toast.LENGTH_LONG).show()}
        }
        recyclerAdapter!!.setOnIsCheckedItem {
                isCheckedChange(it.isDone, it.id!!)
        }

    }

    private fun isCheckedChange(done: String, id: Int) {
        val isChecked : Boolean = done.toBoolean()
        if (isChecked == true){
            viewModel.isCheckedChange(false.toString(), id)
        } else{
            viewModel.isCheckedChange(true.toString(), id)
        }
    }

   private fun deleteActivity(id: Int) {
           val buildier = AlertDialog.Builder(this)
           buildier.setMessage("Do you want to delete activity?")
           buildier.setCancelable(true)
           buildier.setPositiveButton("Yes") { dialog, _ ->
                   viewModel.deleteActivity(id)
                   dialog.dismiss()
           }
           buildier.setNegativeButton("No") { dialog, _ ->
               dialog.dismiss()
           }
            val alert = buildier.create()
            alert.show()
    }
}
