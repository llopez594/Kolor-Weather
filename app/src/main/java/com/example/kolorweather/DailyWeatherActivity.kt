package com.example.kolorweather

import Model.Day
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class DailyWeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_weather)

        //con let se ejecutara solo si no es NULL
        intent.let{
            //utilizamon it porq el llama a su objeto llamado por let
            val days:ArrayList<Day> = it.getParcelableArrayListExtra(MainActivity.DAILY_WEATHER)

            Toast.makeText(this, days.get(0).getFormattedTime(), Toast.LENGTH_LONG).show()
        }
    }
}
