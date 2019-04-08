package Model

import com.example.kolorweather.R

//data antes del class nos modifica la clase a una clase de datos, en ves de
//crear un metodo toString() usamos el data class directamente
data class CurrentWeather(var icon:String, var summary:String, var temp:Double, var precip:Double) {

    fun getIconResource():Int{
        when(icon){
            "clear-night"         -> return R.drawable.clear_night
            "clear-day"           -> return R.drawable.clear_day
            "cloudy"              -> return R.drawable.cloudy
            "cloudy-night"        -> return R.drawable.cloudy_night
            "fog"                 -> return R.drawable.fog
            "partly-cloudy"       -> return R.drawable.partly_cloudy
            "partly-cloudy-night" -> return R.drawable.cloudy_night
            "rain"                -> return R.drawable.rain
            "sleet"               -> return R.drawable.sleet
            "snow"                -> return R.drawable.snow
            "sunny"               -> return R.drawable.sunny
            "wind"                -> return R.drawable.wind
            "partly-cloudy-day"   -> return R.drawable.cloudy
            else                  -> return R.drawable.na
        }
    }
}