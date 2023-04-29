package woowacourse.movie.view.moviemain.movielist

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieAdItemBinding
import woowacourse.movie.view.model.MovieListModel

class MovieAdViewHolder(
    private val binding: MovieAdItemBinding,
    private val onViewClick: MovieListAdapter.OnItemClick
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(ad: MovieListModel.MovieAdModel) {
        binding.adImageview.setImageResource(ad.banner)
        binding.adImageview.setOnClickListener {
            onViewClick.onClick(ad)
        }
    }
}
