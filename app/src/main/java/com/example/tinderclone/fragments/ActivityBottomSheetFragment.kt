package com.example.tinderclone.fragments

import ActivitiesAdapter
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_hello, container, false)

        dbHelper = DatabaseHelper(view.context)
        recyclerView = view.findViewById(R.id.recycleview_adapter)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        val activitiesAdapter = ActivitiesAdapter(dbHelper.getAllActivities() as MutableList<Activity>,view.context)
        recyclerView.adapter = activitiesAdapter

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(activitiesAdapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
        return view
    }


}

class SwipeToDeleteCallback(private val adapter: ActivitiesAdapter) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        adapter.deleteItem(position)
    }
}