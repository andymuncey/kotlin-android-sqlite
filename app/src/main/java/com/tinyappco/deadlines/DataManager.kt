package com.tinyappco.deadlines

import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DataManager (context: Context) {

    private val db : SQLiteDatabase = context.openOrCreateDatabase("Assessment", Context.MODE_PRIVATE, null)

init{
    val modulesCreateQuery = "CREATE TABLE IF NOT EXISTS `Modules` ( `Code` TEXT NOT NULL, `Name` TEXT NOT NULL, PRIMARY KEY(`Code`) )"
    val assignmentsCreateQuery = "CREATE TABLE IF NOT EXISTS `Assignments` ( `Id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `Title` TEXT NOT NULL, `Weight` INTEGER NOT NULL, `Deadline` INTEGER, `ModuleCode` TEXT )"
    db.execSQL(modulesCreateQuery)
    db.execSQL(assignmentsCreateQuery)
}

    fun add(module: Module) : Boolean{
        if (module(module.code) == null) {
            val query =
                "INSERT INTO Modules (code, name) VALUES ('${module.code}', '${module.name}')"
            db.execSQL(query)
            return true
        }
        return false
    }

    fun allModules() : List<Module>{

        val modules = mutableListOf<Module>()

        val cursor = db.rawQuery("SELECT * FROM Modules", null)
        if (cursor.moveToFirst()) {
            do {
                val code = cursor.getString(cursor.getColumnIndexOrThrow("Code"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("Name"))
                val module = Module(code,name)
                modules.add(module)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return modules.sorted()
    }

    private fun module(code: String) : Module? {
        val query = "SELECT * FROM Modules WHERE Code='$code'"
        val cursor = db.rawQuery(query,null)
        return if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow("Code"))
            cursor.close()
            Module(code,name)
        } else {
            cursor.close()
            null
        }
    }

}