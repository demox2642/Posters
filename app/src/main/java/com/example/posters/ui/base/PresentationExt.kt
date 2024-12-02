package com.example.posters.ui.base

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toPresentFormat(): String?{
    val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return  if (this.time < 0) null else format.format(this.time*1000)
}