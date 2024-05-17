package woowacourse.movie.ui.reservationhistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HolderScreenReservationHistoryBinding
import woowacourse.movie.db.reservationhistory.ReservationHistory
import woowacourse.movie.ui.reservationhistory.ReservationHistoryActionHandler

class ReservationHistoryAdapter(
    private val reservationHistoryActionHandler: ReservationHistoryActionHandler,
) : ListAdapter<ReservationHistory, RecyclerView.ViewHolder>(ReservationHistoryDiffUtil()) {
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        if (::inflater.isInitialized.not()) {
            inflater = LayoutInflater.from(parent.context)
        }

        val binding = HolderScreenReservationHistoryBinding.inflate(inflater, parent, false)
        return ReservationHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        (holder as ReservationHistoryViewHolder).bind(getItem(position), reservationHistoryActionHandler)
    }
}
