package woowacourse.movie.presentation.homefragments.movieList.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.model.Theater
import woowacourse.movie.presentation.homefragments.movieList.adapter.TheaterAdapter
import woowacourse.movie.presentation.homefragments.movieList.listener.TheaterClickListener
import woowacourse.movie.presentation.ticketing.TicketingActivity
import woowacourse.movie.repository.TheaterListRepository

class TheaterBottomDialogFragment(
    theaterListRepository: TheaterListRepository,
    private val movieId: Long,
) : BottomSheetDialogFragment(), TheaterClickListener, TheaterBottomDialogContract.View {
    private val presenter = TheaterBottomDialogPresenter(this, theaterListRepository)
    private lateinit var theaterRv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_theater_bottom_sheet_dialog, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        theaterRv = view.findViewById(R.id.theater_list_rv)
        presenter.loadTheaters(movieId)
    }

    override fun showTheaterList(theaterList: List<Theater>) {
        val adapter =
            TheaterAdapter(theaterList, movieId, this)
        theaterRv.adapter = adapter
    }

    override fun onTheaterClick(
        theaterId: Long,
        movieId: Long,
    ) {
        startActivity(TicketingActivity.createIntent(requireActivity(), theaterId, movieId))
    }
}
