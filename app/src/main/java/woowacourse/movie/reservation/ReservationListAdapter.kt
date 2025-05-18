package woowacourse.movie.reservation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.data.Reservation
import woowacourse.movie.databinding.ReservationItemBinding

class ReservationListAdapter(
    private val items: List<Reservation>,
    private val reservationClickListener: ReservationClickListener,
) : RecyclerView.Adapter<ReservationViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationViewHolder {
        val binding = ReservationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationViewHolder(binding, reservationClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: ReservationViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }
}
