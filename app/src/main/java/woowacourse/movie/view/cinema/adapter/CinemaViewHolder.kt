package woowacourse.movie.view.cinema.adapter

import android.view.View.OnClickListener
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemCinemaBinding
import woowacourse.movie.domain.cinema.Cinema
import woowacourse.movie.domain.reservation.Screening

class CinemaViewHolder(
    private val binding: ItemCinemaBinding,
    private val onClick: (cinema: Cinema) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        cinema: Cinema,
        screening: Screening,
    ) {
        bindData(cinema, screening)
    }

    private fun bindData(
        cinema: Cinema,
        screening: Screening,
    ) {
        binding.name = cinema.name
        binding.showtimesCount = cinema.showtimeCount(screening)
        binding.onItemClickListener = OnClickListener { onClick(cinema) }
    }
}
