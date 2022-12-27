package com.bignerdranch.android.labs11.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bignerdranch.android.labs11.data.GROUPS_TABLE

@Entity(tableName = GROUPS_TABLE)
data class GroupsTable(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id:Int,
    @ColumnInfo(index = true)
    var group:String
)



