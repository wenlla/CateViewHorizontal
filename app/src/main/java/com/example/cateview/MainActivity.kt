package com.example.cateview

import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var listText: MutableList<CateModel>

    private lateinit var listImage: MutableList<CouponModel>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        iniTextData()
        initImageData()

        mainCatalogView.cateModels = listText
        mainCatalogView.couponModels = listImage
        mainCatalogView.listener = object : OnCatalogViewTouchListener{
            override fun onCateItemSelected(cateModel: CateModel?) {
                Log.v("MainActivity","catalog selected: ${cateModel?.cateName}")
            }

            override fun onCateItemClicked(cateModel: CateModel?) {
                Log.v("MainActivity", "catalog clicked: ${cateModel?.cateName}")
            }

            override fun onCouponItemClicked(couponModel: CouponModel?) {
                Log.v("MainActivity", "coupon clicked: ${couponModel?.contentImage}")
            }

        }
    }

    private fun iniTextData() {
        listText = ArrayList()
        listText.add(CateModel("Belleza"))
        listText.add(CateModel("Gastronomía"))
        listText.add(CateModel("Ocio"))
        listText.add(CateModel("Shoping"))
        listText.add(CateModel("Viajes"))
        listText.add(CateModel("Salud"))
        listText.add(CateModel("Belleza"))
        listText.add(CateModel("Gastronomía"))
        listText.add(CateModel("Ocio"))
        listText.add(CateModel("Shoping"))
        listText.add(CateModel("Viajes"))
        listText.add(CateModel("Salud"))
        listText.add(CateModel("Belleza"))
        listText.add(CateModel("Gastronomía"))
        listText.add(CateModel("qqqqqqqqqq"))
        listText.add(CateModel("wwwwwwwwwww"))
        listText.add(CateModel("eeeeeeeee"))
        listText.add(CateModel("cccccccccc"))
    }
    //image 浮现文字内容
    private fun initImageData() {
        listImage = ArrayList()
        for (i in 0..10) {
            listImage.add(CouponModel(i))
        }
    }
}
