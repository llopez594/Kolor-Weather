package Model

import org.json.JSONObject
import Extensions.iterator

var TimeZone = ""
fun getCurrentWeatherFromJSON(response: JSONObject):CurrentWeather {

    val currentJSON = response.getJSONObject(currently)
    TimeZone = response.getString(timezone)

    //se puede indicar a que variable se esta enviando la informacion
    with(currentJSON){
        val currentWeather = CurrentWeather(icon =    getString(icon),
                                            summary = getString(summary),
                                            temp =    getDouble(temperature),
                                            precip =  getDouble(precipProbability))
        return currentWeather
    }
}

fun getCurrentDailyWeatherFromJSON(response: JSONObject):ArrayList<Day>{

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

        val timeZone = response.getString(timezone)

        days.add(Day(time, minTemp, maxTemp, timeZone))
    }
    return days
}

fun getHourlyWeatherFromJSON(response: JSONObject):ArrayList<Hour>{

    val hourlyJSON = response.getJSONObject(hourly)

    val hourJSONArray = hourlyJSON.getJSONArray(data)

    val timeZone = response.getString(timezone)

    val hours = ArrayList<Hour> ()

    //funcion de extension mejorada
    for(JsonHour in hourJSONArray) {

        val time = JsonHour.getLong(time)

        val temperature = JsonHour.getDouble(temperature)

        val precipProb = JsonHour.getDouble(precipProbability)

        hours.add(Hour(time, temperature, precipProb, timeZone))
    }
    return hours
}