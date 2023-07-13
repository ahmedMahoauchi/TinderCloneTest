package com.example.tinderclone.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.tinderclone.models.Activity

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "activity_database"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "activities"
        private const val COLUMN_ID = "id"
        private const val COLUMN_ACTIVITY_ACCESSIBILITY = "accessibility"
        private const val COLUMN_ACTIVITY_NAME = "activity"
        private const val COLUMN_ACTIVITY_KEY = "key"
        private const val COLUMN_ACTIVITY_LINK = "link"
        private const val COLUMN_ACTIVITY_PARTICIPANTS = "participants"
        private const val COLUMN_ACTIVITY_PRICE = "price"
        private const val COLUMN_ACTIVITY_TYPE = "type"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_ACTIVITY_ACCESSIBILITY REAL, " +
                "$COLUMN_ACTIVITY_NAME TEXT, " +
                "$COLUMN_ACTIVITY_KEY TEXT, " +
                "$COLUMN_ACTIVITY_LINK TEXT, " +
                "$COLUMN_ACTIVITY_PARTICIPANTS INTEGER, " +
                "$COLUMN_ACTIVITY_PRICE REAL, " +
                "$COLUMN_ACTIVITY_TYPE TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun addActivity(activity: Activity): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ACTIVITY_ACCESSIBILITY, activity.accessibility)
            put(COLUMN_ACTIVITY_NAME, activity.activity)
            put(COLUMN_ACTIVITY_KEY, activity.key)
            put(COLUMN_ACTIVITY_LINK, activity.link)
            put(COLUMN_ACTIVITY_PARTICIPANTS, activity.participants)
            put(COLUMN_ACTIVITY_PRICE, activity.price)
            put(COLUMN_ACTIVITY_TYPE, activity.type)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun getAllActivities(): List<Activity> {
        val activities = mutableListOf<Activity>()
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"

        val cursor = db.rawQuery(selectQuery, null)
        cursor.use {
            val idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID)
            val accessibilityIndex = cursor.getColumnIndexOrThrow(COLUMN_ACTIVITY_ACCESSIBILITY)
            val activityNameIndex = cursor.getColumnIndexOrThrow(COLUMN_ACTIVITY_NAME)
            val keyIndex = cursor.getColumnIndexOrThrow(COLUMN_ACTIVITY_KEY)
            val linkIndex = cursor.getColumnIndexOrThrow(COLUMN_ACTIVITY_LINK)
            val participantsIndex = cursor.getColumnIndexOrThrow(COLUMN_ACTIVITY_PARTICIPANTS)
            val priceIndex = cursor.getColumnIndexOrThrow(COLUMN_ACTIVITY_PRICE)
            val typeIndex = cursor.getColumnIndexOrThrow(COLUMN_ACTIVITY_TYPE)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idIndex)
                val accessibility = cursor.getFloat(accessibilityIndex)
                val activityName = cursor.getString(activityNameIndex)
                val key = cursor.getString(keyIndex)
                val link = cursor.getString(linkIndex)
                val participants = cursor.getInt(participantsIndex)
                val price = cursor.getFloat(priceIndex)
                val type = cursor.getString(typeIndex)

                val activity = Activity(accessibility, activityName, key, link, participants, price, type)
                activities.add(activity)
            }
        }

        return activities
    }

    fun deleteActivityByKey(activityKey: String): Int {
        val db = writableDatabase
        val whereClause = "$COLUMN_ACTIVITY_KEY = ?"
        val whereArgs = arrayOf(activityKey)
        return db.delete(TABLE_NAME, whereClause, whereArgs)
    }
}