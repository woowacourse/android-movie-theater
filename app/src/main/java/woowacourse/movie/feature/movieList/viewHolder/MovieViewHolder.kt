package woowacourse.movie.feature.movieList.viewHolder

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.databinding.MovieItemLayoutBinding
import woowacourse.movie.feature.common.itemModel.CommonItemModel
import woowacourse.movie.feature.common.viewHolder.CommonItemViewHolder
import woowacourse.movie.feature.movieList.itemModel.MovieItemModel
import woowacourse.movie.model.MovieState
import woowacourse.movie.util.DateTimeFormatters

class MovieViewHolder(
    binding: MovieItemLayoutBinding
) : CommonItemViewHolder(binding) {
    override fun bind(itemModel: CommonItemModel) {
        val item = itemModel as MovieItemModel
        val binding = binding as MovieItemLayoutBinding
        binding.movie = item
    }
}

@BindingAdapter("movieDate")
fun setMovieDate(view: TextView, movie: MovieState) {
    view.text = DateTimeFormatters.convertToDateTildeDate(
        view.context,
        movie.startDate,
        movie.endDate
    )
}

@BindingAdapter("imgId")
fun setMovieImageId(view: ImageView, id: Int) {
    view.setImageResource(id)
}
