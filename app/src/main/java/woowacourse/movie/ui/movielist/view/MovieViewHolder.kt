package woowacourse.movie.ui.movielist.view

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.utils.StringFormatter

class MovieViewHolder(
    private val itemBinding: MovieItemBinding,
    val onClickBooking: (Long) -> Unit,
) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(item: Movie) {
        itemBinding.btnBooking.setOnClickListener {
            onClickBooking(item.id)
        }
        itemBinding.stringFormatter = StringFormatter
        itemBinding.movie = item
    }
}
