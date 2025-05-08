package woowacourse.movie.helper

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("drawableResource")
fun setDrawableResource(
    view: ImageView,
    drawableRes: Int,
) {
    view.setImageResource(drawableRes)
}
