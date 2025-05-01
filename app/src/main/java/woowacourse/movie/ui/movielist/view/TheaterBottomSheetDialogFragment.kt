package woowacourse.movie.ui.movielist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterBottomSheetDialogBinding
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.Theaters
import woowacourse.movie.sample.DUMMY_MOVIES
import woowacourse.movie.ui.booking.view.BookingActivity
import woowacourse.movie.ui.movielist.contract.TheaterBottomSheetDialogContract
import woowacourse.movie.ui.movielist.presenter.TheaterBottomSheetDialogPresenter
import woowacourse.movie.utils.bundleSerializable

class TheaterBottomSheetDialogFragment :
    BottomSheetDialogFragment(),
    TheaterBottomSheetDialogContract.View {
    private lateinit var binding: FragmentTheaterBottomSheetDialogBinding
    private val presenter = TheaterBottomSheetDialogPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
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

        val movie = restoreMovie()
        presenter.loadAvailableTheaters(movie)
    }

    override fun showTheaters(theaters: Theaters) {
        val adapter =
            TheaterAdapter { theater ->
                startBookingActivity(binding.root, theater)
            }
        binding.theatersRecyclerView.adapter = adapter
        adapter.submitList(theaters.theaters)
    }

    private fun restoreMovie(): Movie {
        val movie =
            arguments?.bundleSerializable("EXTRA_MOVIE", Movie::class.java) ?: DUMMY_MOVIES.first()
        return movie
    }

    private fun startBookingActivity(
        view: View,
        theater: Theater,
    ) {
        if (theater.movieSchedules.isNotEmpty()) {
            startActivity(BookingActivity.newIntent(view.context, theater))
            parentFragmentManager.commit {
                remove(this@TheaterBottomSheetDialogFragment)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(movie: Movie) =
            TheaterBottomSheetDialogFragment().apply {
                arguments =
                    Bundle().apply {
                        putSerializable("EXTRA_MOVIE", movie)
                    }
            }
    }
}
