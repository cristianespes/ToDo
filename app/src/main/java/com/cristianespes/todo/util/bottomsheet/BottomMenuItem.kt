package com.cristianespes.todo.util.bottomsheet

data class BottomMenuItem(
        val resId: Int,
        val name: String,
        val action: () -> Unit
)