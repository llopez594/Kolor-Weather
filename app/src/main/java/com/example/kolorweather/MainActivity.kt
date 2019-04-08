
package com.example.kolorweather

import Extensions.action
import Extensions.displaySnack
import Model.*
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.simpleName
    //lateinit var days:ArrayList<Day> //lateinit permite definir una variable despues para evitar que sea null
    var days:ArrayList<Day> = ArrayList()

    var hours:ArrayList<Hour> = ArrayList()

    //en vez de usar public static final String variable, ya que kotlin no tiene variables
    //estaticas usamos companion object y definimoz variables sin instancearlas
    companion object {
        val DAILY_WEATHER = "DAILY_WEATHER"

        val HOURLY_WEATHER = "HOURLY_WEATHER"
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
            Response.Listener<String> { //response -> val responseJSON = JSONObject(response)

                val responseJSON = JSONObject(it)

                val currentWeather = getCurrentWeatherFromJSON(responseJSON)

                days = getCurrentDailyWeatherFromJSON(responseJSON)

                hours = getHourlyWeatherFromJSON(responseJSON)

                buildCurrentWeatherUI(currentWeather, TimeZone)
            },
            Response.ErrorListener {
                //Log.d(TAG, " ¡Eso no funcionó! ")
                displayErrorMessage()
            })
        queue.add(stringRequest)
    }

    private fun displayErrorMessage() {
        //se puede llamar a un metodo con la forma lambda
        main.displaySnack(getString(R.string.error_network), Snackbar.LENGTH_INDEFINITE){
            action(getString(R.string.retry)){ getWeather() }
        }
        /*val snackbar = Snackbar.make(main, R.string.error_network, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry, { getWeather() })
        snackbar.show()*/

    }

    private fun buildCurrentWeatherUI(currentWeather: CurrentWeather, timezone: String) {
       with(currentWeather){
           //tempTextView.text = "$temp °C"
           //precipTextView.text = "$precip %"

           //se puede cargar el recurso STRING en tiempo de ejecucion.
           tempTextView.text = getString(R.string.grade_default, temp)
           precipTextView.text = getString(R.string.percentage_humidity_default, precip*100)

           DescripcionTextView.text = summary
           iconImageView.setImageDrawable(ResourcesCompat.getDrawable(resources,
                                            getIconResource(),
                                            null))
           locazationTextView.text = timezone.replace("/", ", ")
       }

    }

    //utilizar .apply nos ayuda a modificar un atributo sin usar .setText()
    fun startHourlyActivity(view: View){
        val intent = Intent()
        intent.setClass(this, HourlyWeatherActivity::class.java).apply {
            putParcelableArrayListExtra(HOURLY_WEATHER, hours)
        }
        startActivity(intent)
    }

    //utilizar .apply nos ayuda a modificar un atributo sin usar .setText()
    fun startDailyActivity(view: View){
        val intent = Intent(this, DailyWeatherActivity::class.java).apply {
            putParcelableArrayListExtra(DAILY_WEATHER, days)
        }
        //intent.putParcelableArrayListExtra("DAILY_WEATHER", days)
        startActivity(intent)
    }

}
