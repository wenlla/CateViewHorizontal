package com.example.cateview

import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.recyclerview.widget.SnapHelper


/**思路：
 * SnapHelper实现了OnFlingListener这个接口，该接口中的onFling()方法会在RecyclerView触发Fling操作时调用。
 * 在onFling()方法中判断当前方向上的速率是否足够做滚动操作，如果速率足够大就调用snapFromFling()方法实现滚动相关的逻辑。
 * 在snapFromFling()方法中会创建一个SmoothScroller，并且根据速率计算出滚动停止时的位置，将该位置设置给SmoothScroller并启动滚动。
 * 而滚动的操作都是由SmoothScroller全权负责，它可以控制Item的滚动速度(刚开始是匀速)，
 * 并且在滚动到targetSnapView被layout时变换滚动速度（转换成减速），以让滚动效果更加真实。

所以，SnapHelper辅助RecyclerView实现滚动对齐就是通过给RecyclerView设置OnScrollerListener和OnFlingListener这两个监听器实现的。*/


//SnapHelper是一个抽象类，官方提供了一个LinearSnapHelper的子类，可以让RecyclerView滚动停止时相应的Item停留中间位置。
interface OnGallerySnapScrollListener {
    fun onTargetView(view: View)
}

class GallerySnapHelper : SnapHelper() {
    private var mHorizontalHelper: OrientationHelper? = null
    private var mRecyclerView: RecyclerView? = null
    private var gallerySnapCallback: OnGallerySnapScrollListener? = null

    //SnapHelper通过attachToRecyclerView()方法附着到RecyclerView上，从而实现辅助RecyclerView滚动对齐操作。
    @Throws(IllegalStateException::class)
    override fun attachToRecyclerView(@Nullable recyclerView: RecyclerView?) {
        mRecyclerView = recyclerView
        super.attachToRecyclerView(recyclerView)
    }


    //这个方法会计算第二个参数对应的ItemView当前的坐标与需要对齐的坐标之间的距离。
    // 该方法返回一个大小为2的int数组，分别对应x轴和y轴方向上的距离。

    //calculateDistanceToFinalSnap（）：计算SnapView当前位置与目标位置的距离
    override fun calculateDistanceToFinalSnap(@NonNull layoutManager: RecyclerView.LayoutManager, targetView: View): IntArray {
        val out = IntArray(2)

        //水平方向滚动,则计算水平方向需要滚动的距离,否则水平方向的滚动距离为0
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager))
        } else {
            out[0] = 0
        }
        if (out[0] == 0) {
            gallerySnapCallback?.onTargetView(targetView)
        }

        return out


    }

    //targetView的start坐标与RecyclerView的paddingStart之间的差值
    //就是需要滚动调整的距离
    private fun distanceToStart(targetView: View, helper: OrientationHelper): Int {
        return helper.getDecoratedStart(targetView) - helper.startAfterPadding
    }

