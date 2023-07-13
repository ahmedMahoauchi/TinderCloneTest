package com.example.tinderclone.fragments

import ActivitiesAdapter
import SwipeToDeleteCallback
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinderclone.R
import com.example.tinderclone.models.Activity
import com.example.tinderclone.utils.DatabaseHelper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ActivityBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var text: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_hello, container, false)

        dbHelper = DatabaseHelper(view.context)
        recyclerView = view.findViewById(R.id.recycleview_adapter)
        text = view.findViewById(R.id.textView)
        text.visibility = View.INVISIBLE

        recyclerView.layoutManager = LinearLayoutManager(view.context)
        val listActivities = dbHelper.getAllActivities() as MutableList<Activity>

        if (listActivities.count() == 0){
            text.visibility = View.VISIBLE
            recyclerView.visibility = View.INVISIBLE
        }
        else{
            text.visibility = View.INVISIBLE
            recyclerView.visibility = View.VISIBLE
            val activitiesAdapter = ActivitiesAdapter(listActivities,view.context)
            recyclerView.adapter = activitiesAdapter

            val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(activitiesAdapter))
            itemTouchHelper.attachToRecyclerView(recyclerView)
        }

        return view
    }


}

