package woowacourse.movie.presentation.bindingadapter

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("imgResPath")
fun setImageViewResourcePath(
    view: ImageView,
    @DrawableRes resId: Int,
) {
    view.setImageResource(resId)
}
