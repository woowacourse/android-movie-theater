package woowacourse.movie.ui.view.cinema.adapter

import android.view.View.OnClickListener
import woowacourse.movie.databinding.ItemCinemaBinding
import woowacourse.movie.domain.cinema.Cinema
import woowacourse.movie.domain.reservation.ShowtimePolicy
import java.time.LocalDateTime

class CinemaViewHolder(
    private val binding: ItemCinemaBinding,
    val onClick: (cinemaName: String, showtimePolicy: ShowtimePolicy) -> Unit,
) : BaseViewHolder<Cinema>(binding.root) {
    override fun bind(item: Cinema) {
        binding.cinema = item
        binding.currentTime = LocalDateTime.now()
        binding.onCinemaClick =
            OnClickListener { this@CinemaViewHolder.onClick(item.name, item.showtimePolicy) }
    }
}
