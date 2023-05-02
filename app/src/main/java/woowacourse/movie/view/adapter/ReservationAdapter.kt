package woowacourse.movie.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.contract.ReservationAdapterContract
import woowacourse.movie.data.ReservationViewData
import woowacourse.movie.data.ReservationsViewData
import woowacourse.movie.presenter.ReservationAdapterPresenter
import woowacourse.movie.view.viewholder.ReservationViewHolder

class ReservationAdapter(
    private val onClickItem: (ReservationViewData) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ReservationAdapterContract.View {
    private var reservationsViewData: ReservationsViewData = ReservationsViewData(emptyList())
    override val presenter: ReservationAdapterContract.Presenter = ReservationAdapterPresenter(this)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ReservationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_reservation, parent, false)
        ) { onClickItem(reservationsViewData.value[it]) }
    }

    override fun getItemCount(): Int = reservationsViewData.value.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ReservationViewHolder).bind(reservationsViewData.value[position])
    }

    override fun setAdapterData(reservations: ReservationsViewData) {
        reservationsViewData = reservations
        notifyDataSetChanged()
    }
}
