package org.silvermoon.devicediagnostics.ui

import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_bluetooth.*
import org.silvermoon.devicediagnostics.R


class BluetoothFragment : Fragment() {

    lateinit var bluetoothAdapter: BluetoothAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        return inflater.inflate(R.layout.fragment_bluetooth, container, false)
        tvName.text = getLocalBluetoothName()
        tvAddress.text = getLocalBluetoothAddress()
    }

    fun getLocalBluetoothName(): String? {
        return bluetoothAdapter.name

    }

    fun getLocalBluetoothAddress(): String? {
        return bluetoothAdapter.address

    }


}