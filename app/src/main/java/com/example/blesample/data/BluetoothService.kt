package com.example.blesample.data

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

class BluetoothService(private val context: Context) {
    private val bluetoothAdapter: BluetoothAdapter? =
        context.getSystemService(BluetoothManager::class.java).adapter

    fun getBluetoothAdapter(): BluetoothAdapter? = bluetoothAdapter

    fun isBluetoothEnable(): Boolean = bluetoothAdapter?.isEnabled == true

    fun getBluetoothAdapterName(): String? {
        if (!checkBluetoothConnectPermission()) {
            return null
        }
        return bluetoothAdapter?.name
    }

    fun checkBluetoothConnectPermission(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            context.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
}