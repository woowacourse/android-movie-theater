package woowacourse.movie.presentation.view.home.movies.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import woowacourse.movie.presentation.model.PosterUiModel

@BindingAdapter("imgRes")
fun ImageView.setImageViewResource(poster: PosterUiModel) {
    if (poster is PosterUiModel.Resource) {
        this.setImageResource(poster.resId)
    }
}
