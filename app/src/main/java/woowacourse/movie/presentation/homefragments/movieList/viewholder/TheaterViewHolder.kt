package woowacourse.movie.presentation.homefragments.movieList.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.Theater
import woowacourse.movie.presentation.homefragments.movieList.listener.TheaterClickListener

class TheaterViewHolder(private val binding: ItemTheaterBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        theater: Theater,
        movieId: Long,
        listener: TheaterClickListener,
    ) {
        binding.theater = theater
        binding.movieId = movieId
        binding.theaterItemClickListener = listener
    }
}
