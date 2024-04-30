package woowacourse.movie.view.theater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterSelectionBinding
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.presenter.theater.TheaterSelectionContract
import woowacourse.movie.presenter.theater.TheaterSelectionPresenter
import woowacourse.movie.view.home.HomeFragment.Companion.MOVIE_ID
import woowacourse.movie.view.theater.adapter.TheaterSelectionAdapter

class TheaterSelectionFragment : Fragment(), TheaterSelectionContract.View {
    private val presenter = TheaterSelectionPresenter(this)
    private lateinit var binding: FragmentTheaterSelectionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_theater_selection, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initTheaterRecyclerView()
    }

    override fun navigateToDetail(
        movieId: Int,
        theaterId: Int,
    ) {
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID, movieId)
        bundle.putInt(THEATER_ID, theaterId)
        setFragmentResult(BUNDLE_THEATER_ID, bundle)
        // TODO 예약화면으로 이동
//        parentFragmentManager.navigateToFragment(
//            R.layout.fragment_theater_selection,
//
//        )
    }

    private fun initTheaterRecyclerView() {
        val theaterSelectionAdapter =
            TheaterSelectionAdapter(
                TheaterDao().findAll(),
            ) { theaterId ->
                // TODO Movie Id 넘기자
                presenter.loadTheater(0, theaterId)
            }
        binding.recyclerViewTheaterSelection.apply {
            adapter = theaterSelectionAdapter
        }
    }

    companion object {
        const val THEATER_ID = "theaterId"
        const val BUNDLE_THEATER_ID = "bundleTheaterId"
    }
}
