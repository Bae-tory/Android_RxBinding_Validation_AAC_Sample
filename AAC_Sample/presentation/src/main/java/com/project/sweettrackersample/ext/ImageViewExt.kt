package com.project.sweettrackersample.ext

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.project.sweettrackersample.R

@BindingAdapter(
    "dimenResTopLeft",
    "dimenResTopRight",
    "dimenResBottomLeft",
    "dimenResBottomRight",
    "isShapeable",
    requireAll = false
)
fun ShapeableImageView.setRadius(
    @Dimension dimenResTopLeft: Float? = null,
    @Dimension dimenResTopRight: Float? = null,
    @Dimension dimenResBottomLeft: Float? = null,
    @Dimension dimenResBottomRight: Float? = null,
    isShapeable: Boolean = true
) {
    if (isShapeable) {
        shapeAppearanceModel =
            shapeAppearanceModel
                .toBuilder()
                .setTopLeftCorner(
                    CornerFamily.ROUNDED, dimenResTopLeft ?: 0f
                )
                .setTopRightCorner(
                    CornerFamily.ROUNDED,
                    dimenResTopRight ?: 0f
                )
                .setBottomLeftCorner(
                    CornerFamily.ROUNDED,
                    dimenResBottomLeft ?: 0f
                )
                .setBottomRightCorner(
                    CornerFamily.ROUNDED,
                    dimenResBottomRight ?: 0f
                )
                .build()
    }
}


@BindingAdapter("loadUrl", "placeHolder")
fun ImageView.loadUrl(url: String?, placeholder: Drawable) {
    loadUrl(url) {
        placeholder(placeholder)
    }
}

fun ImageView.loadUrl(url: String?, block: RequestOptions.() -> RequestOptions) {
    val option = RequestOptions()
    option.block()

    Glide.with(context)
        .load(url)
        .apply(option)
        .into(this)
}

@BindingAdapter("setColorByDeliveryStatus", "idx")
fun ImageView.setColorbyDeliveryStatus(parcelLevel: Int, idx: Int) {
    background = ContextCompat.getDrawable(context, R.drawable.parcel_location_circle)
    val colorRes = when {
        idx == parcelLevel -> R.color.Blue
        idx < parcelLevel -> R.color.LightBlue
        idx > parcelLevel -> R.color.LightLightGray
        else -> throw IllegalArgumentException(context.getString(R.string.error_res))
    }
    val bgDrawable = background as GradientDrawable
    bgDrawable.setStroke(
        (context.density() * 4 + 0.5f).toInt(),
        ContextCompat.getColor(context, colorRes)
    )
}
