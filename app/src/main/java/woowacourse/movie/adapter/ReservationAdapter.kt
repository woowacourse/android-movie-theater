package woowacourse.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import domain.Reservation
import woowacourse.movie.R
import woowacourse.movie.viewholder.ReservationViewHolder

class ReservationAdapter(private val reservations: List<Reservation>) :
    RecyclerView.Adapter<ReservationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_reservation, parent, false)

        return ReservationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reservations.size
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        holder.bind(reservations[position])
    }
}
