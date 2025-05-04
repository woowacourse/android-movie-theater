package woowacourse.movie.presentation.common.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import woowacourse.movie.presentation.common.model.PosterUiModel

@BindingAdapter("poster")
fun ImageView.setImageViewResource(poster: PosterUiModel) {
    if (poster is PosterUiModel.Resource) {
        this.setImageResource(poster.resId)
    }
}
