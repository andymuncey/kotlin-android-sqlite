package com.tinyappco.deadlines

import java.io.Serializable

class Module (val code: String, var name: String) : Serializable, Comparable<Module> {
    override fun compareTo(other: Module): Int {
        return code.compareTo(other.code)
    }

    override fun toString(): String {
        return "$code: $name"
    }

}