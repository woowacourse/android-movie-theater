package woowacourse.movie.presentation.common.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import woowacourse.movie.presentation.common.model.PosterUiModel

@BindingAdapter("poster")
fun setImageViewResource(
    imageView: ImageView,
    poster: PosterUiModel,
) {
    if (poster is PosterUiModel.Resource) {
        imageView.setImageResource(poster.resId)
    }
}
