package woowacourse.movie.view.home.movies.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.view.home.movies.HomeEventHandler
import woowacourse.movie.view.home.movies.model.UiModel.MovieUiModel

class MovieViewHolder private constructor(
    private val binding: MovieItemBinding,
    private val handler: HomeEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    constructor(
        parent: ViewGroup,
        handler: HomeEventHandler,
    ) : this(
        MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        handler,
    )

    fun bind(item: MovieUiModel) {
        binding.movie = item
        binding.handler = handler
    }
}
