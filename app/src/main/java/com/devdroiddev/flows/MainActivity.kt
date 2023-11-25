package com.devdroiddev.flows

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.devdroiddev.flows.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
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

        // Flow is executing on main thread
        // Consumer - 1
        GlobalScope.launch(Dispatchers.Main) {
            val result = producer()
            Log.d(APP_TAG, "${result.toString()}")
            result.collect {
                delay(500)
                    // Clear the previous text
                    binding.countTv.text = ""
                    binding.countTv.text = it.toString()
                    Log.d(APP_TAG, "Consumer 1 - $it")
                }
        }

        // Consumer - 2
        GlobalScope.launch(Dispatchers.Main) {
            val result = producer()
            delay(2000)
            result.collect{
                binding.countTv.text = ""
                binding.countTv.text = it.toString()
                Log.d(APP_TAG, "Consumer 2 - $it")
            }
        }
    }

    private fun producer() : MutableSharedFlow<Int> {

        /*  Share Flow are of 2 types - MutableSharedFlow,   SharedFlow
        MutableSharedFlow - It can be changed
        SharedFlow - Read only
         */
        val mutableSharedFlow = MutableSharedFlow<Int>(replay = 1)
        GlobalScope.launch {
            val list = listOf<Int>(1, 2, 3, 4, 5)
            list.forEach {
                mutableSharedFlow.emit(it)
            }
        }
        return mutableSharedFlow
    }
}

