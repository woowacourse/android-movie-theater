package woowacourse.movie.presentation.homefragments.reservationList

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.presentation.homefragments.reservationList.uiModel.ReservationItemUiModel

class ReservationListAdapter(
    private var reservations: List<ReservationItemUiModel> = emptyList(),
    private val listener: ReservationListClickListener,
) : RecyclerView.Adapter<ReservationListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationListViewHolder {
        val binding = ItemReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationListViewHolder(binding, listener)
    }

    override fun onBindViewHolder(
        holder: ReservationListViewHolder,
        position: Int,
    ) {
        holder.bind(reservations[position])
    }

    override fun getItemId(position: Int): Long {
        Log.d("reservationAdapter", "itemId: ${reservations[position].id}")
        return reservations[position].id
    }

    override fun getItemCount(): Int {
        return reservations.size
    }
}
