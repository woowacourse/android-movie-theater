package woowacourse.movie.ui.view.cinema.adapter

import android.view.View.OnClickListener
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemCinemaBinding
import woowacourse.movie.domain.cinema.Cinema
import woowacourse.movie.domain.reservation.ShowtimePolicy
import java.time.LocalDateTime

class CinemaViewHolder(
    private val binding: ItemCinemaBinding,
    val onClick: (cinemaName: String, showtimePolicy: ShowtimePolicy) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cinema: Cinema) {
        binding.cinema = cinema
        binding.currentTime = LocalDateTime.now()
        binding.onCinemaClick =
            OnClickListener { this@CinemaViewHolder.onClick(cinema.name, cinema.showtimePolicy) }
    }
}
