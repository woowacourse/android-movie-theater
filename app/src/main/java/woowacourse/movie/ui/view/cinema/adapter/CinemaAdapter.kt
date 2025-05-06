package woowacourse.movie.ui.view.cinema.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.ItemCinemaBinding
import woowacourse.movie.domain.cinema.Cinema
import woowacourse.movie.domain.reservation.ShowtimePolicy

class CinemaAdapter(
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
        val binding: ItemCinemaBinding =
            ItemCinemaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return CinemaViewHolder(binding, onClickItem)
    }

    override fun onBindViewHolder(
        holder: CinemaViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}
