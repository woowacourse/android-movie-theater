package woowacourse.movie.reservation

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.database.Ticket
import woowacourse.movie.databinding.ItemReservationBinding

class ReservationAdapter(
    private var reservations: MutableList<Ticket> = mutableListOf()
) : RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view =
            ItemReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        holder.bind(reservations[position])
    }

    override fun getItemCount(): Int = reservations.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateReservations(newReservations: List<Ticket>) {
        reservations.clear()
        reservations.addAll(newReservations)
        notifyDataSetChanged()
    }

    class ReservationViewHolder(
        private val binding: ItemReservationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ticket: Ticket) {
            binding.ticket = ticket
        }
    }
}
