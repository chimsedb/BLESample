package com.example.blesample.presentation.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.blesample.presentation.MainViewModel

@Composable
fun PairedDevicesView() {
    val viewmodel = hiltViewModel<MainViewModel>()
    Button(onClick = {
        viewmodel.fetchPairedDevices()
    }) {
        Text("Get Paired Devices")
    }

    val listDevice = viewmodel.pairedDevicesState.collectAsState().value
    LazyColumn {
        items(listDevice) {
            Text(fontSize = 18.sp, text = "${it.name} - ${it.address}")
        }
    }
}