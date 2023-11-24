package com.devdroiddev.flows

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.devdroiddev.flows.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Flow

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    companion object {
        const val APP_TAG = "Flows_App"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Implement Flows
        producer()

        // Each Flow can have multiple Consumer
        consumer()
    }

    private fun consumer() {

        //  Consumer - 1
        GlobalScope.launch {
            val data = producer()
            data.collect{
                // Clear the previous text
                withContext(Dispatchers.Main) {
          /*          binding.countTv.text = ""
                    binding.countTv.text = it.toString()*/
                    Log.d(APP_TAG, "Consumer 1 -> ${it.toString()}")
                }
            }
        }

        // Consumer - 2
        GlobalScope.launch {
            val data = producer()
            delay(2000)
            data.collect{
                // Clear the previous text
                withContext(Dispatchers.Main) {
              /*      binding.countTv.text = ""
                    binding.countTv.text = it.toString()*/
                    Log.d(APP_TAG, "Consumer 2 -> ${it.toString()}")
                }

            }
        }
    }

    private fun producer() = flow<Int>{
        val list = listOf<Int>(1, 2, 3, 4, 5)
        list.forEach{
            delay(1000)
            emit(it)
        }
    }


}
