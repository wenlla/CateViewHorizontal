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
import androidx.recyclerview.widget.RecyclerView
import com.example.cateview.DensityUtil.dip2px
import kotlinx.android.synthetic.main.cate_button_item.view.*
import kotlinx.android.synthetic.main.footer_item.view.*


const val TYPE_FOOTER = 0
const val TYPE_ITEM = 1

class MyCateAdapter(private val context: Context, private val mTextData: List<ContinentTextModel>) :

    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var lastItemWidth = 0

    //决定了 item 长啥样
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == TYPE_ITEM) {

            val viewItem = LayoutInflater.from(context).inflate(R.layout.cate_button_item, parent, false)

            VHItem(viewItem)
        } else {

            val viewFooter = LayoutInflater.from(context).inflate(R.layout.footer_item, parent, false)

            VHFooter(viewFooter)
        }

    }

    //填充啥数据
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (position != mTextData.size) {

            val entity = mTextData[position]

            if (holder is VHItem) {
                try {
                    if (position == mTextData.size - 1) {

                        val paint = holder.itemName.paint

                        val textWidth = Layout.getDesiredWidth(entity.continentName, 0, entity.continentName.length, paint)

                        lastItemWidth = (textWidth + dip2px(context, 26.0f)).toInt()

                    }

                    holder.itemName.text = entity.continentName

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        } else {
            if (holder is VHFooter) {

                val width = Resources.getSystem().displayMetrics.widthPixels

                holder.itemRl.layoutParams = ViewGroup.LayoutParams((width - lastItemWidth), 30)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == mTextData.size) TYPE_FOOTER else TYPE_ITEM


    }

    //有多少个 item
    override fun getItemCount(): Int {
        return mTextData.size + 1
    }

    inner class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemName = itemView.mainCateTv
    }

    inner class VHFooter(footerView: View) : RecyclerView.ViewHolder(footerView) {
        var itemRl = footerView.mainCateFooterRl
    }

    fun   getData():List<ContinentTextModel>{

        return mTextData
    }



    fun textChange(textView: TextView) {

        textView.setTextColor(Color.parseColor("#93D099"))
        textView.setTypeface(null, Typeface.BOLD)
        textView.background = null

    }

    fun textChangeBack(textView: TextView) {

        textView.setTextColor(Color.BLACK)

        textView.setTypeface(null, Typeface.NORMAL)

        textView.setBackgroundResource(R.drawable.shape_textcate)

    }


}