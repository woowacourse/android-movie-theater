package woowacourse.movie.presentation.main.home.theater

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.databinding.FragmentTheaterBinding
import woowacourse.movie.domain.model.home.Theater
import woowacourse.movie.presentation.detail.DetailActivity
import woowacourse.movie.presentation.main.home.HomeFragment.Companion.EXTRA_MOVIE_ID_KEY_TO_FRAGMENT
import woowacourse.movie.presentation.main.home.theater.adapter.OnTheaterItemClickListener
import woowacourse.movie.presentation.main.home.theater.adapter.TheaterAdapter

class TheaterFragment : BottomSheetDialogFragment(), TheaterContract.View {
    private lateinit var binding: FragmentTheaterBinding
    private lateinit var presenter: TheaterPresenter
    private lateinit var theaterAdapter: TheaterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTheaterBinding.inflate(inflater, container, false)
        presenter = TheaterPresenter(this)

        val movieId = arguments?.getLong(EXTRA_MOVIE_ID_KEY_TO_FRAGMENT) ?: INVALID_MOVIE_ID
        presenter.loadTheaters(movieId)

        return binding.root
    }

    override fun showTheaters(movieId: Long, theaters: List<Theater>) {
        if (theaters.isEmpty()) {
            binding.theaterRecyclerView.visibility = View.INVISIBLE
            binding.tvEmptyTheater.visibility = View.VISIBLE
        } else {
            binding.tvEmptyTheater.visibility = View.INVISIBLE
            theaterAdapter = TheaterAdapter(
                movieId, theaters,
                object : OnTheaterItemClickListener {
                    override fun onClick(movieId: Long, theaterId: Long) {
                        presenter.itemClicked(movieId, theaterId)
                    }
                }
            )
            binding.theaterRecyclerView.adapter = theaterAdapter
            binding.theaterRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun navigateToDetailActivity(movieId: Long, theaterId: Long) {
        val intent = Intent(activity, DetailActivity::class.java).apply {
            putExtra(EXTRA_MOVIE_ID_KEY, movieId)
            putExtra(EXTRA_THEATER_ID_KEY, theaterId)
        }
        startActivity(intent)
    }

    companion object {
        const val INVALID_MOVIE_ID = -1L
        const val EXTRA_MOVIE_ID_KEY = "movie_id_key"
        const val EXTRA_THEATER_ID_KEY = "theater_id_key"
    }
}
