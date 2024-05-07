package woowacourse.movie.presentation

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("imgResource")
fun bindImage(
    imageView: ImageView,
    resourceId: Int,
) {
    imageView.setImageResource(resourceId)
}
