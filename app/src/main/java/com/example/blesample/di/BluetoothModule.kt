package com.example.blesample.di

import android.content.Context
import com.example.blesample.data.BluetoothHandlerUseCaseImpl
import com.example.blesample.data.BluetoothService
import com.example.blesample.domain.BluetoothHandlerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BluetoothModule {

    @Provides
    fun provideBluetoothService(@ApplicationContext context: Context): BluetoothService {
        return BluetoothService(context)
    }

    @Provides
    fun provideBluetoothHandlerUseCase(
        bluetoothService: BluetoothService,
        @ApplicationContext context: Context
    ): BluetoothHandlerUseCase {
        return BluetoothHandlerUseCaseImpl(bluetoothService, context)
    }

}