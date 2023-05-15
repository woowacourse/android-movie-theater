package woowacourse.movie.view.activities.home.fragments.screeninglist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.databinding.ItemScreeningBinding
import woowacourse.movie.view.activities.home.fragments.screeninglist.uistates.AdvertisementUIState
import woowacourse.movie.view.activities.home.fragments.screeninglist.uistates.ScreeningListViewItemUIState
import woowacourse.movie.view.activities.home.fragments.screeninglist.uistates.ScreeningUIState

class ScreeningListAdapter(
    private val screeningListViewItems: List<ScreeningListViewItemUIState>,
    private val onReserveButtonClick: (Long) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)

        return when (viewType) {
            R.layout.item_screening -> ScreeningItemViewHolder(
                ItemScreeningBinding.bind(view),
                onReserveButtonClick
            )

            R.layout.item_advertisement -> AdvertisementItemViewHolder(
                ItemAdvertisementBinding.bind(view)
            )

            else -> throw IllegalArgumentException("존재하지 않는 뷰 타입이 들어옴")
        }
    }

    override fun getItemCount(): Int = screeningListViewItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = screeningListViewItems[position]

        when (holder) {
            is ScreeningItemViewHolder ->
                holder.bind(item as ScreeningUIState)

            is AdvertisementItemViewHolder ->
                holder.bind(item as AdvertisementUIState)
        }
    }

    override fun getItemViewType(position: Int): Int = when (screeningListViewItems[position]) {
        is ScreeningUIState -> R.layout.item_screening
        is AdvertisementUIState -> R.layout.item_advertisement
    }
}
