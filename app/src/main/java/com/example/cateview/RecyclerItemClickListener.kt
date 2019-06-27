package com.example.cateview

import android.content.Context
import android.view.GestureDetector
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.MotionEvent


class RecyclerItemClickListener : RecyclerView.OnItemTouchListener {


    private  var mListener: OnItemClickListener

    private  var gestureDetector: GestureDetector

    interface OnItemClickListener {

        fun onItemClick(view: View, position: Int)

        fun onItemLongClick(view: View, position: Int)
    }


    constructor (context: Context, recyclerView: RecyclerView, listener: OnItemClickListener) {

        mListener = listener

        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {

            override fun onSingleTapUp(e: MotionEvent): Boolean {

                val child = recyclerView.findChildViewUnder(e.x, e.y)

                if (child != null && mListener != null) {

                    mListener.onItemClick(child, recyclerView.getChildAdapterPosition(child))
                }
                return true
            }

            override fun onLongPress(e: MotionEvent) {

                val child = recyclerView.findChildViewUnder(e.x, e.y)

                if (child != null && mListener != null) {

                    mListener.onItemLongClick(child, recyclerView.getChildAdapterPosition(child))
                }
            }
        })
    }

    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {

        val childView = view.findChildViewUnder(e.x, e.y)

        if (childView != null && mListener != null && gestureDetector.onTouchEvent(e)) {

            mListener.onItemClick(childView, view.getChildAdapterPosition(childView))
            return true
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}

