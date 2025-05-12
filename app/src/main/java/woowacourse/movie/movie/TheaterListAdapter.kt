package woowacourse.movie.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.R
import woowacourse.movie.databinding.BottomSheetItemBinding
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Theater

class TheaterListAdapter(
    private val items: List<Theater>,
    private val movie: Movie,
    private val onClicked: (Theater) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<BottomSheetItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.bottom_sheet_item,
            parent,
            false,
        )
        return TheaterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        (holder as TheaterViewHolder).setItem(item, movie, onClicked)
    }

    override fun getItemCount(): Int = items.size
}
