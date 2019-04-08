package Adapters

import Model.Hour
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kolorweather.R
import kotlinx.android.synthetic.main.hourly_item.view.*


class HourAdapter(val days: ArrayList<Hour>): RecyclerView.Adapter<HourAdapter.HourViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder{

        val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.hourly_item, parent, false)

        return HourViewHolder(view)

        //return HourViewHolder(parent.inflate(R.layout.hourly_item))
    }


    override fun getItemCount(): Int = days.size

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) = holder.bind(days[position])


    class HourViewHolder(hourlyItemView: View): RecyclerView.ViewHolder(hourlyItemView){

        fun bind(hour: Hour) = with(itemView){

            hourlyTextView.text = hour.getFormattedTime()

            hourPropTextView.text = "${hour.precip.toInt().toString()} %"

            hourTempTextView.text = "${hour.temp.toInt().toString()} °C"

        }
    }
}