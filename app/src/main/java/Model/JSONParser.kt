package Model

import org.json.JSONObject

class JSONParser {
    var TimeZone = ""
    fun getCurrentWeatherFromJson(response: JSONObject):CurrentWeather {

        val currentJSON = response.getJSONObject(currently)
        TimeZone = response.getString(timezone)

        with(currentJSON){
            val currentWeather = CurrentWeather(icon =    getString(icon),
                                                summary = getString(summary),
                                                temp =    getDouble(temperature),
                                                precip =  getDouble(precipProbability))
            return currentWeather
        }

    }
}