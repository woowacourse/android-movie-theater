package woowacourse.movie.data.adapter

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

object MovieDataBindingAdapter {
    @BindingAdapter("imgRes")
    @JvmStatic
    fun setImageViewResource(
        imageView: ImageView,
        @DrawableRes
        resId: Int,
    ) {
        imageView.setImageResource(resId)
    }
}
