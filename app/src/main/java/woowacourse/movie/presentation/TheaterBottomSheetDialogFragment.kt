package woowacourse.movie.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.presentation.contract.TheaterBottomSheetContract
import woowacourse.movie.presentation.presenter.TheaterBottomSheetPresenterImpl
import woowacourse.movie.presentation.view.MainActivity.Companion.MOVIE_ID_KEY
import woowacourse.movie.presentation.view.MainActivity.Companion.DEFAULT_MOVIE_ID
import woowacourse.movie.repository.TheaterRepositoryImpl

class TheaterBottomSheetDialogFragment : BottomSheetDialogFragment(), TheaterBottomSheetContract.View {
    private val theaterPresenter: TheaterBottomSheetContract.Presenter by lazy {
        TheaterBottomSheetPresenterImpl(this)
    }
    private var movieId = DEFAULT_MOVIE_ID
    private lateinit var theaterAdapter: TheaterAdapter
    private val theaterRepository = TheaterRepositoryImpl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.theater_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieId = arguments?.getInt(MOVIE_ID_KEY, DEFAULT_MOVIE_ID) ?: DEFAULT_MOVIE_ID
        theaterAdapter = TheaterAdapter(theaterRepository.theatersInfo(movieId), ::onTheaterClicked)
        val theatersView = view.findViewById<RecyclerView>(R.id.theater_recycler_view)
        theatersView.adapter = theaterAdapter
    }

    private fun onTheaterClicked(theaterId: Int) {
        moveToMovieDetail(movieId, theaterId)
    }

    override fun showTheaterInfo(theaterInfo: List<Pair<Theater, Int>>) {

    }

    override fun moveToMovieDetail(movieId: Int, theaterId: Int) {
        TODO("Not yet implemented")
    }
}
