package woowacourse.movie.ui.view.cinema.adapter

import android.view.View.OnClickListener
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemCinemaBinding
import woowacourse.movie.domain.cinema.Cinema
import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.domain.reservation.ShowtimePolicy

class CinemaViewHolder(
    private val binding: ItemCinemaBinding,
    val onClick: (cinemaName: String, showtimePolicy: ShowtimePolicy) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    var name = ""
    var showtimesCount = 0
    var onItemClickListener: OnClickListener? = null

    fun bind(
        cinema: Cinema,
        screening: Screening,
    ) {
        binding.cinema = this
        name = cinema.name
        showtimesCount = cinema.showtimeCount(screening)
        onItemClickListener =
            OnClickListener { this@CinemaViewHolder.onClick(cinema.name, cinema.showtimePolicy) }
    }
}
