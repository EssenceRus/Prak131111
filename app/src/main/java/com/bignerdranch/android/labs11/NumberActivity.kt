package com.bignerdranch.android.labs11

import android.R
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.room.Room
import com.bignerdranch.android.labs11.data.DATABASE_NAME
import com.bignerdranch.android.labs11.data.NumberDAO
import com.bignerdranch.android.labs11.data.NumberDatabase
import com.bignerdranch.android.labs11.data.model.GroupsTable
import com.bignerdranch.android.labs11.data.model.NumberTable
import com.bignerdranch.android.labs11.databinding.ActivityNumberBinding
import java.util.*
import java.util.concurrent.Executors

private lateinit var binding: ActivityNumberBinding
private lateinit var db: NumberDatabase
private lateinit var numberDAO: NumberDAO
private var groupsTable: MutableList<GroupsTable> = mutableListOf()
private var numberTable: MutableList<NumberTable> = mutableListOf()
private var groups: MutableList<String> = mutableListOf()



private val Num : MutableList<NumberTable> = mutableListOf()
private val Groups : MutableList<GroupsTable> = mutableListOf()


private var index: Int = -1
private var GroupsId: Int = 0
private var id: Int = 0
class WeatherActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNumberBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        db = Room.databaseBuilder(this,NumberDatabase::class.java, DATABASE_NAME).build()
        numberDAO = db.numberDAO()


        var spinnerAdapter: ArrayAdapter<String>
        val GroupsT = numberDAO.getAllGroups()
        GroupsT.observe(this){
            var tempGroups = mutableListOf<String>()
            it.forEach{
                tempGroups.add(it.group)
                spinnerAdapter = ArrayAdapter<String>(this,R.layout.simple_list_item_1, tempGroups)
                binding.spinner.adapter = spinnerAdapter
            }


        }






        index = intent.getIntExtra("index",-1)
        GroupsId  = intent.getIntExtra("id",0)







        if(index!=-1)
        {
            db = Room.databaseBuilder(this,NumberDatabase::class.java, DATABASE_NAME).build()
            numberDAO = db.numberDAO()
            binding.delButton.isVisible = true
            DbGet()
            val Numbers = numberDAO.getNums(GroupsId)
            Numbers.observe(this){
                binding.btn03.setText("Изменить")
                it.forEach{
                    binding.editTextTextPersonName2.setText(it.name)
                    binding.editTextTextPersonName3.setText(it.number)
                    val name = it.name.split(".")

                    binding.spinner.setSelection(it.groupsId)
                    id = it.id

                }

            }


        }
        else{
            binding.delButton.isVisible = false
        }
        binding.btn03.setOnClickListener {
            db = Room.databaseBuilder(this,NumberDatabase::class.java, DATABASE_NAME).build()
            numberDAO = db.numberDAO()
            if(index==-1) {
                val Name:String =binding.editTextTextPersonName2.text.toString()
                val Number:String =binding.editTextTextPersonName3.text.toString()

                DbGet()
                val executor = Executors.newSingleThreadExecutor()
                executor.execute {


                    numberDAO.addNumber(
                        NumberTable(0,
                        binding.spinner.selectedItemPosition,
                        Name,
                            Number
                        ))

                }

            }
            else if(index!=-1){
                val Name:String =binding.editTextTextPersonName2.text.toString()
                val Number:String =binding.editTextTextPersonName3.text.toString()
                val executor = Executors.newSingleThreadExecutor()
                executor.execute {


                    numberDAO.saveNumber(NumberTable(id,
                        binding.spinner.selectedItemPosition,
                        Name,
                        Number
                    ))
                }

            }

            binding.editTextTextPersonName2.setText("")
            binding.editTextTextPersonName3.setText("")

        }
        binding.delButton.setOnClickListener{
            val Name:String =binding.editTextTextPersonName2.text.toString()
            val Number:String =binding.editTextTextPersonName3.text.toString()
            val exec = Executors.newSingleThreadExecutor()
            exec.execute {
                numberDAO.killNumber(NumberTable(id,
                    binding.spinner.selectedItemPosition,
                    Name,
                    Number
                ))
                   // DayTemp.toInt(),
                   // NightTemp.toInt()))
            }
            val intent = Intent(this, OutputActivity::class.java)
            startActivity((intent))
        }


    }
    fun DbGet()
    {
        db = Room.databaseBuilder(this,NumberDatabase::class.java, DATABASE_NAME).build()
        numberDAO = db.numberDAO()
        Num.clear()
        Groups.clear()
        val numbers = numberDAO.getAllNumber()
        val GroupsT = numberDAO.getAllGroups()
        GroupsT.observe(this){
            groupsTable.addAll(it)
            //cities.addAll(tempCities)

        }

        numbers.observe(this){
            numberTable.addAll(it)
        }
    }


}