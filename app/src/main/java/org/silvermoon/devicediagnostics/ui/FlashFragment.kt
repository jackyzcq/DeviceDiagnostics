package org.silvermoon.devicediagnostics.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.hardware.Camera
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_flash.*
import kotlinx.android.synthetic.main.fragment_flash.view.*
import org.silvermoon.devicediagnostics.R


class FlashFragment : Fragment() {
    private var camera: Camera? = null
    private var isFlashOn = false
    private var hasFlash = false
    var params: android.hardware.Camera.Parameters? = null
    var mp: MediaPlayer? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        hasFlash = requireActivity().applicationContext.packageManager
            .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!hasFlash) {
            // device doesn't support flash
            // Show alert message and close the application
            val alert = AlertDialog.Builder(requireContext())
                .create()
            alert.setTitle("Error")
            alert.setMessage("Device doesn't support flashlight")
            alert.setButton(
                "OK",
                DialogInterface.OnClickListener { dialog, which -> // closing the application
                    requireActivity().finish()
                })
            alert.show()

        }

        // get the camera
        getCamera()

        // displaying button image
        //toggleButtonImage()


/*
				 * Switch button click event to toggle flash on/off
				 */


        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_flash, container, false)
        /*
               * Switch button click event to toggle flash on/off
               */
        view.btnSwitch.setOnClickListener(View.OnClickListener {
            if (isFlashOn) {
                // turn off flash
                turnOffFlash()
            } else {
                // turn on flash
                turnOnFlash()
            }
        })
        return view
    }



  override fun onPause() {
        super.onPause()

        turnOffFlash()
    }


    override fun onResume() {
        super.onResume()
        if (hasFlash) turnOnFlash()
    }

    override fun onStart() {
        super.onStart()
        getCamera()
    }

    override fun onStop() {
        super.onStop()
        if (camera != null) {
            camera!!.release()
            camera = null
        }
    }

    private fun getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open()
                params = camera!!.getParameters()
            } catch (e: RuntimeException) {
                Toast.makeText(requireContext(),"Camera error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return
            }
            params = camera!!.getParameters()
            params!!.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH)
            camera!!.setParameters(params)
            camera!!.startPreview()
            isFlashOn = true


       //     toggleButtonImage()
        }
    }

    private fun turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return
            }

            params = camera!!.getParameters()
            params!!.setFlashMode(Camera.Parameters.FLASH_MODE_OFF)
            camera!!.setParameters(params)
            camera!!.stopPreview()
            isFlashOn = false

          //  toggleButtonImage()
        }
    }



    private fun toggleButtonImage() {
        if (isFlashOn) {
            requireView().btnSwitch.setImageResource(R.drawable.switchon)
        } else {
            requireView().btnSwitch.setImageResource(R.drawable.switchoff)
        }
    }



}