package woowacourse.movie.presentation.homefragments.movieList.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.presentation.homefragments.movieList.listener.TheaterClickListener
import woowacourse.movie.presentation.homefragments.movieList.uimodel.ScreeningTheater

class TheaterViewHolder(private val binding: ItemTheaterBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        screeningTheater: ScreeningTheater,
        listener: TheaterClickListener,
    ) {
        binding.screeningTheater = screeningTheater
        binding.listener = listener
    }
}
