package woowacourse.movie.view.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("imageFromName")
fun ImageView.setImageFromName(name: String) {
    setImageResource(name.toDrawableResourceId(context))
}
