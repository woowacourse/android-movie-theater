package woowacourse.movie.bindingadapter

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("imgRes")
fun setImageViewResource(imageView: ImageView, @DrawableRes resId: Int) {
    imageView.setImageResource(resId)
}
