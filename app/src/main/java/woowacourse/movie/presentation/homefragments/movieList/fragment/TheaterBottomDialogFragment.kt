package woowacourse.movie.presentation.homefragments.movieList.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.databinding.FragmentTheaterBottomSheetDialogBinding
import woowacourse.movie.model.Theater
import woowacourse.movie.presentation.homefragments.movieList.adapter.TheaterAdapter
import woowacourse.movie.presentation.homefragments.movieList.listener.TheaterClickListener
import woowacourse.movie.presentation.ticketing.TicketingActivity
import woowacourse.movie.repository.DummyTheaterList

class TheaterBottomDialogFragment : BottomSheetDialogFragment(), TheaterClickListener, TheaterBottomDialogContract.View {
    private val movieId: Long by lazy { arguments?.getLong(KEY_MOVIE_ID) ?: INVALID_VALUE_MOVIE_ID }
    private val presenter = TheaterBottomDialogPresenter(this, DummyTheaterList)
    private lateinit var binding: FragmentTheaterBottomSheetDialogBinding
    private val theaterAdapter: TheaterAdapter by lazy { TheaterAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTheaterBottomSheetDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.theaterListRv.adapter = theaterAdapter
        presenter.loadTheaters(movieId)
    }

    override fun showTheaterList(theaterList: List<Theater>) {
        theaterAdapter.updateMovieIdAndTheaters(movieId, theaterList)
    }

    override fun onTheaterClick(
        theaterId: Long,
        movieId: Long,
    ) {
        startActivity(TicketingActivity.createIntent(requireActivity(), theaterId, movieId))
    }

    companion object {
        const val KEY_MOVIE_ID = "movie_id"
        const val INVALID_VALUE_MOVIE_ID = -1L

        fun newInstance(movieId: Long): TheaterBottomDialogFragment =
            TheaterBottomDialogFragment().apply {
                arguments = Bundle().apply { putLong(KEY_MOVIE_ID, movieId) }
            }
    }
}
