package com.bignerdranch.android.labs11.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bignerdranch.android.labs11.data.model.GroupsTable
import com.bignerdranch.android.labs11.data.model.NumberTable

@Database(entities = [GroupsTable::class, NumberTable::class],version = 1)
@TypeConverters(DatesConverter::class)
abstract class NumberDatabase: RoomDatabase() {
    abstract fun numberDAO(): NumberDAO
}
