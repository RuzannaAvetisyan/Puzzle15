package com.ani_ruzanna.puzzle15

import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import kotlin.properties.Delegates

class RotateActivity : Fragment(){
    lateinit var bitmap: Bitmap
    var bitmapW by Delegates.notNull<Int>()
    lateinit var listener: RotateActivityListener

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragmrnt_rotate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val photo = view.findViewById<ImageView>(R.id.imageView)
        photo.setImageBitmap(bitmap)
        view.findViewById<ImageButton>(R.id.imageButton).setOnClickListener{
            val matrix = Matrix()
            matrix.postRotate(90F)
            val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            bitmap = rotatedBitmap
            photo.setImageBitmap(bitmap)
        }
        view.findViewById<ImageButton>(R.id.imageButtonSave).setOnClickListener{
            listener.rotatedBitmap(bitmap, bitmapW)
        }
    }

    interface RotateActivityListener{
        fun rotatedBitmap(bitmap: Bitmap, t : Int)
    }

}