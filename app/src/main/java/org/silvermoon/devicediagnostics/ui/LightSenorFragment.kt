package org.silvermoon.devicediagnostics.ui

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_light_senor.*
import org.silvermoon.devicediagnostics.R


/**
 * A simple [Fragment] subclass.
 * Use the [LightSenorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LightSenorFragment : Fragment(), SensorEventListener {

    lateinit var sensmgr: SensorManager
    var lightSenor: Sensor? = null
    lateinit var sensorvalues: FloatArray
    val TAG = "LightSensorFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sensmgr=requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager;
        lightSenor=sensmgr.getDefaultSensor(Sensor.TYPE_LIGHT);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_light_senor, container, false)
    }

    override fun onResume() {
        sensmgr.registerListener(this, lightSenor, SensorManager.SENSOR_DELAY_NORMAL)
        super.onResume()
    }


    override fun onPause() {
        sensmgr.unregisterListener(this)
        super.onPause()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        sensorvalues = event!!.values
        val x = sensorvalues[0]
        val y = x + 5;
        Log.i(TAG, "onSensorChanged: The value of the sensor after adding an offset.")
        tvLightSenor.text = "DAC " + x + " luxes"
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        Log.i(TAG, "onAccuracyChanged: ")
    }



}