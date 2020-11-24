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
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_light_senor.*
import kotlinx.android.synthetic.main.fragment_proximity_sensor.*
import org.silvermoon.devicediagnostics.R


/**
 * A simple [Fragment] subclass.
 * Use the [ProximitySensorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProximitySensorFragment : Fragment(), SensorEventListener {

    lateinit var sensmgr: SensorManager
    var lightSenor: Sensor? = null
    lateinit var sensorvalues: FloatArray
    val TAG = "ProximitySensorFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sensmgr=requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager;
        lightSenor=sensmgr.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(lightSenor == null){
            Toast.makeText(requireContext(),"No proximity sensor found", Toast.LENGTH_SHORT).show()
        }



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_proximity_sensor, container, false)
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
        // The Proximity sensor returns a single value either 0 or 5(also 1 depends on Sensor manufacturer).
        // 0 for near and 5 for far
        if(event!!.sensor.type==Sensor.TYPE_PROXIMITY){
            sensorvalues = event!!.values

            if(sensorvalues[0]==0f){
                tVProximity.setText("You are Near: "+ event.values[0].toString());
            }
            else{
                tVProximity.setText("You are Far: "+event.values[0].toString());
            }

        }}


    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        Log.i(TAG, "onAccuracyChanged: ")
    }



}