package com.devdroiddev.flows

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.devdroiddev.flows.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Flow
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
        GlobalScope.launch(Dispatchers.Main) {
            val time = measureTimeMillis {
                producer()
                    // Use buffering strategy
                    .buffer(3)
                    .collect {
                        delay(2000) // takes to process the data
                        // Clear the previous text
                        binding.countTv.text = ""
                        binding.countTv.text = it.toString()
                        Log.d(APP_TAG, "Consumer 1 -> ${it.toString()}")
                    }
            }
            Log.d(APP_TAG, "Time - $time")
        }

    }

    private fun producer() = flow<Int> {
        val list = listOf<Int>(1, 2, 3, 4, 5)
        list.forEach {
            delay(1000)
            emit(it)
        }
    }

}
