package woowacourse.movie.presentation.homefragments.reservation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.model.Reservation

class ReservationAdapter(private val listener: ReservationItemClickListener) : RecyclerView.Adapter<ReservationViewHolder>() {
    private var reservations: List<Reservation> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationViewHolder {
        val binding =
            ItemReservationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return ReservationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ReservationViewHolder,
        position: Int,
    ) {
        holder.bind(reservations[position], listener)
    }

    override fun getItemCount(): Int {
        return reservations.size
    }

    fun updateReservations(newReservations: List<Reservation>) {
        reservations = newReservations
        notifyDataSetChanged()
    }
}
