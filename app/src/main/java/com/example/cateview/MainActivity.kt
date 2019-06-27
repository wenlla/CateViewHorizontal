package com.example.cateview

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.opengl.ETC1.getHeight
import android.os.Handler
import android.view.MotionEvent
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import kotlinx.android.synthetic.main.top10_item.view.*


class MainActivity : AppCompatActivity() {

    private lateinit var textRecyclerView: RecyclerView

    private lateinit var imageRecyclerView: RecyclerView


    private lateinit var mcAdapter: MyCateAdapter

    private lateinit var miAdapter: MyImageAdapter


    private lateinit var listText: MutableList<ContinentTextModel>

    private lateinit var listImage: MutableList<ImageModel>


    private lateinit var mcGallerySnapHelper: GallerySnapHelper

    private lateinit var miGallerySnapHelper: GallerySnapHelper


    private var currentSelectedView: View? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        iniTextData()

        initImageData()

        initView()

    }

    private fun initView() {

        textRecyclerView = cateButton

        mcAdapter = MyCateAdapter(this, listText)

        textRecyclerView.setHasFixedSize(true)

        textRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        textRecyclerView.adapter = mcAdapter

        mcGallerySnapHelper = GallerySnapHelper()

        mcGallerySnapHelper.attachToRecyclerView(textRecyclerView)



        imageRecyclerView = topImagen

        miAdapter = MyImageAdapter(listImage)

        imageRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        imageRecyclerView.adapter = miAdapter

        miGallerySnapHelper = GallerySnapHelper()

        miGallerySnapHelper.attachToRecyclerView(imageRecyclerView)



        mcGallerySnapHelper.setOnGallerySnapScrollListener(object : OnGallerySnapScrollListener {

            override fun onTargetView(view: View) {

                if (currentSelectedView != view) {

                    (currentSelectedView?.findViewById<TextView>(R.id.mainCateTv))?.let {

                        mcAdapter.textChangeBack(it)
                    }

                    mcAdapter.textChange(view.findViewById(R.id.mainCateTv))

                    currentSelectedView = view

                }

                Log.v("firstView", view.toString())
            }

        })

        textRecyclerView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {

            override fun onPreDraw(): Boolean {

                textRecyclerView.viewTreeObserver.removeOnPreDrawListener(this)

                mcAdapter.textChange(textRecyclerView.getChildAt(0).findViewById(R.id.mainCateTv))

                return true
            }
        })


        textRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(baseContext, textRecyclerView,
            object : RecyclerItemClickListener.OnItemClickListener {

                    override fun onItemClick(view: View, position: Int) {

                        Log.v("onItemClickText", view.toString() + position.toString())
                    }

                    override fun onItemLongClick(view: View, position: Int) {

                        Log.v("onItemLongClickText", view.toString() + position.toString())
                    }
                }))

        imageRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(baseContext, textRecyclerView,
            object : RecyclerItemClickListener.OnItemClickListener {

                override fun onItemClick(view: View, position: Int) {

                    Log.v("onItemClickImage", view.toString() + position.toString())
                }

                override fun onItemLongClick(view: View, position: Int) {

                    Log.v("onItemLongClickImage", view.toString() + position.toString())
                }
            }))

    }


    //text内容
    private fun iniTextData() {
        listText = ArrayList()
        listText.add(ContinentTextModel("Belleza"))
        listText.add(ContinentTextModel("Gastronomía"))
        listText.add(ContinentTextModel("Ocio"))
        listText.add(ContinentTextModel("Shoping"))
        listText.add(ContinentTextModel("Viajes"))
        listText.add(ContinentTextModel("Salud"))
        listText.add(ContinentTextModel("Belleza"))
        listText.add(ContinentTextModel("Gastronomía"))
        listText.add(ContinentTextModel("Ocio"))
        listText.add(ContinentTextModel("Shoping"))
        listText.add(ContinentTextModel("Viajes"))
        listText.add(ContinentTextModel("Salud"))
        listText.add(ContinentTextModel("Belleza"))
        listText.add(ContinentTextModel("Gastronomía"))
        listText.add(ContinentTextModel("qqqqqqqqqq"))
        listText.add(ContinentTextModel("wwwwwwwwwww"))
        listText.add(ContinentTextModel("eeeeeeeee"))
        listText.add(ContinentTextModel("cccccccccc"))

    }

    //image 浮现文字内容
    private fun initImageData() {
        listImage = ArrayList()
        for (i in 0..10) {
            listImage.add(ImageModel(i))
        }
    }


}
