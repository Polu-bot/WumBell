package com.example.ardfitness

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ardfitness.databinding.BluetoothListBinding

private val ITEM_VIEW_TYPE_ITEM = 1
class BluetoothListAdapter(val clickListner: ClickListener): ListAdapter<BluetoothDevice, RecyclerView.ViewHolder>(BluetoothDeviceDiffCallBack()) {

    class BluetoothDeviceDiffCallBack : DiffUtil.ItemCallback<BluetoothDevice>()
    {
        override fun areItemsTheSame(oldItem: BluetoothDevice, newItem: BluetoothDevice): Boolean {
            return oldItem.name==newItem.name
        }
        override fun areContentsTheSame(oldItem: BluetoothDevice, newItem: BluetoothDevice): Boolean {
            return oldItem==newItem
        }
    }


    override fun getItemViewType(position: Int): Int {
        return ITEM_VIEW_TYPE_ITEM
    }
    class ViewHolder private constructor(val binding: BluetoothListBinding): RecyclerView.ViewHolder(binding.root){
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = BluetoothListBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(view)
            }
        }
        fun bind(item: BluetoothDevice, clickListnerimg: ClickListener) {
            binding.click=clickListnerimg
            binding.bluetoothDevice=item

            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder->{
                val item=getItem(position)
                holder.bind(item,clickListner)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent)
    }

}
class ClickListener(val clisklistner: (BluetoothDevice)-> Unit){
    fun onClickBle(ble :BluetoothDevice)= clisklistner(ble)
}

