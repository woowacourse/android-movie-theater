package woowacourse.movie.view.activities.home.fragments.reservationlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ReservationListAdapter(
    private val reservationListViewItems: List<ReservationListViewItemUIState>,
    private val onItemClick: (Long) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)

        return ReservationItemViewHolder(view, onItemClick)
    }

    override fun getItemCount(): Int = reservationListViewItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ReservationItemViewHolder -> holder.bind(reservationListViewItems[position])
        }
    }
}
