package woowacourse.movie.view.moviemain.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieAdItemBinding
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.util.DATE_FORMATTER
import woowacourse.movie.view.model.MovieUiModel

sealed class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class MovieItemViewHolder(private val binding: MovieItemBinding) :
        ItemViewHolder(binding.root) {
        fun set(movie: MovieUiModel, onClick: MovieListAdapter.OnItemClick) {
            val context = binding.movieTitle.context
            binding.movie = movie
            binding.onItemClick = onClick
            binding.moviePoster.setImageResource(movie.posterResourceId)
            binding.movieScreeningDate.text =
                context.getString(R.string.screening_date_format).format(
                    movie.startDate.format(DATE_FORMATTER),
                    movie.endDate.format(DATE_FORMATTER),
                )
        }
    }

    class AdItemViewHolder(private val binding: MovieAdItemBinding) : ItemViewHolder(binding.root) {
        fun set(@DrawableRes resId: Int) {
            binding.adImageview.setImageResource(resId)
        }
    }

    companion object {
        fun of(parent: ViewGroup, type: ListViewType): ItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(type.id, parent, false)
            return when (type) {
                ListViewType.NORMAL_VIEWTYPE -> MovieItemViewHolder(MovieItemBinding.bind(view))
                ListViewType.AD_VIEWTYPE -> AdItemViewHolder(MovieAdItemBinding.bind(view))
            }
        }
    }
}
