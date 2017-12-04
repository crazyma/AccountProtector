package com.beibeilab.itemdeco

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by david on 2017/12/1.
 */

abstract class BaseItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    protected var paddingLeft: Int
    protected var paddingTop: Int
    protected var paddingRight: Int
    protected var paddingBottom: Int
    protected var interval: Int
    protected var orientation: Int
    protected var paddingOnTop: Boolean
    protected var paddingOnBottom: Boolean

    private var divider: Drawable? = null
        set(value) {
            if (value != null) {
                interval = when (orientation) {
                    HORIZONTAL_LIST -> value.intrinsicWidth
                    else -> value.intrinsicHeight
                }
            }

        }

    init {
        paddingLeft = 0
        paddingTop = 0
        paddingRight = 0
        paddingBottom = 0
        interval = 0
        paddingOnTop = false
        paddingOnBottom = false
        orientation = VERTICAL_LIST
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (divider != null) {
            when (orientation) {
                HORIZONTAL_LIST -> {

                }
                else -> {
                    val left = parent.paddingLeft - paddingRight
                    val right = parent.width - parent.paddingRight - paddingRight

                    val childCount = parent.childCount
                    for (i in 0 until childCount) {
                        val child = parent.getChildAt(i)
                        val params = child.layoutParams as RecyclerView.LayoutParams

                        val top =
                                if (paddingOnTop) interval
                                else child.bottom + params.bottomMargin


                        val bottom =
                                if (paddingOnBottom) top + interval
                                else top

                        divider!!.setBounds(left, top, right, bottom)
                        divider!!.draw(c)
                    }
                }
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        when (orientation) {
            HORIZONTAL_LIST -> {
                outRect.set(paddingLeft, paddingTop, interval, paddingBottom)
            }
            else -> {
                outRect.set(paddingLeft, paddingTop, paddingRight, interval)
            }
        }

    }

    companion object {
        val VERTICAL_LIST = LinearLayoutManager.VERTICAL
        val HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL
    }
}