package woowacourse.movie.presentation.movielist.selecttheater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.SelectTheaterBottomSheetItemBinding

class SelectTheaterAdapter(
    private val movieId: Long,
    private val clickBook: (String) -> Unit,
    override val presenter: SelectTheaterContract.Presenter
) :
    ListAdapter<String, RecyclerView.ViewHolder>(SelectTheaterDiffUtil),
    SelectTheaterContract.Adapter {

    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding = SelectTheaterBottomSheetItemBinding.inflate(inflater, parent, false)
        return SelectTheaterViewHolder(binding, clickBook, ::getItem)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as SelectTheaterViewHolder
        val theater: String = getItem(position)
        viewHolder.bind(theater, presenter.getTheaterTimeTableCountByMovieId(movieId, theater))
    }

    companion object {
        private val SelectTheaterDiffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }
    }
}
