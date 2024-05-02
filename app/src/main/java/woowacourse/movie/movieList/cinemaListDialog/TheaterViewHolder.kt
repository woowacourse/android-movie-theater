package woowacourse.movie.movieList.cinemaListDialog

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemBottomSheetTheatersBinding
import woowacourse.movie.model.Cinema

class TheaterViewHolder(
    private val binding: ItemBottomSheetTheatersBinding,
    private val onCinemaClicked: (Cinema) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Cinema) {
        val (title, theater) = item
        binding.title = title
        binding.description =
            binding.root.context.getString(
                R.string.movie_count_for_cinema_format,
                theater.times.size.toString(),
            )
        binding.root.setOnClickListener {
            onCinemaClicked(item)
        }
    }
}
