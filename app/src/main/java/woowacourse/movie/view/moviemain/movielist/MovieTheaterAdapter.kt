package woowacourse.movie.view.moviemain.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieTheaterItemBinding
import woowacourse.movie.view.model.MovieTheater

class MovieTheaterAdapter(
    private val dataList: List<MovieTheater>,
    private val onTheaterClickListener: OnTheaterClickListener
) : RecyclerView.Adapter<MovieTheaterAdapter.MovieTheaterViewHolder>() {

    fun interface OnTheaterClickListener {
        fun onClick(item: MovieTheater)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTheaterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.movie_theater_item, parent, false
        )
        return MovieTheaterViewHolder(MovieTheaterItemBinding.bind(view)) {
            onTheaterClickListener.onClick(it)
        }
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MovieTheaterViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    class MovieTheaterViewHolder(
        private val binding: MovieTheaterItemBinding,
        private val onTheaterClickListener: OnTheaterClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movieTheater: MovieTheater) {
            binding.movieTheaterItem = movieTheater
            binding.movieTheaterLayout.setOnClickListener {
                onTheaterClickListener.onClick(movieTheater)
            }
        }
    }
}
