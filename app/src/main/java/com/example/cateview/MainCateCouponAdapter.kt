package com.example.cateview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.top10_item.view.*

class MainCateCouponAdapter(private val context: Context) :

    RecyclerView.Adapter<MainCateCouponAdapter.ItemViewHolder>() {
    var couponModels: List<CouponModel>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.top10_item, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        couponModels?.let {
            val entity = it[position]
            holder.itemImage.setImageResource(imgs[position % 9])
            holder.itemText.text = entity.contentImage.toString()
            holder.itemText.background.alpha = 200
        }
    }

    override fun getItemCount(): Int {
        return couponModels?.size ?: 0
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemText = itemView.mainTopTV
        val itemImage = itemView.mainTopIV
    }


}