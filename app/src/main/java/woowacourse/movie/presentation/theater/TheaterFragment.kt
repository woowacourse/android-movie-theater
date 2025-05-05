package woowacourse.movie.presentation.theater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.common.util.getSerializableCompat
import woowacourse.movie.data.ScreeningData
import woowacourse.movie.databinding.FragmentTheaterBinding
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.presentation.booking.BookingActivity
import woowacourse.movie.presentation.theater.adapter.TheaterAdapter

class TheaterFragment :
    BottomSheetDialogFragment(),
    TheaterContract.View {
    private var _binding: FragmentTheaterBinding? = null
    private val binding: FragmentTheaterBinding get() = _binding!!
    private lateinit var presenter: TheaterContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movie =
            arguments?.getSerializableCompat(EXTRA_MOVIE, Movie::class.java)
                ?: dismiss().run { return }
        presenter = TheaterPresenter(this, movie, ScreeningData)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_theater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadTheaterList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showTheaters(theaters: List<Screening>) {
        binding.adapter = TheaterAdapter(theaters) { presenter.startBooking(it) }
    }

    override fun navigateToBooking(screening: Screening) {
        val intent = BookingActivity.newIntent(context, screening)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance(movie: Movie) =
            TheaterFragment().apply {
                arguments =
                    Bundle().apply {
                        putSerializable(EXTRA_MOVIE, movie)
                    }
            }

        private const val EXTRA_MOVIE = "movie"
    }
}
