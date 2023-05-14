package woowacourse.movie.feature.movieList.viewHolder

import androidx.databinding.ViewDataBinding
import woowacourse.movie.databinding.ItemMovieLayoutBinding
import woowacourse.movie.feature.movieList.itemModel.CommonItemModel
import woowacourse.movie.feature.movieList.itemModel.MovieItemModel
import woowacourse.movie.util.DateTimeFormatters

class MovieViewHolder(
    binding: ViewDataBinding
) : CommonItemViewHolder(binding) {
    override fun bind(itemModel: CommonItemModel) {
        itemModel as MovieItemModel
        binding as ItemMovieLayoutBinding
        binding.movie = itemModel
        binding.runningDate.text = DateTimeFormatters.convertToDateTildeDate(
            binding.root.context,
            itemModel.movieState.startDate,
            itemModel.movieState.endDate
        )
    }
}
