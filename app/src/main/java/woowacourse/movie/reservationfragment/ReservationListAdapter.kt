package woowacourse.movie.reservationfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import woowacourse.movie.R
import woowacourse.movie.reservationfragment.ReservationViewHolder
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
        val view: View
        val viewHolder: ReservationViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_reservation,parent, false)
            viewHolder = ReservationViewHolder(view, reservations, onClick)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ReservationViewHolder
        }

        viewHolder.bindReservation(position)

        return view
    }
}
