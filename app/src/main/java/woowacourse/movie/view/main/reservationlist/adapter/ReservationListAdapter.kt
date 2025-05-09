package woowacourse.movie.view.main.reservationlist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.model.reservation.ReservationInfo
import woowacourse.movie.model.seat.Seats
import java.time.LocalDate

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
            is ReservationListViewHolder -> holder.bind(dummyReservationInfos[position])
        }
    }

    companion object {
        private const val VIEW_TYPE_RESERVATION_INFO = 0
        private const val ERROR_INVALID_VIEWTYPE = "지원하지 않는 viewType입니다."

        val dummyReservationInfos =
            listOf(
                ReservationInfo(
                    title = "클레멘타인",
                    date = LocalDate.now(),
                    time = "10:00",
                    seats = Seats.create(),
                    price = 10000,
                    theaterName = "선릉 극장",
                ),
                ReservationInfo(
                    title = "디워",
                    date = LocalDate.now(),
                    time = "9:00",
                    seats = Seats.create(),
                    price = 20000,
                    theaterName = "선릉 극장",
                ),
                ReservationInfo(
                    title = "성냥팔이 소녀의 재림",
                    date = LocalDate.now(),
                    time = "19:00",
                    seats = Seats.create(),
                    price = 30000,
                    theaterName = "선릉 극장",
                ),
            )
    }
}
