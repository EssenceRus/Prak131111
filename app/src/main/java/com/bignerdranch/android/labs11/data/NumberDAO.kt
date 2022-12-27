package com.bignerdranch.android.labs11.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bignerdranch.android.labs11.data.model.GroupsTable
import com.bignerdranch.android.labs11.data.model.NumberTable

@Dao

interface NumberDAO {

    @Query("SELECT * FROM $GROUPS_TABLE")
    fun getAllGroups(): LiveData<List<GroupsTable>>


    @Insert
    fun addGroup(groupsTable: GroupsTable)

    @Update
    fun saveGroups(groupsTable: GroupsTable)

    @Delete
    fun killGroups(groupsTable: GroupsTable)


    @Query("SELECT * FROM $NUMBER_TABLE")
    fun getAllNumber(): LiveData<List<NumberTable>>

    @Query("SELECT * FROM $NUMBER_TABLE WHERE _id=:id")
    fun getNums(id: Int): LiveData<List<NumberTable>>

    @Insert
    fun addNumber(numberTable: NumberTable)

    @Update
    fun saveNumber(numberTable: NumberTable)

    @Delete
    fun killNumber(numberTable: NumberTable)




}