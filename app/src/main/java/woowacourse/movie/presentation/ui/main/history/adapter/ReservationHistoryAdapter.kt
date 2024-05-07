package woowacourse.movie.presentation.ui.main.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.databinding.HolderReservationBinding
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.presentation.ui.main.history.ReservationHistoryActionHandler
import woowacourse.movie.presentation.ui.main.history.adapter.ReservationHistoryAdapter.ReservationHistoryViewHolder

class ReservationHistoryAdapter(
    private val actionHandler: ReservationHistoryActionHandler,
    private val reservations: MutableList<Reservation> = mutableListOf(),
) : RecyclerView.Adapter<ReservationHistoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HolderReservationBinding.inflate(inflater, parent, false)
        return ReservationHistoryViewHolder(binding, actionHandler)
    }

    override fun getItemCount(): Int = reservations.size

    override fun onBindViewHolder(
        holder: ReservationHistoryViewHolder,
        position: Int,
    ) {
        holder.bind(reservations[position])
    }

    fun updateReservations(newReservations: List<Reservation>) {
        reservations.clear()
        reservations.addAll(newReservations)
        notifyDataSetChanged()
    }

    class ReservationHistoryViewHolder(
        private val binding: HolderReservationBinding,
        private val actionHandler: ReservationHistoryActionHandler,
    ) : ViewHolder(binding.root) {
        fun bind(reservation: Reservation) {
            binding.reservation = reservation
            binding.actionHandler = actionHandler
        }
    }
}
