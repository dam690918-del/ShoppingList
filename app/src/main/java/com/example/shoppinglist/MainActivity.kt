package com.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import com.example.shoppinglist.ui.ShoppingScreen
import com.example.shoppinglist.ui.ShoppingViewModel
import com.example.shoppinglist.ui.theme.ShoppingListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: ShoppingViewModel = viewModel()
            val isDark by viewModel.isDarkMode.collectAsState()
            ShoppingListTheme(darkTheme = isDark) {
                ShoppingScreen(viewModel = viewModel)
            }
        }
    }
}