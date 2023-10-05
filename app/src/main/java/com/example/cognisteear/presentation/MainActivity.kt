package com.example.cognisteear.presentation

import android.Manifest
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.cognisteear.R
import com.example.cognisteear.presentation.theme.CogniSteearTheme
import kotlin.math.pow

class MainActivity : ComponentActivity() {

    private val distanceState = mutableDoubleStateOf(0.0) // Define distanceState here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check for Bluetooth permission
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.BLUETOOTH),
                0
            )
        }

        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter = bluetoothManager.adapter
        val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner

        val leScanCallback: ScanCallback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                super.onScanResult(callbackType, result)
                if (result.device.address == "f47ac10b-58cc-4372-a567-0e02b2c3d479") {
                    val rssi = result.rssi
                    val txPower = -59  // Replace with your device's calibrated TxPower
                    val distance = calculateDistance(rssi, txPower)
                    distanceState.doubleValue = distance
                    // TODO: Update your UI here
                }
            }
        }

        bluetoothLeScanner.startScan(leScanCallback)

        setContent {
            MainContent()
        }
    }

    @Composable
    fun MainContent() {
        WearApp("Android", distanceState)
    }

    private fun calculateDistance(rssi: Int, txPower: Int): Double {
        return 10.0.pow((txPower - rssi) / 20.0)
    }
}

@Composable
fun WearApp(greetingName: String, distance: MutableState<Double>) {
    CogniSteearTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Estimated Distance: ${distance.value} meters"
            )
            Greeting(greetingName = greetingName)
        }
    }
}

@Composable
fun Greeting(greetingName: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        text = stringResource(R.string.hello_world, greetingName)
    )
}
