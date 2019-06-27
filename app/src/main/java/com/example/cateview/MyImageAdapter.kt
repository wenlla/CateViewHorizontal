package com.example.cateview

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.top10_item.view.*

class MyImageAdapter(private val mImageData: List<ImageModel>) :
    RecyclerView.Adapter<MyImageAdapter.ItemViewHolder>() {

    var imgs = intArrayOf(
        R.drawable.aa,
        R.drawable.bb,
        R.drawable.cc,
        R.drawable.dd,
        R.drawable.ee,
        R.drawable.ff,
        R.drawable.gg,
        R.drawable.hh,
        R.drawable.jj
    )

    //决定了 item 长啥样
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.top10_item, parent, false))

    }

    //填充啥数据
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val entity = mImageData[position]

        holder.itemImage.setImageResource(imgs[position % 9])

        holder.itemText.text = entity.continentImage.toString()

        holder.itemText.background.alpha = 200

    }

    //有多少个 item
    override fun getItemCount(): Int {
        return mImageData.size
    }


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemText = itemView.mainTopTV

        var itemImage = itemView.mainTopIV
    }


}