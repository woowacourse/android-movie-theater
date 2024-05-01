package woowacourse.movie.presentation.view.screening.theater

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.presentation.view.reservation.detail.MovieDetailActivity
import woowacourse.movie.presentation.view.screening.theater.adapter.TheaterAdapter
import woowacourse.movie.presentation.view.screening.ScreeningActivity.Companion.MOVIE_ID_KEY
import woowacourse.movie.presentation.view.screening.ScreeningActivity.Companion.DEFAULT_MOVIE_ID

class TheaterBottomSheetDialogFragment : BottomSheetDialogFragment(),
    TheaterBottomSheetContract.View {
    private val theaterPresenter: TheaterBottomSheetContract.Presenter by lazy {
        TheaterBottomSheetPresenterImpl(this, movieId)
    }
    private var movieId = DEFAULT_MOVIE_ID
    private lateinit var theatersView: RecyclerView
    private lateinit var theaterAdapter: TheaterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.theater_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieId = arguments?.getInt(MOVIE_ID_KEY, DEFAULT_MOVIE_ID) ?: DEFAULT_MOVIE_ID
        theatersView = view.findViewById(R.id.theater_recycler_view)
        theaterPresenter.loadTheaters()
    }

    private fun onTheaterClicked(theaterId: Int) {
        theaterPresenter.onTheaterClicked(theaterId)
    }

    override fun showTheaterInfo(theatersInfo: List<Pair<Theater, Int>>) {
        theaterAdapter = TheaterAdapter(theatersInfo, ::onTheaterClicked)
        theatersView.adapter = theaterAdapter
    }

    override fun moveToMovieDetail(theaterId: Int) {
        val intent = Intent(activity, MovieDetailActivity::class.java)
        intent.putExtra(MOVIE_ID_KEY, movieId)
        intent.putExtra(THEATER_ID_KEY, theaterId)
        startActivity(intent)
    }

    companion object {
        const val THEATER_ID_KEY = "theaterId"
    }
}
