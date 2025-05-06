package woowacourse.movie.presentation.theater

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.data.ScreeningInfoData
import woowacourse.movie.databinding.FragmentTheaterSelectBinding
import woowacourse.movie.domain.model.ScreeningInfo
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.presentation.booking.BookingActivity
import woowacourse.movie.ui.adapter.TheaterAdapter
import woowacourse.movie.ui.util.getSerializableCompat

class TheaterFragment :
    BottomSheetDialogFragment(),
    TheaterContract.View {
    private var movie: Movie? = null
    private var _binding: FragmentTheaterSelectBinding? = null
    private val binding get() = _binding!!
    private val presenter: TheaterPresenter by lazy { TheaterPresenter(this, ScreeningInfoData) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getSerializableCompat(THEATER_KEY, Movie::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_theater_select, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        movie?.let { presenter.initializeTheater(it) }
    }

    override fun showTheaters(theaters: List<ScreeningInfo>) {
        binding.recyclerviewTheaters.adapter =
            TheaterAdapter(theaters) {
                presenter.selectTheater(it)
            }
    }

    override fun navigateToBooking(screeningInfo: ScreeningInfo) {
        val intent = BookingActivity.newIntent(binding.root.context, screeningInfo)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val THEATER_KEY = "Theater"

        @JvmStatic
        fun newInstance(movie: Movie) =
            TheaterFragment().apply {
                arguments =
                    Bundle().apply {
                        putSerializable(THEATER_KEY, movie)
                    }
            }
    }
}
