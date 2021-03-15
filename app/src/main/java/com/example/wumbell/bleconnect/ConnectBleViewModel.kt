package com.example.wumbell.bleconnect

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

class ConnectBleViewModel(application: Application): AndroidViewModel(application) {
    var m_bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var m_pairedDevices: Set<BluetoothDevice>


    private val _bleList = MutableLiveData<MutableList<BluetoothDevice>?>()
    val bleList : LiveData<MutableList<BluetoothDevice>?>
    get() = _bleList
    private val _navigate = MutableLiveData<BluetoothDevice?>()
    val navigate: LiveData<BluetoothDevice?>
        get() = _navigate

     fun pairedDeviceList() {
        m_pairedDevices = m_bluetoothAdapter!!.bondedDevices
        val list : ArrayList<BluetoothDevice> = ArrayList()
        if (m_pairedDevices.isNotEmpty()) {
            for (device: BluetoothDevice in m_pairedDevices) {
                list.add(device)
                Log.i("device", ""+device)
            }
            _bleList.value=list

        } else {
            Toast.makeText(getApplication(),"No paired bluetooth devices found", Toast.LENGTH_SHORT).show()
        }
    }

    fun onListClicked(it: BluetoothDevice) {
        _navigate.value=it
    }


}