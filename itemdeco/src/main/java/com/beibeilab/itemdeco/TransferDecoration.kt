package com.beibeilab.itemdeco

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * This is a sample kt code converted by AS kotlin convert tool
 */
class TransferDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val mDivider: Drawable?
    private val textPaddingLeft: Int

    init {
        val ta = context.obtainStyledAttributes(ATRRS)
        this.mDivider = ta.getDrawable(0)
        ta.recycle()
        textPaddingLeft = context.resources.getDimensionPixelSize(R.dimen.main_list_text_padding_left)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        drawHorizontalLine(c, parent, state)
    }

    //画横线, 这里的parent其实是显示在屏幕显示的这部分
    fun drawHorizontalLine(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        val left = parent.paddingLeft + textPaddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            //获得child的布局信息
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider!!.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
            //Log.d("wnw", left + " " + top + " "+right+"   "+bottom+" "+i);
        }
    }

    //由于Divider也有长宽高，每一个Item需要向下或者向右偏移
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        outRect.set(0, 0, 0, mDivider!!.intrinsicHeight)
    }

    companion object {
        val VERTICAL_LIST = LinearLayoutManager.VERTICAL

        //我们通过获取系统属性中的listDivider来添加，在系统中的AppTheme中设置
        val ATRRS = intArrayOf(android.R.attr.listDivider)
    }

}
