package com.bignerdranch.android.labs11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.room.Room
import com.bignerdranch.android.labs11.data.DATABASE_NAME
import com.bignerdranch.android.labs11.data.NumberDAO
import com.bignerdranch.android.labs11.data.NumberDatabase
import com.bignerdranch.android.labs11.data.model.GroupsTable
import java.util.concurrent.Executors


class GroupsAddActivity : AppCompatActivity() {
    private lateinit var btnInput: Button
    private lateinit var addGroup: EditText
    private lateinit var db: NumberDatabase
    private lateinit var numberDAO: NumberDAO
    private var groupsTable: MutableList<GroupsTable> = mutableListOf()
    private var groups: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groups_add)
        btnInput = findViewById(R.id.button5)
        addGroup = findViewById(R.id.editTextTextPersonName4)
        db = Room.databaseBuilder(this,NumberDatabase::class.java, DATABASE_NAME).build()
        numberDAO = db.numberDAO()

        val executor = Executors.newSingleThreadExecutor()

        val groupsType = numberDAO.getAllGroups()
        groupsType.observe(this){
            groupsTable.addAll(it)
            it.forEach{
                groups.add(it.group)

            }
        }

        btnInput.setOnClickListener{
            if(addGroup.text.isNotEmpty()) {


                executor.execute {
                    numberDAO.addGroup(GroupsTable(0,addGroup.text.toString()))
                }
                val groupsType = numberDAO.getAllGroups()
                groupsType.observe(this) {
                    groups.clear()
                    it.forEach {
                        groups.add(it.group)
                    }
                }
                addGroup.setText("")
            }
        }

    }
}