package com.example.cateview

import android.content.Context
import android.view.GestureDetector
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.MotionEvent


class RecyclerItemClickListener
    (context: Context, listener: OnItemClickListener) : RecyclerView.OnItemTouchListener {

    private  var mListener: OnItemClickListener = listener

    private  var gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return true
        }
    })

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
        val childView = view.findChildViewUnder(e.x, e.y)
        if (childView != null && gestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView))
            return true
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }
}

