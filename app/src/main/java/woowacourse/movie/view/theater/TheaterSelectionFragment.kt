package woowacourse.movie.view.theater

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.MainActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterSelectionBinding
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.presenter.theater.TheaterSelectionContract
import woowacourse.movie.presenter.theater.TheaterSelectionPresenter
import woowacourse.movie.view.home.HomeFragment.Companion.MOVIE_ID
import woowacourse.movie.view.reservation.ReservationDetailActivity
import woowacourse.movie.view.theater.adapter.TheaterSelectionAdapter

class TheaterSelectionFragment(
    private val movieId: Int,
) : BottomSheetDialogFragment(), TheaterSelectionContract.View {
    private lateinit var binding: FragmentTheaterSelectionBinding
    private val presenter: TheaterSelectionPresenter by lazy {
        TheaterSelectionPresenter(
            view = this@TheaterSelectionFragment,
            movieId = movieId,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_theater_selection, container, false)
        initTheaterRecyclerView()
        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun navigateToDetail(
        movieId: Int,
        theaterId: Int,
    ) {
        val intent = Intent(context as MainActivity, ReservationDetailActivity::class.java)
        intent.putExtra(MOVIE_ID, movieId)
        intent.putExtra(THEATER_ID, theaterId)
        startActivity(intent)
    }

    private fun initTheaterRecyclerView() {
        val theaterSelectionAdapter =
            TheaterSelectionAdapter(
                // TODO: movieId에 맞는 데이터만 불러오기
                TheaterDao().findAll(),
            ) { theaterId ->
                presenter.loadTheater(theaterId)
            }
        binding.recyclerViewTheaterSelection.apply {
            adapter = theaterSelectionAdapter
        }
    }

    companion object {
        const val THEATER_ID = "theaterId"
    }
}
