package com.example.blesample.presentation.view

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.blesample.presentation.MainViewModel

@Composable
fun OnOffBluetoothView() {
    val viewmodel = hiltViewModel<MainViewModel>()
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                viewmodel.fetchBluetoothNameState()
                viewmodel.fetchBluetoothAdapterName()
            }
        }
    Button(onClick = {
        viewmodel.turnOnOffBluetooth {
            launcher.launch(it)
        }
    }) {
        val statusName = if (viewmodel.bluetoothStatusState.collectAsState().value) "OFF" else "ON"
        Text("Turn $statusName Bluetooth")
    }
}