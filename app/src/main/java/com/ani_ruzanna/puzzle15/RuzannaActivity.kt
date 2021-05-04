package com.ani_ruzanna.puzzle15

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class RuzannaActivity : AppCompatActivity(), Game16Fragment.Game16FragmentListener, TimerFragment.TimerFragmentListener {
    private lateinit var mainLayout: ViewGroup
    private lateinit var l: List<Pair<ImageView, Int>>
    private val timerFragment = TimerFragment()
    private var imageWidth = 0
    private var xOpen = 0
    private var yOpen = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ruzanna)
        val widthScreen = this.windowManager.defaultDisplay.width - 16
        Log.i("widthScreen ac", widthScreen.toString())
        val game16Fragment = Game16Fragment()
        game16Fragment.listener = this
        game16Fragment.widthScreen = widthScreen
        supportFragmentManager.beginTransaction().add(R.id.game_container, game16Fragment).commit()


        timerFragment.listener = this
        supportFragmentManager.beginTransaction().add(R.id.timer_container, timerFragment).commit()

    }

    override fun endGameListener(sumOfMoves: Int) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Sum Of Moves: $sumOfMoves\nTime: ${timerFragment.getChronometerTime()}")
                .setCancelable(false)
                .setNeutralButton("OK", DialogInterface.OnClickListener { _, _ ->
                    finish()
                })
        val alert = dialogBuilder.create()
        alert.setTitle("Well done!")
        alert.show()
    }

}