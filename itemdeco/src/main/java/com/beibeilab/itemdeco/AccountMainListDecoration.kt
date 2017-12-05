package com.beibeilab.itemdeco

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Rect
import com.beibeilab.itemdeco.TransferDecoration.Companion.ATRRS

/**
 * Created by david on 2017/12/4.
 */
class AccountMainListDecoration(val context: Context) : BaseItemDecoration() {

    init {
        val ta: TypedArray = context.obtainStyledAttributes(ATRRS)
        divider = ta.getDrawable(0)
        ta.recycle()

        paddingOnBottom = true
    }

    override fun getItemOffsetsRect(): Rect {
        return Rect(0, 0, 0, divider!!.intrinsicHeight)
    }

    override fun getDrawableOffsetsRect(): Rect {
        val padding = context.resources.getDimensionPixelSize(R.dimen.main_list_text_padding_left)
        return Rect(padding, 0, 0, 0)
    }
}