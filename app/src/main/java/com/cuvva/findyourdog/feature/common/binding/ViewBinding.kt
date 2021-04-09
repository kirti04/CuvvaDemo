package com.cuvva.findyourdog.feature.common.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object ViewBinding {

    @JvmStatic
    @BindingAdapter("visible")
    fun setIsVisible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter(value = ["image", "isRounded"], requireAll = false)
    fun loadImage(view: ImageView, image: String?, isRounded: Boolean = true) {
        if (image == null)
            return

        view.apply {
            if (isRounded) {
                Glide.with(context).load(image).apply(RequestOptions.circleCropTransform())
                    .into(this)
            } else {
                Glide.with(context).load(image).into(this)
            }
        }
    }
}