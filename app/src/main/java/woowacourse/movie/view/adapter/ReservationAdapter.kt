package woowacourse.movie.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.ReservationViewData
import woowacourse.movie.data.ReservationsViewData
import woowacourse.movie.view.viewholder.ReservationViewHolder

class ReservationAdapter(
    private val reservationsViewData: ReservationsViewData,
    private val onClickItem: (ReservationViewData) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ReservationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_reservation, parent, false)
        ) { onClickItem(reservationsViewData.value[it]) }
    }

    override fun getItemCount(): Int = reservationsViewData.value.size
    override fun getItemId(position: Int): Long = position.toLong()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ReservationViewHolder).bind(reservationsViewData.value[position])
    }
}
