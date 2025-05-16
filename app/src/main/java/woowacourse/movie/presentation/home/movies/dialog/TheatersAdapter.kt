package woowacourse.movie.presentation.home.movies.dialog

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.presentation.common.model.TheaterUiModel

class TheatersAdapter(
    private val eventListener: TheaterViewHolder.OnTheaterEventListener,
) : ListAdapter<TheaterUiModel, TheaterViewHolder>(TheatersDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder = TheaterViewHolder(parent, eventListener)

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        holder.bind(currentList[position])
    }
}
