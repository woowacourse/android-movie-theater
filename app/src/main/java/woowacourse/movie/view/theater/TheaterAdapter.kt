package woowacourse.movie.view.theater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.TheaterUIModel

class TheaterAdapter(
    private val clickListener: TheaterClickListener,
) : ListAdapter<TheaterUIModel, RecyclerView.ViewHolder>(TheaterDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTheaterBinding.inflate(inflater, parent, false)
        return TheaterViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        (holder as TheaterViewHolder).bind(getItem(position))
    }
}
