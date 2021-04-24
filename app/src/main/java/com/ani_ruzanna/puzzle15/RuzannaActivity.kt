package com.ani_ruzanna.puzzle15

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_ruzanna.*

class RuzannaActivity : AppCompatActivity() {
    private lateinit var mainLayout: ViewGroup
    private lateinit var l: List<Pair<ImageView, Int>>
    private var imageWidth = 0
    private var xOpen = 0
    private var yOpen = 0
    private var xDawn = 0
    private var yDawn = 0
    private var viewId = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ruzanna)
        mainLayout = findViewById<View>(R.id.main) as RelativeLayout
        l = listOf(Pair(image1,1), Pair(image2,2), Pair(image3,3), Pair(image4,4),
                Pair(image5,5), Pair(image6,6), Pair(image7,7), Pair(image8,8),
                Pair(image9,9), Pair(image10, 10), Pair(image11, 11), Pair(image12, 12),
                Pair(image13, 13), Pair(image14, 14), Pair(image15, 15))
        var lsh = l.shuffled()
        var odd = true
        while (odd) {
            var n = 0
            for (i in 0..14) {
                for (j in i..14) {
                    if (lsh[i].second > lsh[j].second) {
                        ++n
                    }
                }
            }
            if (n % 2 == 0) {
                odd = false
            } else {
                lsh = l.shuffled()
            }
        }
        val widthScreen = this.windowManager.defaultDisplay.width - 16
        imageWidth = widthScreen / 4
        var leftStart = 8
        var topStart = 0
        for (i in lsh.indices){
            val lp = lsh[i].first.layoutParams as RelativeLayout.LayoutParams
            lp.width = imageWidth
            lp.height = imageWidth
            lp.leftMargin = leftStart
            lp.topMargin = topStart
            leftStart += imageWidth
            if(leftStart > widthScreen){
                topStart += imageWidth
                leftStart = 8
            }
            lsh[i].first.setOnTouchListener(onTouchListener())
        }
        xOpen = 8 + imageWidth*3
        yOpen = imageWidth*3
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onTouchListener(): OnTouchListener {
        return OnTouchListener { view, event ->
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP ->{
                    val layoutParams = view
                            .layoutParams as RelativeLayout.LayoutParams
                    if (viewId == view.id && viewId != 0){
                        layoutParams.leftMargin = xOpen
                        layoutParams.topMargin = yOpen
                        xOpen = xDawn
                        yOpen = yDawn
                        viewId = 0
                        checker()
                    }
                    view.layoutParams = layoutParams
                }
                MotionEvent.ACTION_DOWN ->{
                    val layoutParams = view
                            .layoutParams as RelativeLayout.LayoutParams
                    val x1 = layoutParams.leftMargin
                    val y1 = layoutParams.topMargin
                    if ((x1 - xOpen) == imageWidth || (xOpen - x1) == imageWidth ||
                            (y1 - yOpen)== imageWidth || (yOpen - y1) == imageWidth){
                        if ((x1 - xOpen) == 0 || (xOpen - x1) == 0 ||
                                (y1 - yOpen)== 0 || (yOpen - y1) == 0){
                            xDawn = x1
                            yDawn = y1
                            viewId = view.id
                        }
                    }
                    view.layoutParams = layoutParams
                }
                MotionEvent.ACTION_MOVE ->{
                    val layoutParams = view
                            .layoutParams as RelativeLayout.LayoutParams
                    val x1 = layoutParams.leftMargin
                    val y1 = layoutParams.topMargin
                    if (viewId == view.id && viewId != 0){
                        if (y1 == yOpen){
                            layoutParams.leftMargin = (xOpen + x1)/2
                        }
                        if(x1 == xOpen) {
                            layoutParams.topMargin = (yOpen + y1)/2
                        }
                    }
                    view.layoutParams = layoutParams
                }
            }
            mainLayout.invalidate()
            true
        }
    }

    fun checker(){
        var leftStart = 8
        var topStart = 0
        val widthScreen = this.windowManager.defaultDisplay.width - 16
        var t = true
        for (i in l.indices){
            val lp = l[i].first.layoutParams as RelativeLayout.LayoutParams
            if (lp.leftMargin != leftStart || lp.topMargin != topStart){
                t = false
                break
            }
            leftStart += imageWidth
            if(leftStart > widthScreen){
                topStart += imageWidth
                leftStart = 8
            }
        }
        if(t){
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Well done!")
                    .setCancelable(false)
                    .setNeutralButton("OK", DialogInterface.OnClickListener {
                        _, _ -> finish()
                    })
            val alert = dialogBuilder.create()
            alert.setTitle("Puzzle 15")
            alert.show()
        }
    }
}