package woowacourse.movie.movie.movielist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.dto.AdDto
import woowacourse.movie.movie.dto.movie.MovieDto
import woowacourse.movie.movie.dto.movie.MovieDummy
import woowacourse.movie.movie.moviedetail.MovieDetailActivity

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        setUpMovieData(view)
        return view
    }

    private fun setUpMovieData(view: View) {
        val movieRecyclerView = view.findViewById<RecyclerView>(R.id.movie_rv)
        val movieAdapter = MovieRVAdapter(
            MovieDummy.movieDatas,
            AdDto.getAdData(),
        )

        movieRecyclerView.adapter = movieAdapter
        onMovieItemClickListener(movieAdapter)
        onAdItemClickListener(movieAdapter)
    }

    private fun onMovieItemClickListener(adapter: MovieRVAdapter) {
        adapter.itemMovieClick = object : OnClickListener<MovieDto> {
            override fun onClick(item: MovieDto) {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra(MOVIE_KEY, item)
                startActivity(intent)
            }
        }
    }

    private fun onAdItemClickListener(adapter: MovieRVAdapter) {
        adapter.itemAdClick = object : OnClickListener<AdDto> {
            override fun onClick(item: AdDto) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                startActivity(intent)
            }
        }
    }

    companion object {
        private const val MOVIE_KEY = "movie"
    }
}
