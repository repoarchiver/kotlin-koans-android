package me.vickychijwani.kotlinkoans.features.common

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.DimenRes
import android.support.annotation.Dimension
import android.support.annotation.StyleRes
import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.AppCompatTextView
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.vickychijwani.kotlinkoans.R

fun makeTextView(ctx: Context, text: String, isHtml: Boolean = false, @StyleRes textAppearance: Int? = null,
                 paddingStart: Int = 0, paddingEnd: Int = 0, paddingTop: Int = 0, paddingBottom: Int = 0,
                 fontFamily: String? = null, drawableLeft: Drawable? = null, wrap: Boolean = true,
                 textAllCaps: Boolean = false)
        : TextView {
    val tv = AppCompatTextView(ctx)
    val finalText = if (textAllCaps) text.toUpperCase() else text
    tv.text = if (!isHtml) finalText else textToHtml(finalText)
    tv.setPadding(paddingStart, paddingTop, paddingEnd, paddingBottom)
    textAppearance?.let { TextViewCompat.setTextAppearance(tv, textAppearance) }
    fontFamily?.let { tv.typeface = Typeface.create(fontFamily, Typeface.NORMAL) }
    drawableLeft?.let {
        tv.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)
        tv.compoundDrawablePadding = getOffsetDimen(ctx, R.dimen.padding_inline)
    }
    tv.setHorizontallyScrolling(!wrap)
    return tv
}

fun makeVerticalSpacer(ctx: Context, @Dimension height: Int): View {
    val v = View(ctx)
    v.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
    return v
}

fun getOffsetDimen(ctx: Context, @DimenRes dimen: Int): Int {
    return ctx.resources?.getDimensionPixelOffset(dimen) ?: 0
}

fun getSizeDimen(ctx: Context, @DimenRes dimen: Int): Int {
    return ctx.resources?.getDimensionPixelSize(dimen) ?: 0
}

fun colorToHex(c: Int) = String.format("#%06X", 0xFFFFFF and c)

fun textToHtml(text: String): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(text)
    }
}
