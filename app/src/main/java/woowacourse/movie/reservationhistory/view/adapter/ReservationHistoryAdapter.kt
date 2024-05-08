package woowacourse.movie.reservationhistory.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.data.ReservationHistoryEntity
import woowacourse.movie.databinding.ItemReservationHistoryBinding
import woowacourse.movie.reservationhistory.view.listener.ReservationHistoryClickListener
import java.time.LocalDate
import java.time.LocalTime

class ReservationHistoryAdapter(
    private val clickListener: ReservationHistoryClickListener,
) : RecyclerView.Adapter<ReservationHistoryViewHolder>() {
    private var reservationHistories: List<ReservationHistoryEntity> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemReservationHistoryBinding =
            ItemReservationHistoryBinding.inflate(inflater, parent, false)
        return ReservationHistoryViewHolder(itemReservationHistoryBinding)
    }

    override fun getItemCount(): Int {
        return reservationHistories.size
    }

    override fun onBindViewHolder(
        holder: ReservationHistoryViewHolder,
        position: Int,
    ) {
        holder.bind(reservationHistories[position], clickListener)
    }

    fun updateReservationHistory(reservationHistories: List<ReservationHistoryEntity>) {
//        this.reservationHistories = reservationHistories
        this.reservationHistories =
            listOf(
                ReservationHistoryEntity(
                    0L,
                    "타이타닉",
                    LocalDate.of(2024, 4, 1).toString(),
                    LocalTime.of(12, 30).toString(),
                    4,
                    "A1, B1, A3, D1",
                    "선릉",
                ),
            )
    }
}
