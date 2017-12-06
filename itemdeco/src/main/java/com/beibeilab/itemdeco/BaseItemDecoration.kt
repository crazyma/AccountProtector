package com.beibeilab.itemdeco

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by david on 2017/12/1.
 */

abstract class BaseItemDecoration : RecyclerView.ItemDecoration() {
    protected var interval: Int
    protected var orientation: Int
    protected var paddingOnTop: Int = 0
        set(value) {
            field = if (value > 0) value else 0
        }

    protected var paddingOnBottom: Int = 0
        set(value) {
            field = if (value > 0) value else 0
        }


    protected var divider: Drawable? = null
        set(value) {
            if (value != null) {
                interval = getIntervalSize(value)
                field = value
            }
        }

    protected var header: Drawable? = null
        set(value) {
            if (value != null) {
                paddingOnTop = getIntervalSize(value)
                field = value
            }
        }

    protected var footer: Drawable? = null
        set(value) {
            if (value != null) {
                paddingOnBottom = getIntervalSize(value)
                field = value
            }
        }

    init {
        interval = 0
        paddingOnTop = 0
        paddingOnBottom = 0
        orientation = VERTICAL_LIST
    }

    private fun getIntervalSize(drawable: Drawable): Int {
        return when (orientation) {
            HORIZONTAL_LIST -> drawable.intrinsicWidth
            else -> drawable.intrinsicHeight
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (divider != null) {
            when (orientation) {
                HORIZONTAL_LIST -> {

                }
                else -> {
                    val drawableOffsetRect = getDrawableOffsetsRect()
                    val left = parent.paddingLeft + drawableOffsetRect.left
                    val right = parent.width - parent.paddingRight - drawableOffsetRect.right

                    val childCount = parent.childCount

                    for (i in 0 until childCount) {
                        val child = parent.getChildAt(i)
                        val params = child.layoutParams as RecyclerView.LayoutParams

                        var top: Int
                        var bottom: Int

                        when (i) {
                            0 -> {
                                top =
                                        if (paddingOnTop > 0)
                                            child.bottom + params.bottomMargin + paddingOnTop
                                        else
                                            child.bottom + params.bottomMargin
                                bottom = top + interval
                            }
                            childCount -1 -> {
                                top = child.bottom + params.bottomMargin
                                bottom =
                                        if (paddingOnBottom > 0) top + paddingOnBottom
                                        else top
                            }
                            else -> {
                                top = child.bottom + params.bottomMargin
                                bottom = top + paddingOnBottom
                            }
                        }

                        divider!!.setBounds(left, top, right, bottom)
                        divider!!.draw(c)
                    }
                }
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(getItemOffsetsRect())
    }

    abstract fun getItemOffsetsRect(): Rect
    abstract fun getDrawableOffsetsRect(): Rect

    companion object {
        val VERTICAL_LIST = LinearLayoutManager.VERTICAL
        val HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL
    }
}