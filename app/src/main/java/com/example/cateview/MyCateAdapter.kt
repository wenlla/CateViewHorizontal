package com.example.cateview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cate_button_item.view.*
import android.content.Context
import android.util.Log
import android.view.WindowManager
import com.example.cateview.DensityUtil.dip2px
import kotlinx.android.synthetic.main.footer_item.view.*


const val TYPE_FOOTER = 0
const val TYPE_ITEM = 1

class MyCateAdapter(private val context: Context, private val mTextData: List<ContinentTextModel>) :

    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var lastItemWidth = 0

    //决定了 item 长啥样
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == TYPE_ITEM) {

            val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.cate_button_item, parent, false)

            VHItem(viewItem)
        } else {

            val viewFooter = LayoutInflater.from(parent.context).inflate(R.layout.footer_item, parent, false)

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

                        lastItemWidth = holder.itemRl.width + dip2px(context, 10.0f).toInt()

                        Log.v("itemRl", lastItemWidth.toString())
                        Log.v("itemText", holder.itemName.width.toString())
                    }

                    holder.itemName.text = entity.continentName

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        } else {
            if (holder is VHFooter) {

                var wm: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

                var width = wm.defaultDisplay.width

                Log.v("itemText", width.toString())

                holder.itemRl.layoutParams = ViewGroup.LayoutParams((width - lastItemWidth), 30)

                holder.itemRl.invalidate()
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
        var itemRl = itemView.mainCateRl
    }

    inner class VHFooter(footerView: View) : RecyclerView.ViewHolder(footerView) {
        var itemRl = footerView.mainCateFooterRl

    }


}