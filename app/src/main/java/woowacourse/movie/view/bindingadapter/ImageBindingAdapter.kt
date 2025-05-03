package woowacourse.movie.view.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import woowacourse.movie.view.ext.toDrawableResourceId

sealed interface ImageSource {
    data class Url(val url: String) : ImageSource

    data class Resource(val name: String) : ImageSource
}

@BindingAdapter("imageFromName")
fun ImageView.setImageFromName(source: ImageSource) {
    when (source) {
        is ImageSource.Url -> {}
        is ImageSource.Resource -> setImageResource(source.name.toDrawableResourceId(context))
    }
}
