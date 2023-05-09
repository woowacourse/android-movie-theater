package woowacourse.movie.reservationList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.common.model.ReservationViewData
import woowacourse.movie.databinding.ItemReservationBinding
import java.time.format.DateTimeFormatter

class ReservationViewHolder private constructor(
    private val binding: ItemReservationBinding,
    onClickItem: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onClickItem(adapterPosition)
        }
    }

    fun bind(reservation: ReservationViewData) {
        val dateFormat =
            DateTimeFormatter.ofPattern(binding.root.context.getString(R.string.reservation_item_datetime_format))
        binding.itemReservationDatetime.text = dateFormat.format(reservation.reservationDetail.date)
        binding.itemReservationTitle.text = reservation.movie.title
    }

    companion object {
        fun from(
            root: ViewGroup,
            onClickItem: (Int) -> Unit
        ): ReservationViewHolder {
            return ReservationViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(root.context), R.layout.item_reservation, root, false
                ),
                onClickItem
            )
        }
    }
}
