package com.project.sweettrackersample.customview

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import com.project.sweettrackersample.R

class NoFontPaddingRegularTextView @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        includeFontPadding = false
        typeface = ResourcesCompat.getFont(context, R.font.notosanscjkkr_regular)
    }

}