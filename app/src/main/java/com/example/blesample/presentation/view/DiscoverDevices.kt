package com.example.blesample.presentation.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.blesample.presentation.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun DiscoverDevices() {
    val viewmodel = hiltViewModel<MainViewModel>()
    val coroutineScope = rememberCoroutineScope()
    Button(onClick = {
        coroutineScope.launch {
            viewmodel.fetchDiscoverDevices()
        }
    }) {
        Text("Discover Devices")
    }
    val listDiscoverDevice =
        viewmodel.discoverDevicesState.collectAsState().value
    LazyColumn {
        items(listDiscoverDevice) {
            Text(fontSize = 18.sp, text = "${it.name} - ${it.address}")
        }
    }
}