//    createSnapScroller()创建的是一个LinearSmoothScroller，并且在创建该LinearSmoothScroller的时候主要考虑两个方面：
//    第一个是滚动速率，由calculateSpeedPerPixel()方法决定；
//    第二个是在滚动过程中，targetView即将要进入到视野时，将匀速滚动变换为减速滚动，
//    然后一直滚动目的坐标位置，使滚动效果更真实，这是由onTargetFound()方法决定。

    @Nullable
    override fun createSnapScroller(layoutManager: RecyclerView.LayoutManager?): LinearSmoothScroller? {

        //同样，这里也是先判断layoutManager是否实现了ScrollVectorProvider这个接口，
        //没有实现该接口就不创建SmoothScroller
        return if (layoutManager !is RecyclerView.SmoothScroller.ScrollVectorProvider) {
            null
        }
        //这里创建一个LinearSmoothScroller对象，然后返回给调用函数，
        //也就是说，最终创建出来的平滑滚动器就是这个LinearSmoothScroller

        else object : LinearSmoothScroller(mRecyclerView!!.context) {

            //该方法会在targetSnapView被layout出来的时候调用。
            //这个方法有三个参数：
            //第一个参数targetView，就是本文所讲的targetSnapView
            //第二个参数RecyclerView.State这里没用到，先不管它
            //第三个参数Action，这个是什么东西呢？它是SmoothScroller的一个静态内部类,
            //保存着SmoothScroller在平滑滚动过程中一些信息，比如滚动时间，滚动距离，差值器等

            override fun onTargetFound(
                targetView: View,
                state: RecyclerView.State,
                action: Action
            ) {
                val snapDistances = calculateDistanceToFinalSnap(mRecyclerView!!.layoutManager!!, targetView)
                val dx = snapDistances[0]
                val dy = snapDistances[1]

                //通过calculateTimeForDeceleration（）方法得到做减速滚动所需的时间
                val time = calculateTimeForDeceleration(Math.max(Math.abs(dx), Math.abs(dy)))
                if (time > 0) {
                    //调用Action的update()方法，更新SmoothScroller的滚动速率，使其减速滚动到停止
                    //这里的这样做的效果是，此SmoothScroller用time这么长的时间以mDecelerateInterpolator这个差值器的滚动变化率滚动dx或者dy这么长的距离
                    action.update(dx, dy, time, mDecelerateInterpolator)
                }
            }

            //该方法是计算滚动速率的，返回值代表滚动速率，该值会影响刚刚上面提到的
            //calculateTimeForDeceleration（）的方法的返回返回值，
            //MILLISECONDS_PER_INCH的值是100，也就是说该方法的返回值代表着每dpi的距离要滚动100毫秒
            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi
            }
        }
    }

    // findTargetSnapPosition()： 在触发fling时找到targetSnapPosition。
    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager,
        velocityX: Int,
        velocityY: Int
    ): Int {
        //判断layoutManager是否实现了RecyclerView.SmoothScroller.ScrollVectorProvider这个接口
        if (layoutManager !is RecyclerView.SmoothScroller.ScrollVectorProvider) {
            return RecyclerView.NO_POSITION
        }

        val itemCount = layoutManager.itemCount
        if (itemCount == 0) {
            return RecyclerView.NO_POSITION
        }

        //找到snapView
        val currentView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION

        val currentPosition = layoutManager.getPosition(currentView)
        if (currentPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION
        }

        val vectorProvider = layoutManager as RecyclerView.SmoothScroller.ScrollVectorProvider
        // 通过ScrollVectorProvider接口中的computeScrollVectorForPosition（）方法
        // 来确定layoutManager的布局方向
        val vectorForEnd = vectorProvider.computeScrollVectorForPosition(itemCount - 1)
            ?: // cannot get a vector for the given position.
            return RecyclerView.NO_POSITION

        //在松手之后,列表最多只能滚多一屏的item数
        val deltaThreshold =
            layoutManager.width / getHorizontalHelper(layoutManager).getDecoratedMeasurement(currentView)

        var hDeltaJump: Int
        if (layoutManager.canScrollHorizontally()) {
            //layoutManager是横向布局，并且内容超出一屏，canScrollHorizontally()才返回true
            //估算fling结束时相对于当前snapView位置的横向位置偏移量
            hDeltaJump = estimateNextPositionDiffForFling(
                layoutManager,
                getHorizontalHelper(layoutManager), velocityX, 0
            )

            if (hDeltaJump > deltaThreshold) {
                hDeltaJump = deltaThreshold
            }
            if (hDeltaJump < -deltaThreshold) {
                hDeltaJump = -deltaThreshold
            }
            //vectorForEnd.x < 0代表layoutManager是反向布局的，就把偏移量取反
            if (vectorForEnd.x < 0) {
                hDeltaJump = -hDeltaJump
            }
        } else {
            //不能横向滚动，横向位置偏移量当然就为0
            hDeltaJump = 0
        }

        if (hDeltaJump == 0) {
            return RecyclerView.NO_POSITION
        }

        var targetPos = currentPosition + hDeltaJump
        if (targetPos < 0) {
            targetPos = 0
        }
        if (targetPos >= itemCount) {
            targetPos = itemCount - 1
        }

        Log.v("position", targetPos.toString())
        return targetPos
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        return findStartView(layoutManager, getHorizontalHelper(layoutManager))
    }

