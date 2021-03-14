package com.example.ardfitness.bleconnect

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.appsinthesky.bluetoothtutorial.ControlActivity
import com.example.ardfitness.BluetoothListAdapter
import com.example.ardfitness.ClickListener
import com.example.ardfitness.R
import com.example.ardfitness.databinding.DeviceListBinding

class ConnectBle : Fragment(){

    private var m_bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var m_pairedDevices: Set<BluetoothDevice>
    private val REQUEST_ENABLE_BLUETOOTH = 1
    private lateinit var selectDeviceRefresh : Button

    companion object {
        val EXTRA_ADDRESS: String = "Device_address"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding=DataBindingUtil.inflate<DeviceListBinding>(inflater,R.layout.device_list, container, false)
        selectDeviceRefresh=binding.pairedbutton
        enableBle()
        val listView=binding.listView
        selectDeviceRefresh.setOnClickListener{ pairedDeviceList(listView) }
        return binding.root
    }
    private fun enableBle():Boolean{

        m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if(m_bluetoothAdapter!!.isEnabled)
            return true
        if(m_bluetoothAdapter == null) {
            Toast.makeText(context,"Device dosen't support Bluetooth", Toast.LENGTH_SHORT).show()
            return false
        }
        if(!m_bluetoothAdapter!!.isEnabled) {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
            if(m_bluetoothAdapter!!.isEnabled)
                return true
        }
        return false
    }
    private fun pairedDeviceList(listView :RecyclerView) {
        m_pairedDevices = m_bluetoothAdapter!!.bondedDevices
        val list : ArrayList<BluetoothDevice> = ArrayList()

        if (m_pairedDevices.isNotEmpty()) {
            for (device: BluetoothDevice in m_pairedDevices) {
                list.add(device)
                Log.i("device", ""+device)
            }
        } else {
            Toast.makeText(context,"No paired bluetooth devices found", Toast.LENGTH_SHORT).show()
        }
        val adapter = BluetoothListAdapter(ClickListener { ble: BluetoothDevice ->

            val intent = Intent(context, ControlActivity::class.java)
            intent.putExtra(EXTRA_ADDRESS, ble.address)
            startActivity(intent)
        })
        listView.adapter=adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if (resultCode == Activity.RESULT_OK) {
                if (m_bluetoothAdapter!!.isEnabled) {
                    Toast.makeText(context,"Bluetooth has been enabled", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context,"Bluetooth has been disabled", Toast.LENGTH_SHORT).show()
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(context,"Bluetooth enabling has been canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }
}