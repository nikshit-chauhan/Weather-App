package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lat = intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")
        Toast.makeText(this, "lat: $lat ---long: $long", Toast.LENGTH_LONG).show()
        getJsonData(lat,long)

        
    }

    private fun getJsonData(lat: String?, long: String?){
        val API_KEY = "5e02eb37a56b621ba3946228bd02411a"
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/forecast?lat=${lat}&lon=${long}&appid=${API_KEY}"
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null, { response ->
                Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show()
            },
            { Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show() })

         queue.add(jsonRequest)
    }


}