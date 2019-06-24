package com.example.cateview

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var textRecyclerView: RecyclerView

    private lateinit var mAdapter: MyAdapter

    private lateinit var list: MutableList<TextContentModel>

    private lateinit var mGallerySnapHelper: GallerySnapHelper


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        initData()


        textRecyclerView = cateButton

        mAdapter = MyAdapter(this, list)

        textRecyclerView.setHasFixedSize(true)

        textRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        textRecyclerView.adapter = mAdapter

        mGallerySnapHelper = GallerySnapHelper()

        mGallerySnapHelper.attachToRecyclerView(textRecyclerView)


    }

    //text内容
    private fun initData() {
        list = ArrayList()
        list.add(TextContentModel("Belleza"))
        list.add(TextContentModel("Gastronomía"))
        list.add(TextContentModel("Ocio"))
        list.add(TextContentModel("Shoping"))
        list.add(TextContentModel("Viajes"))
        list.add(TextContentModel("Salud"))
        list.add(TextContentModel("Belleza"))
        list.add(TextContentModel("Gastronomía"))
        list.add(TextContentModel("Ocio"))
        list.add(TextContentModel("Shoping"))
        list.add(TextContentModel("Viajes"))
        list.add(TextContentModel("Salud"))
        list.add(TextContentModel("Belleza"))
        list.add(TextContentModel("Gastronomía"))
        list.add(TextContentModel("qqqqqqqqqq"))
        list.add(TextContentModel("wwwwwwwwwww"))
        list.add(TextContentModel("eeeeeeeee"))
        list.add(TextContentModel("cccccccccc"))
        list.add(TextContentModel("cccccccccccc"))
        list.add(TextContentModel("ggggggggggg"))
        list.add(TextContentModel("ewqaxdffxxx vvvvv"))
        list.add(TextContentModel("bbbbbbbbbbb"))
        list.add(TextContentModel("xxxxxxxxx"))
        list.add(TextContentModel("zzzzzzzzzzz"))
        list.add(TextContentModel("wwwwwwwwww"))
        list.add(TextContentModel("oooooooooooooooooo"))
        list.add(TextContentModel("nnnnnnnnnnnnnn"))
        list.add(TextContentModel("yyyyyyyyyyyyyyy"))
        list.add(TextContentModel("mmmmmmmmmmmmm"))
        list.add(TextContentModel("qqqqqqqqqqqqq"))
        list.add(TextContentModel("fghjkggfhfthdtrhd"))
        list.add(TextContentModel("sergrgfxvxv"))
        list.add(TextContentModel("dgsdsdzvcx x"))
        list.add(TextContentModel("svdsvvdv"))
        list.add(TextContentModel("fsdfdsvds"))
        list.add(TextContentModel("svsdvzdsvdsv"))
        list.add(TextContentModel("vdsvsvfv"))
        list.add(TextContentModel("Shopinccsffgfthfg"))

    }

}
