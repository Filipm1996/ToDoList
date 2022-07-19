package com.example.todolist.data.repositories


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.todolist.data.db.entities.Activity
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception


class ToDoRepository: DeafultRepository {

    private val activitiesCollectionRef = Firebase.firestore.collection("activities")

    override fun saveActivityToFirebase(activity: Activity) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                activitiesCollectionRef.add(activity).await()
            } catch (e: Exception) {
                Log.e("error", e.message.toString())
            }
        }
    }


    override fun deleteActivityFromFirebase(description: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val activityQuery = activitiesCollectionRef
                .whereEqualTo("description", description)
                .get()
                .await()

            if (activityQuery.documents.isNotEmpty()){
                for (document in activityQuery){
                    try{
                        activitiesCollectionRef.document(document.id).delete().await()
                    }catch (e:Exception){
                        Log.e("error", e.message!!)
                    }
                }
            }
        }
    }

    override fun getAllActivitiesFromFirebase() : MutableLiveData<MutableList<Activity>> {
        val mutableLiveData = MutableLiveData<MutableList<Activity>>()
        activitiesCollectionRef.addSnapshotListener { querySnapshot, exception ->
            exception?.let {
                Log.e("FirebaseFirestoreException", it.message!!)
            }
            querySnapshot?.let {
                val listOfActivities = mutableListOf<com.example.todolist.data.db.entities.Activity>()
                for (document in it) {
                    val activity =
                        document.toObject(com.example.todolist.data.db.entities.Activity::class.java)
                    listOfActivities.add(activity)
                }
                mutableLiveData.postValue(listOfActivities)
            }
        }
        return  mutableLiveData
    }

    override fun isCheckedChangeInFirebase(done: String, description: String) {
        CoroutineScope(Dispatchers.IO).launch{
            val map = mutableMapOf<String,String>()
            map["done"] = done
            val activityQuery = activitiesCollectionRef
                .whereEqualTo("description", description)
                .get()
                .await()

            if (activityQuery.documents.isNotEmpty()){
                for (document in activityQuery){
                    try{
                        activitiesCollectionRef.document(document.id).set(map, SetOptions.merge()).await()
                    }catch (e:Exception){
                        Log.e("error", e.message!!)
                    }
                }
            }
        }
    }

    override fun deleteAllActivities(list: MutableList<Activity>) {
        CoroutineScope(Dispatchers.IO).launch {
            list.forEach {
                deleteActivityFromFirebase(it.description)
            }
        }
    }

}