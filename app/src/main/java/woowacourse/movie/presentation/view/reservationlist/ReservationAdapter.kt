package woowacourse.movie.presentation.view.reservationlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.presentation.model.ReservationInfoUiModel

class ReservationAdapter(
    private val clickListener: ReservationClickListener,
) : ListAdapter<ReservationInfoUiModel, RecyclerView.ViewHolder>(ReservationDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReservationBinding.inflate(inflater, parent, false)
        return ReservationViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        (holder as ReservationViewHolder).bind(getItem(position))
    }
}
