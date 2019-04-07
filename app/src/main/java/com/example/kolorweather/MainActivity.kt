
package com.example.kolorweather

import Model.*
import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.res.ResourcesCompat
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.simpleName
    val JsonParser = JSONParser()
    lateinit var days:ArrayList<Day>

    companion object {
        val DAILY_WEATHER = "DAILY_WEATHER"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tempTextView.text = getString(R.string.grade_default, 0.0)
        precipTextView.text = getString(R.string.percentage_humidity_default, 0.0)

        getWeather()
    }

    private fun getWeather() {
        val latitud = 8.3512200
        val longitud = -62.6410200
        val language = getString(R.string.language)
        val units = getString(R.string.units)

        val URL = "$DARK_SKY_URL/$API_KEY/$latitud,$longitud?lang=$language&units=$units"

        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, URL,
            Response.Listener<String> { response ->
                val responseJSON = JSONObject(response)

                val currentWeather = JsonParser.getCurrentWeatherFromJson(responseJSON)

                days = JsonParser.getCurrentDailyWeatherFromJson(responseJSON)

                buildCurrentWeatherUI(currentWeather, JsonParser.TimeZone)
            },
            Response.ErrorListener {
                //Log.d(TAG, " ¡Eso no funcionó! ")
                displayErrorMessage()
            })
        queue.add(stringRequest)
    }

    private fun displayErrorMessage() {
        val snackbar = Snackbar.make(main, R.string.error_network, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry, { getWeather() })
        snackbar.show()
    }

    private fun buildCurrentWeatherUI(currentWeather: CurrentWeather, timezone: String) {
       with(currentWeather){
           //tempTextView.text = "$temp °C"
           //precipTextView.text = "$precip %"
           tempTextView.text = getString(R.string.grade_default, temp)
           precipTextView.text = getString(R.string.percentage_humidity_default, precip*100)

           DescripcionTextView.text = summary
           iconImageView.setImageDrawable(ResourcesCompat.getDrawable(resources,
                                            getIconResource(),
                                            null))
           locazationTextView.text = timezone.replace("/", ", ")
       }

    }

    fun startHourlyActivity(view: View){

        val intent = Intent()
        intent.setClass(this, HourlyWeatherActivity::class.java)
        startActivity(intent)
    }

    fun startDailyActivity(view: View){
        val intent = Intent(this, DailyWeatherActivity::class.java).apply {
            putParcelableArrayListExtra(DAILY_WEATHER, days)
        }
        //intent.putParcelableArrayListExtra("DAILY_WEATHER", days)
        startActivity(intent)
    }

}
