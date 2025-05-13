package woowacourse.movie.reservationfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.domain.BookingStatus

class ReservationListAdapter(
    val reservations : List<BookingStatus>,
    val onClick: (BookingStatus) -> Unit
) : BaseAdapter() {
    override fun getCount(): Int = reservations.size

    override fun getItem(position: Int): BookingStatus = reservations[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View? {
        val binding: ItemReservationBinding
        val viewHolder: ReservationViewHolder

        if (convertView == null) {
            binding = ItemReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            viewHolder = ReservationViewHolder(binding, reservations, onClick)
            binding.root.tag = viewHolder
        } else {
            binding = ItemReservationBinding.bind(convertView)
            viewHolder = binding.root.tag as ReservationViewHolder
        }

        viewHolder.bindReservation(position)

        return binding.root
    }
}
