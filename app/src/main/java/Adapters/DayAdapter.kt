package Adapters

import Model.Day
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.kolorweather.R

class DayAdapter(val datasource:ArrayList<Day>): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val viewHolder:ViewHolder
        
        val view:View
        
        if(convertView == null){
            view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.daily_item, parent, false)
            //view = parent.inflate(R.layout.daily_item)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            viewHolder = convertView.tag as ViewHolder
            view = convertView
        }

        val currentDay = datasource[position]

        viewHolder.apply {
            dayTextView.text = currentDay.getFormattedTime()
            minTextView.text = "Min ${currentDay.minTemp.toInt()} °C"
            maxTextView.text = "Max ${currentDay.maxTemp.toInt()} °C"
        }
        return view
    }

    override fun getItem(position: Int): Any {
        return datasource[position]
    }

    override fun getItemId(position: Int): Long {
        return 0//no lo necesitaremos
    }

    override fun getCount(): Int {

        return datasource.size
    }

    private class ViewHolder(view: View){

        val dayTextView:TextView = view.findViewById(R.id.DayTextView)
        val minTextView:TextView = view.findViewById(R.id.MinTextView)
        val maxTextView:TextView = view.findViewById(R.id.MaxTextView)

    }
}



