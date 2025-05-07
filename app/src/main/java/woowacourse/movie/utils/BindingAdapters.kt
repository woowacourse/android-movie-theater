package woowacourse.movie.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("imageRes")
    fun loadImage(
        view: ImageView,
        @DrawableRes imageRes: Int,
    ) {
        view.setImageResource(imageRes)
    }
}
