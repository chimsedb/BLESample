package com.example.blesample.presentation

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blesample.data.BluetoothService
import com.example.blesample.data.model.BluetoothDeviceInfo
import com.example.blesample.domain.BluetoothHandlerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val bluetoothService: BluetoothService,
    private val bluetoothHandlerUseCase: BluetoothHandlerUseCase
) : ViewModel() {

    private val _bluetoothStatusState = MutableStateFlow(bluetoothService.isBluetoothEnable())
    val bluetoothStatusState = _bluetoothStatusState

    private val _bluetoothNameState = MutableStateFlow("")
    val bluetoothNameState = _bluetoothNameState

    private val _pairedDevicesState = MutableStateFlow<List<BluetoothDeviceInfo>>(emptyList())
    val pairedDevicesState = _pairedDevicesState

    private val _discoverDevicesState = MutableStateFlow<MutableList<BluetoothDeviceInfo>>(
        mutableListOf()
    )
    val discoverDevicesState = _discoverDevicesState

    fun fetchBluetoothNameState() {
        _bluetoothStatusState.value = bluetoothService.isBluetoothEnable()
    }

    fun fetchBluetoothAdapterName() {
        if (bluetoothService.isBluetoothEnable()) {
            _bluetoothNameState.value = bluetoothService.getBluetoothAdapterName() ?: "<Empty>"
        } else {
            _bluetoothNameState.value = ""
        }

    }

    fun setBluetoothAdapterName(customName: String) {
        bluetoothHandlerUseCase.setBluetoothAdapterName(customName)
        viewModelScope.launch {
            delay(1000L)
            fetchBluetoothAdapterName()
        }
    }

    fun turnOnOffBluetooth(callback: (intent: Intent) -> Unit) {
        bluetoothHandlerUseCase.turnOnOffBluetooth(callback)
    }

    fun fetchPairedDevices() {
        pairedDevicesState.update { bluetoothHandlerUseCase.getPairedDevices() }
    }

    suspend fun fetchDiscoverDevices() {
        _discoverDevicesState.value = mutableListOf()
        bluetoothHandlerUseCase.discoverBluetoothDevices().collect { device ->
            _discoverDevicesState.update {
                val list = _discoverDevicesState.value.toMutableList()
                list.add(device)
                list.distinct().toMutableList()
            }
        }
    }

}