package woowacourse.movie.presentation.activities.main.fragments.history

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.presentation.activities.main.fragments.history.viewholder.HistoryViewHolder
import woowacourse.movie.presentation.base.BaseViewHolder
import woowacourse.movie.presentation.model.item.ListItem
import woowacourse.movie.presentation.model.item.Reservation

class HistoryListAdapter(
    private val items: List<Reservation>,
    private val onItemClick: (ListItem) -> Unit = {},
) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder {
        return HistoryViewHolder(parent) { position ->
            onItemClick(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        (holder as HistoryViewHolder).bind(items[position])
    }
}
