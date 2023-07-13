package com.example.tinderclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.barryzeha.dotsloadingview.components.DotsLoadingComponent
import com.example.tinderclone.fragments.ActivityBottomSheetFragment
import com.example.tinderclone.fragments.SwipedActivitiesFragment
import com.example.tinderclone.models.Activity
import com.example.tinderclone.utils.ApiService
import com.example.tinderclone.utils.DatabaseHelper
import com.example.tinderclone.utils.RetrofitClient
import retrofit2.Call

class MainActivity : AppCompatActivity() {
    private lateinit var textActivity : TextView
    private lateinit var greenButton : LinearLayout
    private lateinit var redButton : LinearLayout
    private lateinit var blueButton : LinearLayout
    private lateinit var loader : DotsLoadingComponent
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewes()
        remplirText()
        dbHelper = DatabaseHelper(this)

        greenButton.setOnClickListener {
            acceptActivity()
        }

        redButton.setOnClickListener {
            refuseActivity()
        }

        blueButton.setOnClickListener {
            val bottomSheetFragment = ActivityBottomSheetFragment()
            bottomSheetFragment.show(supportFragmentManager, "activity_bottom_sheet")
        }

    }

    private fun refuseActivity() {
        //TODO("refus")
        remplirText()
    }

    private fun acceptActivity() {


        val insertedRowId = dbHelper.addActivity(activity)
        Log.i("sqllite",insertedRowId.toString())
        remplirText()

        val activities = dbHelper.getAllActivities()
        Log.i("all activities",activities.toString())

    }

    private fun initViewes() {
        textActivity = findViewById(R.id.text)
        greenButton = findViewById(R.id.green)
        redButton = findViewById(R.id.red)
        blueButton = findViewById(R.id.blue)
        loader = findViewById(R.id.myDotLoading)

    }


    private fun remplirText() {
        textActivity.visibility = View.INVISIBLE
        loader.visibility = View.VISIBLE
        val retrofit = RetrofitClient.getClient()
        val apiService = retrofit.create(ApiService::class.java)

        val call = apiService.getUser()

        call.enqueue(object : retrofit2.Callback<Activity> {
            override fun onResponse(call: Call<Activity>, response: retrofit2.Response<Activity>) {
                if (response.isSuccessful) {
                    val user = response.body()
                     activity = Activity(
                        accessibility = user!!.accessibility,
                        activity = user!!.activity,
                        key = user!!.key,
                        link = user!!.link,
                        participants = user!!.participants,
                        price = user!!.price,
                        type = user!!.type
                    )
                    Log.e("hello",user.toString())
                    textActivity.text = user?.activity.toString()
                    textActivity.visibility = View.VISIBLE
                    loader.visibility = View.INVISIBLE
                } else {
                    Log.i("hello",response.toString())
                }
            }

            override fun onFailure(call: Call<Activity>, t: Throwable) {
                Log.e("error",t.toString())
            }
        })
    }


}