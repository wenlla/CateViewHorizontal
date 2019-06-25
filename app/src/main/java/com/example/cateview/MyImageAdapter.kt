package com.example.cateview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.top10_item.view.*

class MyImageAdapter(private val mImageData: List<ImageModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.top10_item, parent, false))

    }

    //填充啥数据
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val entity = mImageData[position]

        holder.itemView.mainTopIV.setImageResource(imgs[position % 9])

        holder.itemView.mainTopTV.text = entity.continentImage.toString()

        holder.itemView.mainTopTV.background.alpha = 200

    }

    //有多少个 item
    override fun getItemCount(): Int {
        return mImageData.size
    }


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var itemText = itemView.mainTopTV
//        var itemImage = itemView.mainTopIV
    }


}