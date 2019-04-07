package Model

import org.json.JSONObject
import Extensions.iterator

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

    fun getCurrentDailyWeatherFromJson(response: JSONObject):ArrayList<Day>{

        val dailyJSON = response.getJSONObject(daily)

        val dayJSONArray = dailyJSON.getJSONArray(data)

        val days = ArrayList<Day> ()

        /*for (i in 0..dayJSONArray.length()){

            val dayJSONObject = dayJSONArray.getJSONObject(i)

            val minTemp = dayJSONObject.getDouble(temperatureMin)

            val maxTemp = dayJSONObject.getDouble(temperatureMax)

            val time  = dayJSONObject.getLong(time)

            days.add(Day(time, minTemp, maxTemp))

        }*/

        //funcion de extension mejorada
        for(JsonDay in dayJSONArray) {

            val minTemp = JsonDay.getDouble(temperatureMin)

            val maxTemp = JsonDay.getDouble(temperatureMax)

            val time = JsonDay.getLong(time)

            days.add(Day(time, minTemp, maxTemp))
        }
        return days
    }
}