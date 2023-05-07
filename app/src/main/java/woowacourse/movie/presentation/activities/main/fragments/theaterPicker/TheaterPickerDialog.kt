package woowacourse.movie.presentation.activities.main.fragments.theaterPicker

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.main.fragments.home.HomeFragment.Companion.MOVIE_KEY
import woowacourse.movie.presentation.activities.ticketing.TicketingActivity
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.model.item.Movie
import woowacourse.movie.presentation.model.item.Theater

class TheaterPickerDialog : BottomSheetDialogFragment(R.layout.fragment_bottom_sheet), TheaterPickerDialogContract.View {
    override lateinit var presenter: TheaterPickerDialogPresenter
    private val movie: Movie by lazy { requireArguments().getParcelableCompat(MOVIE_KEY)!! }
    private lateinit var theaterRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = TheaterPickerDialogPresenter(this)
        theaterRecyclerView = view.findViewById(R.id.theater_recycler_view)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val theaterAdapter = TheaterListAdapter { presenter.moveTicketingActivity(movie) }
        theaterAdapter.appendAll(Theater.provideDummyData())
        theaterRecyclerView.adapter = theaterAdapter
    }

    override fun startTicketingActivity(movie: Movie) {
        val intent = Intent(requireContext(), TicketingActivity::class.java)
            .putExtra(MOVIE_KEY, movie)
        startActivity(intent)
    }

    companion object {
        const val TAG = "TheaterPickerDialog"

        fun getInstance(movie: Movie): TheaterPickerDialog = TheaterPickerDialog().apply {
            arguments = bundleOf(MOVIE_KEY to movie)
        }
    }
}
