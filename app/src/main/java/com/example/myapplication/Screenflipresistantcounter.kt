package com.example.myapplication

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class Screenflippersistantcounter : ViewModel() {

    private val _counter = MutableStateFlow(10)
    val counter: StateFlow<Int> = _counter

    private val _bgColor = MutableStateFlow(Color.White)
    val bgColor: StateFlow<Color> = _bgColor

    private val _items = MutableStateFlow<List<String>>(emptyList())
    val items: StateFlow<List<String>> = _items


    fun startCountdown() {
        viewModelScope.launch {
            while (_counter.value > 0) {
                delay(1000)
                _counter.value--
            }
        }
    }

    fun setBackground(color: Color) {
        _bgColor.value = color
    }

    fun addItem() {
        _items.value = _items.value + "Item ${_items.value.size + 1}"
    }

}
