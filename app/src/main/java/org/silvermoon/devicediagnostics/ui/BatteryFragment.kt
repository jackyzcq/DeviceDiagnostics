package org.silvermoon.devicediagnostics.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_battery.*
import org.silvermoon.devicediagnostics.R


class BatteryFragment : Fragment() {

    

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_battery, container, false)
    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(mBatInfoReceiver, IntentFilter(
                Intent.ACTION_BATTERY_CHANGED)
        );
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(mBatInfoReceiver)
    }

    //Create Broadcast Receiver Object along with class definition
    private val mBatInfoReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        //When Event is published, onReceive method is called
        override fun onReceive(c: Context?, i: Intent) {
            //Get Battery %
            val level = i.getIntExtra("level", 0)

            //Set progress level with battery % value
            progressbar.progress = level
            tvBatteryLevelIndicator.text = "Battery Level: " + Integer.toString(level) + "%"
        }
    }

}