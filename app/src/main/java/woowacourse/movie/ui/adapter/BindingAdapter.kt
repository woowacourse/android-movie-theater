package woowacourse.movie.ui.adapter

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import woowacourse.movie.ui.util.PosterMapper

@BindingAdapter("posterImage")
fun ImageView.setPosterImage(title: String?) {
    if (title == null) return
    setImageResource(PosterMapper.convertTitleToResId(title))
}

@BindingAdapter("imgRes")
fun ImageView.setImageViewResource(resId: Bitmap) {
    setImageBitmap(resId)
}
