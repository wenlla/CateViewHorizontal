package com.example.cateview

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var textRecyclerView: RecyclerView

    private lateinit var imageRecyclerView: RecyclerView


    private lateinit var mcAdapter: MyCateAdapter

    private lateinit var miAdapter: MyImageAdapter


    private lateinit var listText: MutableList<ContinentTextModel>

    private lateinit var listImage: MutableList<ImageModel>


    private lateinit var mcGallerySnapHelper: GallerySnapHelper

    private lateinit var miGallerySnapHelper: GallerySnapHelper



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        iniTextData()

        initImageData()



        textRecyclerView = cateButton

        mcAdapter = MyCateAdapter(this, listText)

        textRecyclerView.setHasFixedSize(true)

        textRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        textRecyclerView.adapter = mcAdapter

        mcGallerySnapHelper = GallerySnapHelper()

        mcGallerySnapHelper.attachToRecyclerView(textRecyclerView)


        imageRecyclerView = topImagen

        miAdapter = MyImageAdapter(listImage)

        imageRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)

        imageRecyclerView.adapter = miAdapter

        miGallerySnapHelper = GallerySnapHelper()

        miGallerySnapHelper.attachToRecyclerView(imageRecyclerView)



        textRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                super.onScrollStateChanged(recyclerView, newState)

                val manager = recyclerView.layoutManager as LinearLayoutManager

                // 当不滚动时
                if (newState === RecyclerView.SCROLL_STATE_IDLE) {
                    //获取第一个完全显示的ItemPosition
                    val firstPosotion = manager.findFirstCompletelyVisibleItemPosition()
                    manager.findViewByPosition(firstPosotion)

                }
            }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

                    val manager = recyclerView.layoutManager as LinearLayoutManager

                    var firstPosotion = manager.findFirstVisibleItemPosition()

                    //设置左边的RecyclerView的被点击
//                    mcAdapter(firstPosotion)
                    //刷新左边的RecyclerView，否则选中无效(亲自踩坑)
                    mcAdapter.notifyDataSetChanged()
        }
    }


        )

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
