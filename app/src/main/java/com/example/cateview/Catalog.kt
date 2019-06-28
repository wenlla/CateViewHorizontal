package com.example.cateview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class Catalog {

    private lateinit var textRecyclerView: RecyclerView

    private lateinit var imageRecyclerView: RecyclerView


    private lateinit var mcAdapter: MyCateAdapter

    private lateinit var miAdapter: MyImageAdapter


    private lateinit var listText: MutableList<ContinentTextModel>

    private lateinit var listImage: MutableList<ImageModel>


    private lateinit var mcGallerySnapHelper: GallerySnapHelper

    private lateinit var miGallerySnapHelper: GallerySnapHelper


    private var currentSelectedView: View? = null


}