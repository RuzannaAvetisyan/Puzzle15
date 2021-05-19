package com.ani_ruzanna.puzzle15

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.ExifInterface
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_ruzanna.*
import java.io.File


class RuzannaActivity : AppCompatActivity(), Game16Fragment.Game16FragmentListener, TimerFragment.TimerFragmentListener, BitmapFragment.BitmapFragmentListener {
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

        camera.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            intent.type = "image/*"
           // val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(intent, 1)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                        applicationContext, "There is no camera on the device.",
                        Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null) {
            val bitmap = MediaStore.Images.Media.getBitmap(applicationContext.contentResolver, data.data)
            val h = bitmap.height
            val w = bitmap.width
            var tw = 0
            var th = 0
            var t = h
            if (w > h){
                tw = (w - h) / 2
            }
            else if(h > w){
                th = (h - w) / 2
                t = w
            }
            Log.i(" tw, th, t, t", "$tw, $th, $t, $t, $w, $h)")
            val bm1 = Bitmap.createBitmap(bitmap, tw, th, t, t)
            val bitmapFragment = BitmapFragment()
            bitmapFragment.bitmap = bm1
            val widthScreen = this.windowManager.defaultDisplay.width - 16
            bitmapFragment.widthScreen = widthScreen
            bitmapFragment.widthBitmap = t/4
            bitmapFragment.listener = this
            supportFragmentManager.beginTransaction().replace(R.id.game_container, bitmapFragment).commit()
        }else{
            Toast.makeText(
                    applicationContext, "Bad request",
                    Toast.LENGTH_LONG
            ).show()
        }
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