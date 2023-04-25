package woowacourse.movie.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.view.data.ReservationViewData
import woowacourse.movie.view.data.ReservationViewDatas
import woowacourse.movie.view.viewholder.ReservationViewHolder

class ReservationAdapter(
    private val reservationViewDatas: ReservationViewDatas,
    private val onItemClick: (ReservationViewData) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ReservationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_reservation, parent, false)
        ) { onItemClick(reservationViewDatas.value[it]) }
    }

    override fun getItemCount(): Int = reservationViewDatas.value.size
    override fun getItemId(position: Int): Long = position.toLong()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ReservationViewHolder).bind(reservationViewDatas.value[position])
    }
}
