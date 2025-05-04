package woowacourse.movie.presentation.home.movies.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.presentation.common.model.TheaterUiModel

class TheatersAdapter(
    private val onClickTheater: (TheaterUiModel) -> Unit,
) : ListAdapter<TheaterUiModel, TheaterViewHolder>(TheatersDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemTheaterBinding>(inflater, R.layout.item_theater, parent, false)
        return TheaterViewHolder(binding, onClickTheater)
    }

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        holder.bind(currentList[position])
    }
}
