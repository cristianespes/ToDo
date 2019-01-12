package com.cristianespes.todo.data.repository.datasource.local

import androidx.room.TypeConverter
import java.util.*

// Indicamos a Room como convertir el tipo de datos que no conoce
class TypeConverters {
    @TypeConverter
    fun fromLongToDate(input: Long?): Date? = Date(input ?: 0)

    @TypeConverter
    fun fromDateToLong(input: Date?): Long? = input?.time
}