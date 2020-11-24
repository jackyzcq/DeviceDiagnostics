package org.silvermoon.devicediagnostics.ui

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_microphone.*
import kotlinx.android.synthetic.main.fragment_microphone.view.*
import org.silvermoon.devicediagnostics.R
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [MicrophoneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MicrophoneFragment : Fragment(), TextToSpeech.OnInitListener {

    private lateinit var textToSpeech: TextToSpeech
    private val TAG = "MicrophoneFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        textToSpeech = TextToSpeech(requireContext(), this)

        val baseView = inflater.inflate(R.layout.fragment_microphone, container, false)

        baseView.bMicrophone.setOnClickListener(View.OnClickListener { displayResponse() })

        // Inflate the layout for this fragment
        return baseView
    }

    override fun onDestroy() {

        if (textToSpeech != null) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }

    override fun onInit(p0: Int) {
       
        if (p0 === TextToSpeech.SUCCESS) {
            val result: Int = textToSpeech.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED
            ) {

                Log.e(TAG, "Language is not available.")
            } else {

                bMicrophone.isEnabled = true
                // Greet the user.
                displayResponse()
            }
        } else {
            // Initialization failed.
            Log.e(TAG, "Could not initialize TextToSpeech.")
        }
    }

    private fun displayResponse() {

        textToSpeech.speak(
            "Microphone is working",
            TextToSpeech.QUEUE_FLUSH,  // Drop all pending entries in the playback queue.
            null
        )
    }

}