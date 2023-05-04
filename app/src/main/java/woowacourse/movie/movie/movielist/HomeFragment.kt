package woowacourse.movie.movie.movielist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.dto.AdDto
import woowacourse.movie.movie.dto.movie.MovieDto
import woowacourse.movie.movie.dto.movie.MovieDummy
import woowacourse.movie.movie.moviedetail.MovieDetailActivity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setUpBinding()
        setUpMovieData()
        return binding.root
    }

    private fun setUpBinding() {
        binding = FragmentHomeBinding.inflate(layoutInflater)
    }

    private fun setUpMovieData() {
        val movieAdapter = MovieAdapter(
            MovieDummy.movieDatas,
            AdDto.getAdData(),
        )

        binding.movieRv.adapter = movieAdapter
        onMovieItemClickListener(movieAdapter)
        onAdItemClickListener(movieAdapter)
    }

    private fun onMovieItemClickListener(adapter: MovieAdapter) {
        adapter.itemMovieClick = object : OnClickListener<MovieDto> {
            override fun onClick(item: MovieDto) {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra(MOVIE_KEY, item)
                startActivity(intent)
            }
        }
    }

    private fun onAdItemClickListener(adapter: MovieAdapter) {
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
