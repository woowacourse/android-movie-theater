package woowacourse.movie.presentation.common.binding

import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("bitmapFromRes")
fun setImageViewResource(
    imageView: ImageView,
    resId: Int,
) {
    val bitmap = BitmapFactory.decodeResource(imageView.resources, resId)
    imageView.setImageBitmap(bitmap)
}
