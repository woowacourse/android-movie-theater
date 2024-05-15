package woowacourse.movie.presentation.main.home.theater.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.domain.model.home.Theater

class TheaterViewHolder(private val binding: TheaterItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        movieId: Long,
        theater: Theater,
        onTheaterItemClickListener: OnTheaterItemClickListener,
    ) {
        binding.movieId = movieId
        binding.theater = theater
        binding.onTheaterItemClickListener = onTheaterItemClickListener
    }
}
