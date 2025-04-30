package woowacourse.movie.view.cinema.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.R
import woowacourse.movie.domain.cinema.Cinema
import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.domain.reservation.ShowtimePolicy

class CinemaAdapter(
    private val screening: Screening,
    private val onClickItem: (cinemaName: String, showtimePolicy: ShowtimePolicy) -> Unit,
) :
    ListAdapter<Cinema, CinemaViewHolder>(
            object : DiffUtil.ItemCallback<Cinema>() {
                override fun areItemsTheSame(
                    oldItem: Cinema,
                    newItem: Cinema,
                ): Boolean {
                    return oldItem === newItem
                }

                override fun areContentsTheSame(
                    oldItem: Cinema,
                    newItem: Cinema,
                ): Boolean {
                    return oldItem == newItem
                }
            },
        ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CinemaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cinema, parent, false)
        return CinemaViewHolder(view, onClickItem)
    }

    override fun onBindViewHolder(
        holder: CinemaViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position), screening)
    }
}
