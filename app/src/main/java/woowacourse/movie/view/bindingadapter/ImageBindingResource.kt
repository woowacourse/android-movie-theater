package woowacourse.movie.view.bindingadapter

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("imageRes")
fun setImageResource(
    view: ImageView,
    @DrawableRes resId: Int,
) {
    view.setImageResource(resId)
}
