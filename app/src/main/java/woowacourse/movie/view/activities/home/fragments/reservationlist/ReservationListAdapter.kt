package woowacourse.movie.view.activities.home.fragments.reservationlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemReservationListBinding

class ReservationListAdapter(
    private val reservationListViewItems: List<ReservationListViewItemUIState>,
    private val onItemClick: (Long) -> Unit
) : RecyclerView.Adapter<ReservationItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reservation_list, parent, false)

        return ReservationItemViewHolder(ItemReservationListBinding.bind(view), onItemClick)
    }

    override fun getItemCount(): Int = reservationListViewItems.size

    override fun onBindViewHolder(holder: ReservationItemViewHolder, position: Int) {
        holder.bind(reservationListViewItems[position])
    }
}
