package com.ani_ruzanna.puzzle15

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_game16.*
import kotlin.properties.Delegates

class Game16Fragment : Fragment() {
    private lateinit var mainLayout: ViewGroup
    private lateinit var l: List<Pair<ImageView, Int>>
    private var imageWidth = 0
    private var xOpen = 0
    private var yOpen = 0
    private var sumOfMoves = 0
    lateinit var listener: Game16FragmentListener
    var widthScreen by Delegates.notNull<Int>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game16, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainLayout = view.findViewById<View>(R.id.main) as RelativeLayout
        l = listOf(Pair(image1,1), Pair(image2,2), Pair(image3,3), Pair(image4,4),
                Pair(image5,5), Pair(image6,6), Pair(image7,7), Pair(image8,8),
                Pair(image9,9), Pair(image10, 10), Pair(image11, 11), Pair(image12, 12),
                Pair(image13, 13), Pair(image14, 14), Pair(image15, 15))
        var lsh = l.shuffled()
        var odd = true
        while (odd) {
            var n = 0
            for (i in 0..14) {
                for (j in (i+1)..14) {
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

       // val widthScreen = resources.configuration.screenWidthDp - 16
        Log.i("widthScreen fr", widthScreen.toString())
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
    private fun onTouchListener(): View.OnTouchListener {
        return View.OnTouchListener { view, event ->
            val layoutParams = view.layoutParams as RelativeLayout.LayoutParams
            val x1 = layoutParams.leftMargin
            val y1 = layoutParams.topMargin
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    if ((x1 - xOpen) == imageWidth || (xOpen - x1) == imageWidth ||
                            (y1 - yOpen) == imageWidth || (yOpen - y1) == imageWidth) {
                        if ((x1 - xOpen) == 0 || (xOpen - x1) == 0 ||
                                (y1 - yOpen) == 0 || (yOpen - y1) == 0) {
                            val x = ObjectAnimator.ofFloat(view, "x", xOpen.toFloat())
                            val y = ObjectAnimator.ofFloat(view, "y", yOpen.toFloat())
                            AnimatorSet().apply {
                                playTogether(x, y)
                                start()
                            }
                            layoutParams.leftMargin = xOpen
                            layoutParams.topMargin = yOpen
                            xOpen = x1
                            yOpen = y1
                            mainLayout.invalidate()
                            checker()
                            ++sumOfMoves
                        }
                    }
                }
            }
            true
        }
    }

    private fun checker(){
        var leftStart = 8
        var topStart = 0
        //val widthScreen = resources.configuration.screenWidthDp - 16
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
            //chronometer.stop()
            listener.endGameListener(sumOfMoves)
        }
    }

    interface Game16FragmentListener{
        fun endGameListener(sumOfMoves: Int)
    }

}