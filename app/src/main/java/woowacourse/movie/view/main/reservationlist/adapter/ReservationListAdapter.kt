package woowacourse.movie.view.main.reservationlist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.model.reservation.ReservationInfo

class ReservationListAdapter(
    private val clickListener: ReservationListClickListener,
) : ListAdapter<ReservationInfo, RecyclerView.ViewHolder>(ReservationDataDiffUtil) {
    override fun getItemViewType(position: Int): Int = VIEW_TYPE_RESERVATION_INFO

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_RESERVATION_INFO ->
                ReservationListViewHolder.Companion.from(
                    parent,
                    clickListener,
                )

            else -> throw IllegalArgumentException(ERROR_INVALID_VIEWTYPE)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ReservationListViewHolder -> holder.bind(getItem(position))
        }
    }

    companion object {
        private const val VIEW_TYPE_RESERVATION_INFO = 0
        private const val ERROR_INVALID_VIEWTYPE = "지원하지 않는 viewType입니다."
    }
}
