package com.example.shoppinglist.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.ShoppingRepository
import com.example.shoppinglist.data.ThemeRepository
import com.example.shoppinglist.domain.ShoppingItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShoppingViewModel(application: Application) : AndroidViewModel(application) {

    private val shoppingRepo = ShoppingRepository(application)
    private val themeRepo = ThemeRepository(application)

    private val _items = MutableStateFlow<List<ShoppingItem>>(emptyList())
    val items: StateFlow<List<ShoppingItem>> = _items

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText

    val isDarkMode: StateFlow<Boolean> = themeRepo.isDarkMode
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    init {
        _items.value = shoppingRepo.loadItems()
    }

    fun onInputChange(text: String) {
        _inputText.value = text
    }

    fun addItem() {
        val name = _inputText.value.trim()
        if (name.isEmpty()) return
        _items.update { it + ShoppingItem(name = name) }
        _inputText.value = ""
        saveItems()
    }

    fun toggleItem(id: String) {
        _items.update { list ->
            list.map { if (it.id == id) it.copy(isChecked = !it.isChecked) else it }
        }
        saveItems()
    }

    fun deleteItem(id: String) {
        _items.update { it.filter { item -> item.id != id } }
        saveItems()
    }

    fun toggleTheme() {
        viewModelScope.launch {
            themeRepo.setDarkMode(!isDarkMode.value)
        }
    }

    private fun saveItems() {
        shoppingRepo.saveItems(_items.value)
    }
}