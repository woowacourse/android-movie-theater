package woowacourse.movie.presentation.homefragments.movieList.viewholder

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.model.Movie
import woowacourse.movie.presentation.homefragments.movieList.listener.MovieListClickListener

class MovieViewHolder(private val binding: ItemMovieBinding, val listener: MovieListClickListener) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Movie) {
        binding.movie = movie
        binding.listener = listener
    }
}

@BindingAdapter("imgRes")
fun bindImage(
    imageView: ImageView,
    resId: Int,
) {
    imageView.setImageResource(resId)
}
