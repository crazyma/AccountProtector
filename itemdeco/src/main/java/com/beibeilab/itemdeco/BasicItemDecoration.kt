package com.beibeilab.itemdeco

import android.R.attr.listDivider
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by david on 2017/11/29.
 */

class BasicItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private var mDivider: Drawable
    private var textPaddingLeft: Int = 0
    private val ATRRS: IntArray = intArrayOf(listDivider)

    init {
        val ta: TypedArray = context.obtainStyledAttributes(ATRRS)
        mDivider = ta.getDrawable(0)
        ta.recycle()
        textPaddingLeft = context.resources.getDimensionPixelSize(R.dimen.main_list_text_padding_left)
    }


    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        if (parent != null) {
            val left = parent.paddingLeft.plus(textPaddingLeft)
            val right = parent.width.minus(parent.paddingRight)

            val childCount = parent.childCount
            for (i in 0 until childCount) {

                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val top = child.bottom.plus(params.bottomMargin)
                val bottom = top.plus(mDivider.intrinsicHeight)
                mDivider.setBounds(left, top, right, bottom)
                mDivider.draw(c)
            }
        }
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect?.set(0, 0, 0, mDivider.intrinsicHeight)
    }
}