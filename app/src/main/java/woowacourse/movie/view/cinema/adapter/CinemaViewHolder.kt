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
    private var name: String = ""
    private var showtimesCount: Int = 0
    private var onItemClickListener: OnClickListener? = null

    fun bind(
        cinema: Cinema,
        screening: Screening,
    ) {
        initData(cinema, screening)
        bindData()
    }

    private fun initData(
        cinema: Cinema,
        screening: Screening,
    ) {
        name = cinema.name
        showtimesCount = cinema.showtimeCount(screening)
        onItemClickListener = OnClickListener { onClick(cinema) }
    }

    private fun bindData() {
        binding.name = name
        binding.showtimesCount = showtimesCount
        binding.onItemClickListener = onItemClickListener
    }
}
