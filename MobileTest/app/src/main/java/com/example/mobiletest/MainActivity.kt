package com.example.mobiletest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mobiletest.data.WeCity
import com.example.mobiletest.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.okButton.setOnClickListener {
            getWeather(binding.nameCity.text.toString())
            Log.i("data", binding.nameCity.text.toString())
        }
        binding.buttonC.setOnClickListener{
            changeTemp(true)
        }
        binding.buttonK.setOnClickListener{
            changeTemp(false)
        }
    }
    fun changeTemp(bool:Boolean){
        if(bool){
            binding.temp.text = getString(R.string.Temperature,
                NumberFormat.getNumberInstance().format(data.main.temp.toString()))
        }
        else{
            binding.temp.text = getString(R.string.Temperature,data.main.temp.toString())

        }
    }
    private fun getWeather(city: String) {

        val call = ApiService.retrofitBuild().getWeather(city)

        call.enqueue(object : Callback<WeCity> {
            override fun onFailure(call: Call<WeCity>, t: Throwable) {
                Log.e("API ERROR!!!!!", t.message.toString())
            }

            override fun onResponse(call: Call<WeCity>, response: Response<WeCity>) {
                val data = response.body()
                if (response.isSuccessful && data != null) {
                    Log.i("API", data.toString())
                    binding.city.text = getString(R.string.City,data.name)
                    binding.temp.text = getString(R.string.Temperature,data.main.temp.toString())
                    binding.humidity.text = getString(R.string.Humidity,data.main.humidity.toString())
                } else {
//                    Toast.makeText(, "cityname", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}