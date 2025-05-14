package woowacourse.movie.presentation.home.movies.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.presentation.common.base.BaseViewHolder
import woowacourse.movie.presentation.common.model.TheaterUiModel

class TheaterViewHolder(
    parent: ViewGroup,
    eventListener: OnTheaterEventListener,
) : BaseViewHolder<TheaterUiModel, ItemTheaterBinding>(
    DataBindingUtil.inflate(
        LayoutInflater.from(parent.context),
        R.layout.item_theater,
        parent,
        false
    ),
) {

    init {
        binding.onTheaterClick = eventListener
    }

    override fun bind(item: TheaterUiModel) {
        binding.theater = item
        binding.executePendingBindings()
    }

    interface OnTheaterEventListener {
        fun onTheaterClick(theater: TheaterUiModel)
    }
}
