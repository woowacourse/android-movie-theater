package woowacourse.movie.presentation.ui.main.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.HolderReservationHistoryBinding
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.presentation.utils.ItemDiffCallback

class ReservationHistoryRecyclerViewAdapter :
    ListAdapter<Reservation, ReservationHistoryRecyclerViewHolder>(
        ReservationHistoryAdapterDiffCallback,
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationHistoryRecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HolderReservationHistoryBinding.inflate(inflater, parent, false)
        return ReservationHistoryRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ReservationHistoryRecyclerViewHolder,
        position: Int,
    ) {
        holder.bind(currentList[position])
    }

    companion object {
        private val ReservationHistoryAdapterDiffCallback =
            ItemDiffCallback<Reservation>(
                onItemsTheSame = { old, new -> old.id == new.id },
                onContentsTheSame = { old, new -> old == new },
            )
    }
}
