package com.bignerdranch.android.labs11

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.labs11.data.model.GroupsTable
import com.bignerdranch.android.labs11.data.model.NumberTable


class NumberRVAdapter(context: Context?, val data: MutableList<NumberTable>, val groups: MutableList<GroupsTable>): RecyclerView.Adapter<NumberRVAdapter.NumberViewHolder?>() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    private var iClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val view: View = layoutInflater.inflate(R.layout.item_number,parent,false)
        return NumberViewHolder(view)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        val item = data[position]
        val itemc = groups[data[position].groupsId]
        holder.name.text = item.name
        holder.number.text = item.number
        //holder.groupsId.text = item.groupsId.toString()
        holder.Group.text = itemc.group
    }

    override fun getItemCount(): Int = data.size
    inner class NumberViewHolder(item: View): RecyclerView.ViewHolder(item), View.OnClickListener {
        var name = item.findViewById<TextView>(R.id.DateTime)
        var number = item.findViewById<TextView>(R.id.Day_Temp)
        //var groupsId = item.findViewById<TextView>(R.id.Night_Temp)
        var Group = item.findViewById<TextView>(R.id.City)
        init{
            itemView.setOnClickListener(this)
        }
        override fun onClick(view: View?){
            iClickListener?.onItemClick(view,adapterPosition)
        }
    }
    fun setOnClickListener(itemClickListener: ItemClickListener?){
        iClickListener = itemClickListener
    }
    interface ItemClickListener {
        fun onItemClick(view: View?,position: Int)
    }
}