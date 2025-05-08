package woowacourse.movie.ui.movielist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.utils.StringFormatter

class MovieViewHolder(
    private val binding: MovieItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Movie) {
        binding.stringFormatter = StringFormatter
        binding.movie = item
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onClickBooking: BookingButtonClickListener,
        ): MovieViewHolder {
            val binding =
                MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            binding.clickListener = onClickBooking
            return MovieViewHolder(binding)
        }
    }
}
