package org.silvermoon.devicediagnostics.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_headphone.*
import org.silvermoon.devicediagnostics.R


class HeadphoneFragment : Fragment() {

    lateinit var musicIntentReceiver: MusicIntentReceiver
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        musicIntentReceiver = MusicIntentReceiver()
        return inflater.inflate(R.layout.fragment_headphone, container, false)
    }

    override fun onResume() {
        val filter = IntentFilter(Intent.ACTION_HEADSET_PLUG)
        requireActivity().registerReceiver(musicIntentReceiver, filter)
        super.onResume()
    }

    override fun onPause() {
        requireActivity().unregisterReceiver(musicIntentReceiver)
        super.onPause()
    }

    inner class MusicIntentReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.action == Intent.ACTION_HEADSET_PLUG) {
                val state = intent.getIntExtra("state", -1)
                when (state) {
                    0 -> tvHeadphone.text = "Headset is unplugged"
                    1 -> tvHeadphone.text = "Headset is plugged"
                    else -> tvHeadphone.text = "Plugin the headset"
                }
            }
        }
    }
}