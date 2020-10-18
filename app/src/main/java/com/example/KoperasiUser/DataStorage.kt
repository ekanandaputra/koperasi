package com.example.kotlinnotifikasi

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class DataStorage {
    internal lateinit var savedSession: SharedPreferences
    private val KEY: String = "data_storage"

    fun saveId(context: Context, id: String) {
        // TODO Auto-generated method stub
        val editor = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE).edit()
        editor.putString("id", id)
        editor.commit()
    }

    fun getId(context: Context): String {
        savedSession = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE)
        return savedSession.getString("id", "").toString()
    }

    fun saveNama(context: Context, nama: String) {
        // TODO Auto-generated method stub
        val editor = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE).edit()
        editor.putString("nama", nama)
        editor.commit()
    }


    fun getNama(context: Context): String {
        savedSession = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE)
        return savedSession.getString("nama", "").toString()
    }

    fun saveUsername(context: Context, username: String) {
        // TODO Auto-generated method stub
        val editor = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE).edit()
        editor.putString("username", username)
        editor.commit()
    }


    fun getUsername(context: Context): String {
        savedSession = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE)
        return savedSession.getString("username", "").toString()
    }

}