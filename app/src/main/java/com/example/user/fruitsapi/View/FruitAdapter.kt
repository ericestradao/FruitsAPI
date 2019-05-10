package com.example.user.fruitsapi.View

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.user.fruitsapi.Model.Fruit
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.user.fruitsapi.R
import kotlinx.android.synthetic.main.item_fruit.view.*


class FruitAdapter (private val dataList : Fruit,
                    private val listener : Listener) :
    RecyclerView.Adapter<FruitAdapter.ViewHolder>(){

    interface Listener{
        fun onItemClick(fruit: Fruit, position: Int)
    }
    private val colors : Array<String> = arrayOf("#EF5350", "#EC407A", "#AB47BC", "#7E57C2", "#5C6BC0", "#42A5F5")

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList, listener, colors, position)
    }

    override fun getItemCount(): Int = dataList.fruit.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_fruit, parent, false)
        return ViewHolder(view)

    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bind(fruit: Fruit, listener: Listener, colors:
        Array<String>, position: Int){
            itemView.tv_type.text = fruit.fruit.get(adapterPosition).type
            itemView.setBackgroundColor(Color.parseColor(colors[position % 6]))
            itemView.setOnClickListener{
                listener.onItemClick(fruit, position)
            }
        }
    }
}