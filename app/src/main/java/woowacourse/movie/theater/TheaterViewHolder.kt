package woowacourse.movie.theater

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItmeBinding
import woowacourse.movie.dto.theater.MovieTheaterDto

class TheaterViewHolder(private val binding: TheaterItmeBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: MovieTheaterDto) {
        binding.theaterTitle.text = item.place
        binding.theaterCount.text = item.time.size.toString()
    }
}
