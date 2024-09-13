package com.example.blesample.presentation.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.blesample.presentation.MainViewModel

@Composable
fun BluetoothProfileView() {
    val viewmodel = hiltViewModel<MainViewModel>()
    val deviceName = viewmodel.bluetoothNameState.collectAsState().value
    var text by remember { mutableStateOf("") }

    if (deviceName.isNotEmpty()) {
        Text("Device Bluetooth: $deviceName")
        Spacer(modifier = Modifier.height(5.dp))
    }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") }
    )

    Spacer(modifier = Modifier.height(5.dp))

    Button(onClick = {
        viewmodel.setBluetoothAdapterName(text)
    }) {
        Text("Set Bluetooth Profile")
    }

}