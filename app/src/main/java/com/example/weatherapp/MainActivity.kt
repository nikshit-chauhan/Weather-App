package com.example.weatherapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_day_main.*
import org.json.JSONObject
import kotlin.math.ceil


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_main)
        val lat = intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")
        getJsonData(lat,long)
        
    }

    private fun getJsonData(lat: String?, long: String?){
        val API_KEY = "5e02eb37a56b621ba3946228bd02411a"
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${API_KEY}"
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null, { response ->
                setValues(response)
            },
            { Log.d("Main Activity", "Error") })

         queue.add(jsonRequest)
    }


    private fun setValues(response: JSONObject) {

            tv_city_name.text = response.getString("name")

            var lat = response.getJSONObject("coord").getString("lat")
            var long = response.getJSONObject("coord").getString("lon")
            tv_lat_long.text = "${lat} , ${long}"

            tv_weather.text = response.getJSONArray("weather").getJSONObject(0).getString("main")

            var temp = response.getJSONObject("main").getString("temp")
            temp = (((temp).toFloat()-273.15)).toInt().toString()
            tv_temperature.text = "${temp}°C"

            var minTemp = response.getJSONObject("main").getString("temp_min")
            minTemp = (((minTemp).toFloat()-273.15)).toInt().toString()
            tv_min_temp.text = "${minTemp}°C"

            var maxTemp = response.getJSONObject("main").getString("temp_max")
            maxTemp = (ceil((maxTemp).toFloat()-273.15)).toInt().toString()
            tv_max_temp.text = "${maxTemp}°C"

            tv_pressure.text = response.getJSONObject("main").getString("pressure")

            tv_humidity.text = response.getJSONObject("main").getString("humidity")+"%"

            tv_wind_speed.text = response.getJSONObject("wind").getString("speed")

            tv_wind_angle.text = "Degree : " + response.getJSONObject("wind").getString("deg") + "°"

        if(response.getJSONObject("wind").getString("gust") != null){
                tv_gust.text = "Gust : " + response.getJSONObject("wind").getString("gust")
            }else{
                tv_gust.text = "0"
        }
    }


}