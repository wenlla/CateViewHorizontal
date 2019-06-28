package com.example.cateview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.catelog_layout.view.*

interface OnCatalogViewTouchListener {
    fun onCateItemSelected(cateModel: CateModel?)
    fun onCateItemClicked(cateModel: CateModel?)
    fun onCouponItemClicked(couponModel: CouponModel?)
}

class CatalogView : ConstraintLayout {

    private val cateAdapter = MainCateAdapter(context)

    private val couponAdapter: MainCateCouponAdapter = MainCateCouponAdapter(context)

    private val cateGallerySnapHelper = GallerySnapHelper()

    private val couponGallerySnapHelper = GallerySnapHelper()

    private var currentSelectedView: View? = null

    var listener: OnCatalogViewTouchListener? = null

    var cateModels: List<CateModel>? = null
        set(value) {
            field = value
            cateAdapter.cateModels = value
        }

    var couponModels: List<CouponModel>? = null
        set(value) {
            field = value
            couponAdapter.couponModels = value
        }

    constructor(context: Context) : super(context)

    constructor(context: Context, mAttributeSet: AttributeSet) : super(context, mAttributeSet) {
        LayoutInflater.from(context).inflate(R.layout.catelog_layout, this, true)
        cateRV.setHasFixedSize(true)
        cateRV.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        cateRV.adapter = cateAdapter
        cateGallerySnapHelper.attachToRecyclerView(cateRV)
        couponRV.setHasFixedSize(true)
        couponRV.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        couponRV.adapter = couponAdapter
        couponGallerySnapHelper.attachToRecyclerView(couponRV)

        cateRV.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                cateRV.viewTreeObserver.removeOnPreDrawListener(this)
                cateAdapter.textChange(cateRV.getChildAt(0).findViewById(R.id.mainCateTv))
                currentSelectedView = cateRV.getChildAt(0)
                return true
            }
        })

        cateGallerySnapHelper.setOnGallerySnapScrollListener(object : OnGallerySnapScrollListener {
            override fun onTargetView(view: View) {
                if (currentSelectedView != view) {
                    (currentSelectedView?.findViewById<TextView>(R.id.mainCateTv))?.let {
                        cateAdapter.textChangeBack(it)
                    }
                    cateAdapter.textChange(view.findViewById(R.id.mainCateTv))
                    currentSelectedView = view
                    listener?.onCateItemSelected(cateModels?.get(cateRV.getChildAdapterPosition(view)))
                }
            }

        })

        cateRV.addOnItemTouchListener(

            RecyclerItemClickListener(context,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        listener?.onCateItemClicked(cateModels?.get(position))
                    }
                })
        )

        couponRV.addOnItemTouchListener(

            RecyclerItemClickListener(context,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        listener?.onCouponItemClicked(couponModels?.get(position))
                    }
                })
        )
    }
}