package woowacourse.movie.presentation

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("imageResourceId")
fun bindImage(
    imageView: ImageView,
    resId: Int,
) {
    imageView.setImageResource(resId)
}
