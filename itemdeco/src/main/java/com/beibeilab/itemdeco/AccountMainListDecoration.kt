package com.beibeilab.itemdeco

import android.content.Context
import android.content.res.TypedArray
import com.beibeilab.itemdeco.TransferDecoration.Companion.ATRRS

/**
 * Created by david on 2017/12/4.
 */
class AccountMainListDecoration(context: Context) : BaseItemDecoration() {

    init {
        val ta: TypedArray = context.obtainStyledAttributes(ATRRS)
        divider = ta.getDrawable(0)
        ta.recycle()

        drawablePaddingLeft = context.resources.getDimensionPixelSize(R.dimen.main_list_text_padding_left)

        paddingOnBottom = true
    }
}