package woowacourse.movie.presentation.view.movies.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.presentation.model.TheaterUiModel

class TheatersAdapter(
    private val onClickTheater: (TheaterUiModel) -> Unit,
) : ListAdapter<TheaterUiModel, RecyclerView.ViewHolder>(TheatersDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemTheaterBinding>(inflater, R.layout.item_theater, parent, false)
        return TheaterViewHolder(binding, onClickTheater)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        (holder as? TheaterViewHolder)?.bind(currentList[position])
    }
}
