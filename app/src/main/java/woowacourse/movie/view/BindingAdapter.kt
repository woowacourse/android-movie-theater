package woowacourse.movie.view

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("srcDrawable")
fun setDrawableResource(
    view: ImageView,
    @DrawableRes drawableResource: Int,
) {
    view.setImageResource(drawableResource)
}
