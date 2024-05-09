package woowacourse.movie.presentation.reservation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.model.Reservation
import woowacourse.movie.presentation.movieList.MovieItemListener

class ReservationAdapter(
    private val onClick: (reservationId: Long) -> Unit,
) : RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {
    private var items: List<Reservation> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemReservationBinding.inflate(layoutInflater, parent, false)
        return ReservationViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(
        holder: ReservationViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(reservations: List<Reservation>) {
        items = reservations
        notifyDataSetChanged()
    }

    class ReservationViewHolder(
        private val binding: ItemReservationBinding,
        private val onClick: (reservationId: Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reservation: Reservation) {
            binding.data = reservation
            binding.listener = MovieItemListener {
                onClick(reservation.id)
            }
        }
    }
}
