package com.ani_ruzanna.puzzle15

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import androidx.fragment.app.Fragment

class TimerFragment : Fragment() {

    lateinit var listener: TimerFragmentListener
    private lateinit var chronometer: Chronometer

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chronometer = view.findViewById<Chronometer>(R.id.chronometer)
        chronometer.start()
    }

    fun getChronometerTime(): String{
        chronometer.stop()
        return chronometer.text.toString()
    }


    interface TimerFragmentListener{
    }
}