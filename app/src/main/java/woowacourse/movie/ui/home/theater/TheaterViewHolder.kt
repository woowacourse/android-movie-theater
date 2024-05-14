package woowacourse.movie.ui.home.theater

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.movie.Theater

class TheaterViewHolder(
    private val binding: ItemTheaterBinding,
    private val theaterClickListener: (Long) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(theater: Theater) {
        binding.theater = theater
        binding.root.setOnClickListener {
            theaterClickListener(theater.id)
        }
    }
}
