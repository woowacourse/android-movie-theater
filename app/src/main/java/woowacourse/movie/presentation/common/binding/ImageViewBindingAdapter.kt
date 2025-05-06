package woowacourse.movie.presentation.common.binding

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("srcBitmap")
fun setImageViewResource(
    imageView: ImageView,
    bitmap: Bitmap,
) {
    imageView.setImageBitmap(bitmap)
}
