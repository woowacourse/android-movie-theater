package woowacourse.movie.reservationlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationListBinding
import woowacourse.movie.screeningmovie.AdapterClickListener

class ReservationListAdapter(
    private val adapterClickListener: AdapterClickListener,
) :
    RecyclerView.Adapter<ReservationListAdapter.ReservationListViewHolder>() {
    private var itemList = listOf<ReservationListUiModel>()

    class ReservationListViewHolder(
        private val binding: ItemReservationListBinding,
        private val adapterClickListener: AdapterClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: ReservationListUiModel) {
            with(binding) {
                tvReservationListDate.text = item.date
                tvReservationListTime.text = item.time
                tvReservationListTheaterName.text = item.theaterName
                tvReservationListTitle.text = item.title
                root.setOnClickListener {
                    adapterClickListener.onClick(item.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationListViewHolder {
        val binding =
            ItemReservationListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationListViewHolder(binding, adapterClickListener)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(
        holder: ReservationListViewHolder,
        position: Int,
    ) {
        holder.onBind(itemList[position])
    }

    fun submitList(newItemList: List<ReservationListUiModel>) {
        itemList = newItemList
        notifyDataSetChanged()
    }
}
