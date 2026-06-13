package com.example.shoppinglist.domain

import java.util.UUID

data class ShoppingItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val isChecked: Boolean = false
)