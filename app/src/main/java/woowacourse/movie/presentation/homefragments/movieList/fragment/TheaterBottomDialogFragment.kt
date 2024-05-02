package woowacourse.movie.presentation.homefragments.movieList.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterBottomSheetDialogBinding
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
    private lateinit var binding: FragmentTheaterBottomSheetDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_theater_bottom_sheet_dialog,
                container,
                false,
            )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadTheaters(movieId)
    }

    override fun showTheaterList(theaterList: List<Theater>) {
        val adapter =
            TheaterAdapter(theaterList, movieId, this)
        binding.theaterListRv.adapter = adapter
    }

    override fun onTheaterClick(
        theaterId: Long,
        movieId: Long,
    ) {
        startActivity(TicketingActivity.createIntent(requireActivity(), theaterId, movieId))
    }
}
