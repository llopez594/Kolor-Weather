package com.example.kolorweather

import Adapters.HourAdapter
import Model.Hour
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_hourly_weather.*

class HourlyWeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hourly_weather)

        //para RecyclerView necesita un layoutManager
        HourlyRecyclerView.layoutManager = LinearLayoutManager(this)

        intent.let{
            val hours:ArrayList<Hour> = it.getParcelableArrayListExtra(MainActivity.HOURLY_WEATHER)

            val baseAdapter = HourAdapter(hours)

            HourlyRecyclerView.adapter = baseAdapter
            //Toast.makeText(this, hours[0].toString(), Toast.LENGTH_LONG).show()
        }
    }
}
