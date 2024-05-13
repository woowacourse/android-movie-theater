package woowacourse.movie.reservationlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.reservationlist.uimodel.ReservationUiModel

class ReservationAdapter(
    private var reservations: List<ReservationUiModel>,
    private val adapterClickListener: AdapterClickListener,
) : RecyclerView.Adapter<ReservationViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationViewHolder {
        val binding =
            ItemReservationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return ReservationViewHolder(binding, adapterClickListener)
    }

    override fun onBindViewHolder(
        holder: ReservationViewHolder,
        position: Int,
    ) {
        holder.onBind(reservations[position])
    }

    override fun getItemCount(): Int = reservations.size

    fun updateData(newReservations: List<ReservationUiModel>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(reservations, newReservations))
        reservations = newReservations
        diffResult.dispatchUpdatesTo(this)
    }

    class DiffCallback(
        private val old: List<ReservationUiModel>,
        private val new: List<ReservationUiModel>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = old.size

        override fun getNewListSize(): Int = new.size

        override fun areItemsTheSame(
            oldItemPosition: Int,
            newItemPosition: Int,
        ): Boolean = old[oldItemPosition] == new[newItemPosition]

        override fun areContentsTheSame(
            oldItemPosition: Int,
            newItemPosition: Int,
        ): Boolean = old[oldItemPosition] == new[newItemPosition]
    }
}
