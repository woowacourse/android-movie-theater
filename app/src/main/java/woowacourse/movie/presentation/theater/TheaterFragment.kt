package woowacourse.movie.presentation.theater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.common.util.getSerializableCompat
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
        presenter = TheaterPresenter(this, movie)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTheaterBinding.inflate(inflater, container, false)
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
        binding.adapter = TheaterAdapter(theaters) { presenter.selectTheater(it) }
    }

    override fun navigateToBooking(screening: Screening) {
        val intent = BookingActivity.newIntent(context, screening)
        startActivity(intent)
        dismiss()
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
