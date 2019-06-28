package com.example.cateview

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.example.cateview.DensityUtil.dip2px
import kotlinx.android.synthetic.main.cate_button_item.view.*
import kotlinx.android.synthetic.main.footer_item.view.*

const val TYPE_FOOTER = 0
const val TYPE_ITEM = 1

class MainCateAdapter(private val context: Context) :

    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var cateModels: List<CateModel>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var lastItemWidth = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_ITEM) {
            val viewItem = LayoutInflater.from(context).inflate(R.layout.cate_button_item, parent, false)
            VHItem(viewItem)
        } else {
            val viewFooter = LayoutInflater.from(context).inflate(R.layout.footer_item, parent, false)
            VHFooter(viewFooter)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        cateModels?.let {
            if (position != it.size) {
                val entity = it[position]
                if (holder is VHItem) {
                    if (position == it.size - 1) {
                        val paint = holder.itemName.paint
                        val textWidth = Layout.getDesiredWidth(entity.cateName, 0, entity.cateName.length, paint)
                        lastItemWidth = (textWidth + dip2px(context, 26.0f)).toInt()
                    }
                    holder.itemName.text = entity.cateName
                }
            } else {
                if (holder is VHFooter) {
                    val width = Resources.getSystem().displayMetrics.widthPixels
                    holder.itemRl.layoutParams = ViewGroup.LayoutParams((width - lastItemWidth), 50)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == cateModels?.size) TYPE_FOOTER else TYPE_ITEM
    }

    override fun getItemCount(): Int {
        var count = 0
        cateModels?.let { count = it.size + 1 }
        return count
    }

    inner class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName = itemView.mainCateTv
    }

    inner class VHFooter(footerView: View) : RecyclerView.ViewHolder(footerView) {
        val itemRl = footerView.mainCateFooterRl
    }

    fun textChange(textView: TextView) {
        textView.setTextColor(getColor(context, R.color.green))
        textView.setTypeface(null, Typeface.BOLD)
        textView.background = null
    }

    fun textChangeBack(textView: TextView) {
        textView.setTextColor(Color.BLACK)
        textView.setTypeface(null, Typeface.NORMAL)
        textView.setBackgroundResource(R.drawable.shape_textcate)
    }
}