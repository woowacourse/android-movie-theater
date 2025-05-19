package woowacourse.movie.view.cinema.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.ItemCinemaBinding
import woowacourse.movie.domain.cinema.Cinema
import woowacourse.movie.domain.reservation.Screening

class CinemaAdapter(
    private val screening: Screening,
    private val onClickItem: (cinema: Cinema) -> Unit,
) : ListAdapter<Cinema, CinemaViewHolder>(diffUtil) {
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
        holder.bind(getItem(position), screening)
    }

    companion object {
        val diffUtil =
            object : DiffUtil.ItemCallback<Cinema>() {
                override fun areItemsTheSame(
                    oldItem: Cinema,
                    newItem: Cinema,
                ): Boolean = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: Cinema,
                    newItem: Cinema,
                ): Boolean = oldItem == newItem
            }
    }
}
