package woowacourse.movie.view.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:imageRes")
fun setImage(
    imageView: ImageView,
    resId: Int,
) {
    imageView.setImageResource(resId)
}
