package woowacourse.movie.presentation.theater

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.common.constant.IntentKeys
import woowacourse.movie.common.util.bundleSerializable
import woowacourse.movie.databinding.FragmentTheaterBinding
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.presentation.booking.BookingActivity
import woowacourse.movie.presentation.theater.adapter.TheaterAdapter

class TheaterFragment :
    BottomSheetDialogFragment(),
    TheaterContract.View {
    private var movie: Movie? = null
    private lateinit var binding: FragmentTheaterBinding
    private lateinit var presenter: TheaterContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.bundleSerializable(IntentKeys.MOVIE, Movie::class.java)
        }
        movie?.let { presenter = TheaterPresenter(this, it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_theater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated()
    }

    override fun showTheaters(theaters: List<Screening>) {
        binding.recyclerviewTheaters.adapter =
            TheaterAdapter(theaters) {
                presenter.onTheaterClicked(it)
            }
    }

    override fun navigateToBooking(screening: Screening) {
        val intent =
            Intent(context, BookingActivity::class.java).apply {
                putExtra(IntentKeys.SCREENING_INFO, screening)
            }
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance(movie: Movie) =
            TheaterFragment().apply {
                arguments =
                    Bundle().apply {
                        putSerializable(IntentKeys.MOVIE, movie)
                    }
            }
    }
}
