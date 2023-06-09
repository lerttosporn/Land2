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
    private var beforButton = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.okButton.setOnClickListener {
            getWeather(binding.nameCity.text.toString())
//            Log.i("data", binding.nameCity.text.toString())
        }
        binding.buttonC.setOnClickListener {
            changeTemp(true)
        }
        binding.buttonK.setOnClickListener {
            changeTemp(false)
        }
    }
    private var temp: Double? = null
    private fun changeTemp(bool: Boolean) {
//        K->C
        if (temp != null) {
            if (bool && beforButton) {
                temp = temp!! - 273
                beforButton = false
                binding.temp.text = getString(
                    R.string.Temperature,
                    NumberFormat.getNumberInstance().format(temp) + "C"
                )
            }
//        C->K
            else if (!bool && !beforButton) {
                temp = temp!! + 273
                beforButton = true
                binding.temp.text = getString(
                    R.string.Temperature,
                    NumberFormat.getNumberInstance().format(temp) + "K"
                )
            }
            Log.i("tttttttttttttt", "$temp")
        } else {
            Toast.makeText(
                applicationContext,
                "Pls. put you city",
                Toast.LENGTH_SHORT
            ).show()
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
                    temp = data.main.temp.toString().toDouble()
                    binding.city.text = getString(R.string.City, data.name)
                    binding.temp.text =
                        getString(R.string.Temperature, data.main.temp.toString() + "K")
                    binding.humidity.text =
                        getString(R.string.Humidity, data.main.humidity.toString())
                } else {
                    Log.i("APIiiii", data.toString())
                    temp = data?.main?.temp?.toString()?.toDouble()
                    Toast.makeText(
                        applicationContext,
                        "I don't have $city city",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                beforButton = true
            }
        })
    }
}