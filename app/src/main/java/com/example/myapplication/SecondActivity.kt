package com.example.myapplication

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SecondActivity( onGoBack: () -> Unit, onSendNotification: () -> Unit, viewModel: Screenflippersistantcounter = viewModel()) {
    val context = LocalContext.current

    var isBlack by remember { mutableStateOf(true) }
    val bgColor by viewModel.bgColor.collectAsState()
    val items by viewModel.items.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        PieChartComposable(onGoBack)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onSendNotification) {
            Text("test_notif")
        }

        Button(onClick = onGoBack) {
            Text("go back")
        }

        Button(onClick = { viewModel.addItem() }) {
            Text("Add Feature")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items) { item ->
                FeatureItem(
                    text = item,
                    onClick = {
                        Toast.makeText(context, "Feature clicked!", Toast.LENGTH_SHORT).show()
                        isBlack = !isBlack
                        if (isBlack) viewModel.setBackground(Color.Black) else viewModel.setBackground(Color.White)
                    }
                )
            }
        }
    }
}

@Composable
fun FeatureItem(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFF03DAC5))
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )
    }
}

@Composable
fun PieChartComposable(onGoBack: () -> Unit) {

    Button(onClick = onGoBack) {
        Text("go back")
    }

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        factory = { context ->
            PieChart(context).apply {
                val entries = listOf(
                    PieEntry(40f, "Red"),
                    PieEntry(30f, "Blue"),
                    PieEntry(20f, "Green"),
                    PieEntry(10f, "Yellow")
                )

                val dataSet = PieDataSet(entries, "My Chart")
                dataSet.colors = listOf(
                    "#BB86FC".toColorInt(),
                    "#6200EE".toColorInt(),
                    "#3700B3".toColorInt(),
                    "#03DAC5".toColorInt()
                )

                data = PieData(dataSet)
                invalidate()
            }
        }
    )
}




