package com.example.blesample.presentation

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.BLUETOOTH_CONNECT
import android.Manifest.permission.BLUETOOTH_SCAN
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blesample.presentation.view.BluetoothProfileView
import com.example.blesample.presentation.view.DiscoverDevices
import com.example.blesample.presentation.view.OnOffBluetoothView
import com.example.blesample.presentation.view.PairedDevicesView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val launcherPermission =
                rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {

                }
            LaunchedEffect(key1 = true) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    launcherPermission.launch(
                        arrayOf(
                            BLUETOOTH_CONNECT,
                            BLUETOOTH_SCAN,
                            ACCESS_COARSE_LOCATION,
                            ACCESS_FINE_LOCATION
                        )
                    )
                } else {
                    launcherPermission.launch(arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION))
                }
            }
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Greeting(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }

}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(
            top = 50.dp,
            start = 24.dp,
            end = 24.dp,
            bottom = 50.dp
        )
    ) {
        OnOffBluetoothView()
        Spacer(modifier = Modifier.height(20.dp))
        BluetoothProfileView()
        Spacer(modifier = Modifier.height(20.dp))
        PairedDevicesView()
        Spacer(modifier = Modifier.height(20.dp))
        DiscoverDevices()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting()
}