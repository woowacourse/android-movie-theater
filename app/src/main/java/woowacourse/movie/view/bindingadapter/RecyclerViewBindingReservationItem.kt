package woowacourse.movie.view.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.view.home.movies.adapter.ReservationAdapter
import woowacourse.movie.view.reservation.Ticket
import woowacourse.movie.view.reservation.history.OnReservationEventListener

@BindingAdapter(value = ["items", "itemClickListener"])
fun setRecyclerViewItem(
    recyclerView: RecyclerView,
    items: List<Ticket>?,
    itemClickListener: OnReservationEventListener?,
) {
    if (itemClickListener == null) return

    val adapter = recyclerView.adapter
    if (adapter == null) {
        val reservationAdapter = ReservationAdapter(itemClickListener)
        recyclerView.adapter = reservationAdapter
        reservationAdapter.submitList(items ?: emptyList())
    } else if (adapter is ReservationAdapter) {
        adapter.submitList(items ?: emptyList())
    }
}
