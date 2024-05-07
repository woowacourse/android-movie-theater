package woowacourse.movie.ui.home.theater

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.movie.Theater
import woowacourse.movie.ui.reservation.MovieReservationActivity

class TheaterViewHolder(
    private val binding: ItemTheaterBinding,
    private val movieContentId: Long,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(theater: Theater) {
        binding.theater = theater
        binding.root.setOnClickListener {
            MovieReservationActivity.startActivity(binding.root.context, movieContentId, theater.id)
        }
    }
}
