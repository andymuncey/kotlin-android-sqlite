package com.tinyappco.deadlines

import android.widget.DatePicker
import java.util.Calendar
import java.util.Date

fun DatePicker.date() : Date {
    val cal = Calendar.getInstance()
    cal.set(year,month,dayOfMonth)
    return cal.time
}