package com.tinyappco.deadlines

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Assignment(var id: Int?, var title:String, var weight: Int, var deadline: Date, var module:Module) : Serializable, Comparable<Assignment>{
    override fun compareTo(other: Assignment): Int {
        return deadline.compareTo(other.deadline)
    }

    override fun toString(): String {
        return "${deadline.shortDate()} ${module.code}: $title ($weight%)"
    }

}


fun Date.shortDate() : String{
    val formatter = SimpleDateFormat("dd/MM/yy", Locale.UK)
    return formatter.format(this)
}