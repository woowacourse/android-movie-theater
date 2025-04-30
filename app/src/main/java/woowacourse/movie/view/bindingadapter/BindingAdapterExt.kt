package woowacourse.movie.view.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import woowacourse.movie.view.ext.toDrawableResourceId

@BindingAdapter("imageFromName")
fun ImageView.setImageFromName(name: String) {
    setImageResource(name.toDrawableResourceId(context))
}
