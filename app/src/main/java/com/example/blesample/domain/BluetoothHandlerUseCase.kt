package com.example.blesample.domain

import android.content.Intent
import com.example.blesample.data.model.BluetoothDeviceInfo
import kotlinx.coroutines.flow.Flow

interface BluetoothHandlerUseCase {
    fun turnOnOffBluetooth(callback: (intent: Intent) -> Unit)

    fun setBluetoothAdapterName(customName: String)

    fun getPairedDevices(): List<BluetoothDeviceInfo>

    fun discoverBluetoothDevices(): Flow<BluetoothDeviceInfo>
}