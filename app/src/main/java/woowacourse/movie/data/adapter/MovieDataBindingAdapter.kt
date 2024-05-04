package woowacourse.movie.data.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter

object MovieDataBindingAdapter {
    @BindingAdapter("imgRes")
    @JvmStatic
    fun setImageViewResource(
        imageView: ImageView,
        resId: Int,
    ) {
        imageView.setImageResource(resId)
    }
}
