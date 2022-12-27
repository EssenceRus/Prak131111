package com.bignerdranch.android.labs11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bignerdranch.android.labs11.data.DATABASE_NAME
import com.bignerdranch.android.labs11.data.NumberDAO
import com.bignerdranch.android.labs11.data.NumberDatabase
import com.bignerdranch.android.labs11.data.model.GroupsTable
import com.bignerdranch.android.labs11.data.model.NumberTable

class OutputActivity : AppCompatActivity() {
    private val Num : MutableList<NumberTable> = mutableListOf()
    private val Cities : MutableList<GroupsTable> = mutableListOf()

    private var index: Int = -1
    private lateinit var db: NumberDatabase
    private lateinit var numberDAO: NumberDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_output)

        db = Room.databaseBuilder(this,NumberDatabase::class.java, DATABASE_NAME).build()
        numberDAO = db.numberDAO()
        DataBasetoList()
        updateInfo()





    }
    fun DataBasetoList(){
        Num.clear()
        Cities.clear()
        val Nums = numberDAO.getAllNumber()
        val GroupsT = numberDAO.getAllGroups()
        GroupsT.observe(this, androidx.lifecycle.Observer {
            it.forEach {
                Cities.add(GroupsTable(it.id,it.group, ))

            }
            updateInfo()
        })
        Nums.observe(this, androidx.lifecycle.Observer {
            it.forEach {
                Num.add(NumberTable(it.id,it.groupsId,it.name,it.number))

            }
            updateInfo()
        })
    }
    override fun onResume() {
        super.onResume()
        if(index != -1)
        {
            DataBasetoList()
            updateInfo()
        }

    }
    fun updateInfo(){
        val rv = findViewById<RecyclerView>(R.id.recylerView)
        val adapter = NumberRVAdapter(this, Num,Cities)
        val rvListener = object : NumberRVAdapter.ItemClickListener{
            override fun onItemClick(view: View?, position: Int) {
                val intent = Intent(this@OutputActivity, WeatherActivity::class.java)
                intent.putExtra("index", position)
                intent.putExtra("id", Num[position].id)
                index = position
                startActivity(intent)
            }
        }

        adapter.setOnClickListener(rvListener)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }

}