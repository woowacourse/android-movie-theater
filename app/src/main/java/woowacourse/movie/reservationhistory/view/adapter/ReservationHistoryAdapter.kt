package woowacourse.movie.reservationhistory.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.data.ReservationHistoryEntity
import woowacourse.movie.databinding.ItemReservationHistoryBinding
import woowacourse.movie.reservationhistory.view.listener.ReservationHistoryClickListener

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
                ReservationHistoryEntity(1, "1", "2", "3", 4, "5", "6"),
                ReservationHistoryEntity(1, "1", "2", "3", 4, "5", "6"),
            )
    }
}