//    OrientationHelper这个工具类，它是LayoutManager用于测量child的一个辅助类，
//    可以根据LayoutManager的布局方式和布局方向来计算得到ItemView的大小位置等信息。

    private fun findStartView(layoutManager: RecyclerView.LayoutManager, helper: OrientationHelper): View? {
        if (layoutManager is LinearLayoutManager) {
            val firstChildPosition = layoutManager.findFirstVisibleItemPosition()
            if (firstChildPosition == RecyclerView.NO_POSITION) {
                return null
            }

            if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {

                gallerySnapCallback?.onTargetView(layoutManager.findViewByPosition(layoutManager.findFirstCompletelyVisibleItemPosition())!!)

                Log.v("findViewByPosition", gallerySnapCallback.toString())
                return null
            }

            val firstChildView = layoutManager.findViewByPosition(firstChildPosition)
            return if (helper.getDecoratedEnd(firstChildView) >= helper.getDecoratedMeasurement(firstChildView) / 2 && helper.getDecoratedEnd(
                    firstChildView
                ) > 0
            ) {
                firstChildView
            } else {
                layoutManager.findViewByPosition(firstChildPosition + 1)
            }
        } else {
            return null
        }
    }


    private fun estimateNextPositionDiffForFling(
        layoutManager: RecyclerView.LayoutManager,
        helper: OrientationHelper, velocityX: Int, velocityY: Int
    ): Int {
        //计算滚动的总距离，这个距离受到触发fling时的速度的影响
        val distances = calculateScrollDistance(velocityX, velocityY)
        //计算每个ItemView的长度
        val distancePerChild = computeDistancePerChild(layoutManager, helper)
        if (distancePerChild <= 0) {
            return 0
        }
        val distance = distances[0]
        return if (distance > 0) {
            Math.floor((distance / distancePerChild).toDouble()).toInt()
        } else {
            Math.ceil((distance / distancePerChild).toDouble()).toInt()
        }
    }

    private fun computeDistancePerChild(
        layoutManager: RecyclerView.LayoutManager,
        helper: OrientationHelper
    ): Float {
        var minPosView: View? = null
        var maxPosView: View? = null
        var minPos = Integer.MAX_VALUE
        var maxPos = Integer.MIN_VALUE
        val childCount = layoutManager.childCount
        if (childCount == 0) {
            return INVALID_DISTANCE
        }

        for (i in 0 until childCount) {
            val child = layoutManager.getChildAt(i)
            val pos = layoutManager.getPosition(child!!)
            if (pos == RecyclerView.NO_POSITION) {
                continue
            }
            if (pos < minPos) {
                minPos = pos
                minPosView = child
            }
            if (pos > maxPos) {
                maxPos = pos
                maxPosView = child
            }
        }
        if (minPosView == null || maxPosView == null) {
            return INVALID_DISTANCE
        }
        val start = Math.min(
            helper.getDecoratedStart(minPosView),
            helper.getDecoratedStart(maxPosView)
        )
        val end = Math.max(
            helper.getDecoratedEnd(minPosView),
            helper.getDecoratedEnd(maxPosView)
        )
        val distance = end - start
        return if (distance == 0) {
            INVALID_DISTANCE
        } else 1f * distance / (maxPos - minPos + 1)
    }


    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return mHorizontalHelper!!
    }

    fun setOnGallerySnapScrollListener(listener: OnGallerySnapScrollListener) {
        this.gallerySnapCallback = listener
    }

    companion object {
        const val INVALID_DISTANCE = 1f
        const val MILLISECONDS_PER_INCH = 40f
    }

}