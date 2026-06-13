package com.example.shoppinglist.data

import android.content.Context
import com.example.shoppinglist.domain.ShoppingItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ShoppingRepository(context: Context) {

    private val prefs = context.getSharedPreferences("shopping_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val KEY = "items"

    fun loadItems(): List<ShoppingItem> {
        val json = prefs.getString(KEY, null) ?: return emptyList()
        val type = object : TypeToken<List<ShoppingItem>>() {}.type
        return gson.fromJson(json, type)
    }

    fun saveItems(items: List<ShoppingItem>) {
        prefs.edit().putString(KEY, gson.toJson(items)).apply()
    }
}