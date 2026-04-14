package com.example.myapplication

import android.Manifest
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.createGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private val accelerometerState = mutableStateOf(Triple(0f, 0f, 0f))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
        NotificationHelper.createChannel(this)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "front"
            ) {

                composable("front") {
                    FrontPage(
                        onNextPage = { navController.navigate("second") },
                        onSendNotification = {
                            NotificationHelper.sendTestNotification(this@MainActivity)
                        },
                        sensorManager = sensorManager,
                        accelerometerValues = accelerometerState
                    )
                }

                composable("second") {
                    SecondActivity(
                        onGoBack = { navController.navigate("front") },
                        onSendNotification = {
                            NotificationHelper.sendTestNotification(this@MainActivity)
                        }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.also { sensor ->
            sensorManager.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]
        accelerometerState.value = Triple(x, y, z)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

}






