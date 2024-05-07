package woowacourse.movie.feature.theater.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.theater.Theater

class TheaterSelectionViewHolder(
    private val binding: ItemTheaterBinding,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.viewHolder = this
    }

    fun bind(
        theater: Theater,
        screeningCount: Int,
        onTheaterSelected: OnTheaterSelected,
    ) {
        with(binding) {
            this.theater = theater
            this.screeningCount = screeningCount
            clTheater.setOnClickListener {
                onTheaterSelected(theater.theaterId)
            }
        }
    }
}
