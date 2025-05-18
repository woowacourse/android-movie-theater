package woowacourse.movie.view.reservationDetails

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.data.entity.MovieTicketEntity

class ReservationDetailAdapter(
    private val onReservationDetailClick: (id: Long) -> Unit,
) : androidx.recyclerview.widget.ListAdapter<MovieTicketEntity, ReservationDetailViewHolder>(
        object : DiffUtil.ItemCallback<MovieTicketEntity>() {
            override fun areItemsTheSame(
                oldItem: MovieTicketEntity,
                newItem: MovieTicketEntity,
            ): Boolean = oldItem.reservationInfoEntity.id == newItem.reservationInfoEntity.id

            override fun areContentsTheSame(
                oldItem: MovieTicketEntity,
                newItem: MovieTicketEntity,
            ): Boolean = oldItem == newItem
        },
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationDetailViewHolder = ReservationDetailViewHolder.from(parent, onReservationDetailClick)

    override fun onBindViewHolder(
        holder: ReservationDetailViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}
