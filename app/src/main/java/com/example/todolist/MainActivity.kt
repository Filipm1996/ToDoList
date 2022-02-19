package com.example.todolist

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.todolist.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlin.coroutines.suspendCoroutine


private lateinit var datebase : ActivityDatabase
private  var recyclerAdapter : RecyclerAdapter? =null
private lateinit var binding: ActivityMainBinding
private lateinit var dao : ActivityDao
@DelicateCoroutinesApi
@RequiresApi(Build.VERSION_CODES.P)
class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        recyclerAdapter = RecyclerAdapter()

        GlobalScope.launch(Dispatchers.Main){
            datebase = Room.databaseBuilder(applicationContext, ActivityDatabase::class.java, "activity_database").build()
            dao = datebase.activityDao()
            val recyclerView = findViewById<RecyclerView>(R.id.listView)
                val listOfAcivities = dao.getAllActivities()
                recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                recyclerAdapter?.addItem(listOfAcivities)
                recyclerView.adapter = recyclerAdapter
                setOnClickListeners()


        }
        setContentView(binding.root)
    }


    suspend private fun getActivityList() {
        dao.getAllActivities().let { recyclerAdapter?.addItem(it) }
    }

    suspend private fun setOnClickListeners() {
        recyclerAdapter!!.setOnClickDeleteItem {
            GlobalScope.launch {
                deleteActivity(it.id!!)
            }
        }
        binding.confirmButton.setOnClickListener {
            val text: String = binding.editText.text.toString()
            if (text != "") {
                GlobalScope.launch {
                dao.insertActivity(Activity(description = text))
                    withContext(Dispatchers.Main) {
                        recyclerAdapter!!.notifyDataSetChanged()
                    }
                getActivityList()
                binding.editText.getText()!!.clear()}
            }
            else{Toast.makeText(this,"Please write activity", Toast.LENGTH_LONG).show()}
        }
        recyclerAdapter!!.setOnIsCheckedItem {
            GlobalScope.launch {
                isCheckedChange(it.isDone, it.id!!)
            }
        }

    }

    suspend private fun isCheckedChange(done: String, id: Int) {
        val isChecked : Boolean = done.toBoolean()
        if (isChecked == true){
            dao.isCheckedChange(false.toString(), id)
        } else{
            dao.isCheckedChange(true.toString(), id)
        }
    }

   suspend private fun deleteActivity(id: Int) {
           val buildier = AlertDialog.Builder(this)
           buildier.setMessage("Do you want to delete activity?")
           buildier.setCancelable(true)
           buildier.setPositiveButton("Yes") { dialog, _ ->
               GlobalScope.launch(Dispatchers.Main) {
                   dao.deleteActivity(id)
                   getActivityList()
                   recyclerAdapter!!.notifyDataSetChanged()
                   dialog.dismiss()
               }

           }
           buildier.setNegativeButton("No") { dialog, _ ->
               dialog.dismiss()
           }
        withContext(Dispatchers.Main) {
            val alert = buildier.create()
            alert.show()
        }
    }
}
