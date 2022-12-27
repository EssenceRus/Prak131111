package com.bignerdranch.android.labs11.data.model

import android.provider.BaseColumns
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bignerdranch.android.labs11.data.NUMBER_TABLE
import java.util.*

@Entity(tableName = NUMBER_TABLE)
class NumberTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    @NonNull
    val id:Int,
    var groupsId: Int,
    var name: String,
    var number: String,
)

