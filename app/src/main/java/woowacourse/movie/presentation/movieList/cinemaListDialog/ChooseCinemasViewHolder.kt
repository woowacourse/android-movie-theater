package woowacourse.movie.presentation.movieList.cinemaListDialog

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemBottomSheetTheatersBinding
import woowacourse.movie.model.Cinema

class ChooseCinemasViewHolder(
    private val binding: ItemBottomSheetTheatersBinding,
    private val onCinemaClicked: (Cinema) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Cinema) {
        val (title, theater) = item
        binding.title = title
        binding.description = theater.times.size.toString()
        binding.root.setOnClickListener {
            onCinemaClicked(item)
        }
    }
}
