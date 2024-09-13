package com.example.blesample.data

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.example.blesample.data.model.BluetoothDeviceInfo
import com.example.blesample.domain.BluetoothHandlerUseCase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class BluetoothHandlerUseCaseImpl(
    private val bluetoothService: BluetoothService,
    private val context: Context
) :
    BluetoothHandlerUseCase {
    companion object {
        private const val ACTION_REQUEST_ENABLE = BluetoothAdapter.ACTION_REQUEST_ENABLE
        private const val ACTION_REQUEST_DISABLE =
            "android.bluetooth.adapter.action.REQUEST_DISABLE"
    }

    override fun turnOnOffBluetooth(callback: (intent: Intent) -> Unit) {
        val isBluetoothEnable = bluetoothService.isBluetoothEnable()
        val intent = Intent(
            if (isBluetoothEnable) ACTION_REQUEST_DISABLE
            else ACTION_REQUEST_ENABLE
        )
        callback.invoke(intent)
    }

    override fun setBluetoothAdapterName(customName: String) {
        if (!bluetoothService.checkBluetoothConnectPermission()) {
            return
        }
        val bluetoothAdapter = bluetoothService.getBluetoothAdapter()
        bluetoothAdapter?.setName(customName)
    }

    override fun getPairedDevices(): List<BluetoothDeviceInfo> {
        if (!bluetoothService.checkBluetoothConnectPermission()) {
            return emptyList()
        }
        val bluetoothAdapter = bluetoothService.getBluetoothAdapter()
        return bluetoothAdapter?.bondedDevices?.map { BluetoothDeviceInfo(it.name, it.address) }
            ?.toList() ?: emptyList()
    }

    override fun discoverBluetoothDevices() = callbackFlow {
        val receiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                val action: String? = intent.action
                when (action) {
                    BluetoothDevice.ACTION_FOUND -> {
                        if (!bluetoothService.checkBluetoothConnectPermission()) {
                            return
                        }
                        val device: BluetoothDevice =
                            intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE) ?: return
                        val deviceName = device.name ?: "<No name>"
                        val deviceHardwareAddress = device.address ?: "<No MACAddress>"
                        trySend(BluetoothDeviceInfo(deviceName, deviceHardwareAddress))
                    }
                }
            }
        }

        if (!bluetoothService.checkBluetoothConnectPermission()) {
            return@callbackFlow
        }
        val bluetoothAdapter = bluetoothService.getBluetoothAdapter() ?: return@callbackFlow
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery()
        } else {
            bluetoothAdapter.startDiscovery()
            val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
            context.registerReceiver(receiver, filter)
        }

        awaitClose {
            context.unregisterReceiver(receiver)
        }
    }
}