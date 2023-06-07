package woowacourse.movie.presentation.activities.main.fragments.theaterPicker

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.presentation.activities.main.fragments.theaterPicker.viewholder.TheaterViewHolder
import woowacourse.movie.presentation.base.BaseViewHolder
import woowacourse.movie.presentation.model.item.Theater

class TheaterListAdapter(
    private val items: List<Theater>,
    private val onItemClick: (Theater) -> Unit,
) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder {
        return TheaterViewHolder(parent) { position ->
            onItemClick(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        (holder as TheaterViewHolder).bind(items[position])
    }
}
