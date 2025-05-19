package woowacourse.movie.view.reservelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.domain.model.Ticket

class ReservationListViewHolder(
    val parent: ViewGroup,
    val onItemClick: (Ticket) -> Unit,
    val binding: ItemReservationBinding = inflate(parent),
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var data: Ticket

    init {
        binding.root.setOnClickListener {
            onItemClick(data)
        }
    }

    fun bind(data: Ticket) {
        this.data = data
        binding.ticket = data
    }

    companion object {
        fun inflate(parent: ViewGroup): ItemReservationBinding {
            return ItemReservationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        }
    }
}
