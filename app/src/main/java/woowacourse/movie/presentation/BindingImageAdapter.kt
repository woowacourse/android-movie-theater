package woowacourse.movie.presentation

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("imgRes")
fun bindImage(
    imageView: ImageView,
    resId: Int,
) {
    imageView.setImageResource(resId)
}
