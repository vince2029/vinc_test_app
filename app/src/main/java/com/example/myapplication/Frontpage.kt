package com.example.myapplication
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow

@Composable
fun FrontPage(
    onNextPage: () -> Unit,
    onSendNotification: () -> Unit,
    sensorManager: SensorManager,
    accelerometerValues: State<Triple<Float, Float, Float>>,
    viewModel: Screenflippersistantcounter = viewModel()
) {
    val counter by viewModel.counter.collectAsState()
    val bgColor by viewModel.bgColor.collectAsState()
    var sensors by remember { mutableStateOf<List<Sensor>>(emptyList()) }
    var isCounting by remember { mutableStateOf(false) }

    val (x, y, z) = accelerometerValues.value

    LaunchedEffect(isCounting) {
        if (isCounting) {
            viewModel.startCountdown()
            isCounting = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("Accelerometer")
        Text("X = $x")
        Text("Y = $y")
        Text("Z = $z")

        Text(text = counter.toString())

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { isCounting = true }) {
            Text("Start Countdown")
        }

        Spacer(modifier = Modifier.height(20.dp))



        Button(onClick = {
            sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
            println(sensors)
        }) {
            Text("yay sensors")
        }

        LazyColumn {
            items(sensors) { sensor ->
                Text(sensor.name)
            }
        }

        Button(onClick = onNextPage) {
            Text("Next Page")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            Button(onClick = { viewModel.setBackground(Color.Black) }) {
                Text("Black BG")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = { viewModel.setBackground(Color.White) }) {
                Text("White BG")
            }
        }

        Button(onClick = onSendNotification) {
            Text("test_notif")
        }
    }
}






