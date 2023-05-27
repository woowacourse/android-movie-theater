package woowacourse.movie.fragment.reservationlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.view.data.ReservationViewData
import woowacourse.movie.view.data.ReservationsViewData

class ReservationAdapter(
    private val reservationsViewData: ReservationsViewData,
    private val onClickItem: (ReservationViewData) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemReservationBinding =
            ItemReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationViewHolder(
            binding
        ) { onClickItem(reservationsViewData.value[it]) }
    }

    override fun getItemCount(): Int = reservationsViewData.value.size
    override fun getItemId(position: Int): Long = position.toLong()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ReservationViewHolder).bind(reservationsViewData.value[position])
    }
}
