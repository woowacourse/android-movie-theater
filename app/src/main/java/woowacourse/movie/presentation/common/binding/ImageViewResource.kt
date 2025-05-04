package woowacourse.movie.presentation.common.binding

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("srcBitmap")
fun ImageView.setImageViewResource(bitmap: Bitmap) {
    this.setImageBitmap(bitmap)
}
