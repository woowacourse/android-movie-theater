package woowacourse.movie.list.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.databinding.FragmentTheaterBinding
import woowacourse.movie.detail.view.MovieInformationDetailActivity
import woowacourse.movie.list.adapter.TheaterAdapter
import woowacourse.movie.list.model.TheaterData.theaters
import woowacourse.movie.list.view.MovieListFragment.Companion.EXTRA_MOVIE_ID_KEY_TO_FRAGMENT

class TheaterBottomSheetFragment : BottomSheetDialogFragment(), TheaterFragmentContract.View {
    private lateinit var binding: FragmentTheaterBinding
    private lateinit var presenter: TheaterPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTheaterBinding.inflate(inflater, container, false)
        presenter = TheaterPresenter(this)

        val movieId = arguments?.getLong(EXTRA_MOVIE_ID_KEY_TO_FRAGMENT)
        setupRecyclerView(movieId ?: INVALID_MOVIE_ID)

        return binding.root
    }

    private fun setupRecyclerView(movieId: Long) {
        binding.theaterRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TheaterAdapter().also {
                it.initTheatersInfo(movieId, theaters)
                it.setItemClickListener(object : TheaterAdapter.OnItemClickListener {
                    override fun onClick(movieId: Long, theaterId: Long) {
                        presenter.itemClicked(movieId, theaterId)
                    }
                })
            }
        }
    }

    override fun navigateToDetailActivity(movieId: Long, theaterId: Long) {
        Intent(activity, MovieInformationDetailActivity::class.java).apply {
            putExtra(EXTRA_MOVIE_ID_KEY, movieId)
            putExtra(EXTRA_THEATER_ID_KEY, theaterId)
            startActivity(this)
        }
    }

    companion object {
        const val INVALID_MOVIE_ID = -99L
        const val EXTRA_MOVIE_ID_KEY = "movie_id_key"
        const val EXTRA_THEATER_ID_KEY = "theater_id_key"
    }
}
