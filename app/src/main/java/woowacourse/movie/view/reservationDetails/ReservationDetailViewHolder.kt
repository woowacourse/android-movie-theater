package woowacourse.movie.view.reservationDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.entity.MovieTicketEntity
import woowacourse.movie.databinding.ItemReservationDetailBinding
import woowacourse.movie.view.mapper.Formatter.localDateToUi
import java.time.LocalDate
import java.time.LocalTime

class ReservationDetailViewHolder(
    private val binding: ItemReservationDetailBinding,
    onReservationClick: (reservationDetailId: Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onReservationDetailClick = onReservationClick
    }

    fun bind(movieTicketEntity: MovieTicketEntity) {
        binding.movieTicketEntity = movieTicketEntity
        binding.tvReservationDetailTheaterInfo.text =
            binding.root.context.getString(
                R.string.reservation_detail_ticket_info,
                localDateToUi(
                    LocalDate.of(
                        movieTicketEntity.reservationInfoEntity.movieDateYear,
                        movieTicketEntity.reservationInfoEntity.movieDateMonth,
                        movieTicketEntity.reservationInfoEntity.movieDateDay,
                    ),
                ),
                LocalTime
                    .of(
                        movieTicketEntity.reservationInfoEntity.movieTimeHour,
                        movieTicketEntity.reservationInfoEntity.movieTimeMinute,
                    ).toString(),
                movieTicketEntity.theater.name,
            )
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onReservationClick: (Long) -> Unit,
        ): ReservationDetailViewHolder =
            ReservationDetailViewHolder(
                ItemReservationDetailBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
                onReservationClick,
            )
    }
}
