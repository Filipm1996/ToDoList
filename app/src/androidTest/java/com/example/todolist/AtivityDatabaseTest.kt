package com.example.todolist


import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.todolist.data.db.ActivityDao
import com.example.todolist.data.db.entities.ActivityDatabase
import com.example.todolist.data.db.entities.Activity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class AtivityDatabaseTest {
    private lateinit var database: ActivityDatabase
    private lateinit var activityDao: ActivityDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ActivityDatabase::class.java
        ).allowMainThreadQueries().build()
        activityDao = database.activityDao()
    }
    @After
    fun teardown() {
        database.close()
    }
    @Test
    fun insertActivity() = runBlocking{
        val activity = Activity("activity1", "true", 1)
        activityDao.insertActivity(activity)
        val allActivities = activityDao.getAllActivities()
        assertThat(activity in allActivities.value!!).isTrue()
    }

    @Test
    fun deleteActivity() = runBlocking {
        val activity = Activity("activity1", "true", 1)
        activityDao.insertActivity(activity)
        activityDao.deleteActivity(activity.id!!)
        val allActivities = activityDao.getAllActivities()
        assertThat(allActivities.value!!.contains(activity)).isFalse()
    }

    @Test
    fun isCheckChange() = runBlocking {
        val activity = Activity("activity1", "true", 1)
        activityDao.insertActivity(activity)
        activityDao.isCheckedChange("false", activity.id!!)
        val allActivities = activityDao.getAllActivities()
        assertThat(allActivities.value!![0].isDone == "false").isTrue()
    }

    @Test
    fun getDataCount () = runBlocking {
        val activity1 = Activity("activity1", "true", 1)
        val activity2 = Activity("activity2", "true", 2)
        activityDao.insertActivity(activity1)
        activityDao.insertActivity(activity2)
        val  allActivitiesCount = activityDao.getDataCount()
        assertThat(allActivitiesCount == 2).isTrue()
    }
}