package woowacourse.movie.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.model.movie.MovieContent

object MovieContentDiffUtil : DiffUtil.ItemCallback<MovieContent>() {
    override fun areItemsTheSame(oldItem: MovieContent, newItem: MovieContent): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieContent, newItem: MovieContent): Boolean {
        return oldItem == newItem
    }
}
