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
import woowacourse.movie.movie.theater.TheaterSheetFragment

class HomeFragment : Fragment(), HomeContract.View {
    private lateinit var binding: FragmentHomeBinding
    override lateinit var presenter: HomeContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setUpBinding()
        presenter = HomePresenter(this)
        presenter.initFragment()
        return binding.root
    }

    private fun setUpBinding() {
        binding = FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun setUpMovieData(movies: List<MovieDto>, ads: AdDto) {
        val movieAdapter = MovieAdapter(movies, ads)
        binding.movieRv.adapter = movieAdapter
        onMovieItemClickListener(movieAdapter)
        onAdItemClickListener(movieAdapter)
    }

    private fun onMovieItemClickListener(adapter: MovieAdapter) {
        adapter.itemMovieClick = object : OnClickListener<MovieDto> {
            override fun onClick(item: MovieDto) {
                showTheaterSheet(item)
            }
        }
    }

    private fun showTheaterSheet(item: MovieDto) {
        TheaterSheetFragment.newInstance(item)
            .show(parentFragmentManager, TheaterSheetFragment.THEATER_KEY)
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
        const val MOVIE_KEY = "movie"
    }
}
