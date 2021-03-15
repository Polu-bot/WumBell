package com.example.wumbell.bleconnect

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wumbell.R
import com.example.wumbell.databinding.DeviceListBinding

class ConnectBle : Fragment(){
    private lateinit var selectDeviceRefresh : Button
    val REQUEST_ENABLE_BLUETOOTH = 1
    companion object {
        val EXTRA_ADDRESS: String = "Device_address"
    }
    private lateinit var viewModel: ConnectBleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding=DataBindingUtil.inflate<DeviceListBinding>(inflater,R.layout.device_list, container, false)
        selectDeviceRefresh=binding.pairedbutton
        val application = requireNotNull(this.activity).application
        val viewModelFactory = ConnectBleViewModelFactory(application)
        viewModel= ViewModelProvider(this, viewModelFactory).get(ConnectBleViewModel::class.java)
        enableBle()
        val listView=binding.listView
        viewModel.pairedDeviceList()
        selectDeviceRefresh.setOnClickListener{ viewModel.pairedDeviceList() }
        binding.lifecycleOwner = this
        val adapter= BluetoothListAdapter(ClickListener { ble: BluetoothDevice ->
            viewModel.onListClicked(ble)
            Toast.makeText(context,ble.name, Toast.LENGTH_SHORT).show()
        })
        listView.adapter=adapter
        viewModel.bleList.observe(viewLifecycleOwner,{
            it?.let {
                adapter.submitList(it)
            }
        })
//        viewModel.navigate.observe(viewLifecycleOwner, Observer {
//            if (it == true) {
//                val adapter = BluetoothListAdapter(ClickListener { ble: BluetoothDevice ->
//
//                    val intent = Intent(context, ControlActivity::class.java)
//                    intent.putExtra(EXTRA_ADDRESS, ble.address)
//                    startActivity(intent)
//                })
//            }
//        })
        return binding.root
    }
    private fun enableBle():Boolean{

        viewModel.m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if(viewModel.m_bluetoothAdapter!!.isEnabled)
            return true
        if(viewModel.m_bluetoothAdapter == null) {
            Toast.makeText(context,"Device dosen't support Bluetooth", Toast.LENGTH_SHORT).show()
            return false
        }
        if(!viewModel.m_bluetoothAdapter!!.isEnabled) {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
            if(viewModel.m_bluetoothAdapter!!.isEnabled)
                return true
        }
        return false
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if (resultCode == Activity.RESULT_OK) {
                if (viewModel.m_bluetoothAdapter!!.isEnabled) {
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