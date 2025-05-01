package woowacourse.movie.view.home.theater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Showings
import woowacourse.movie.domain.Theater
import woowacourse.movie.view.home.movies.OnBottomSheetDialogEventListener
import woowacourse.movie.view.home.movies.adapter.TheaterAdapter

class TheaterBottomSheetDialogFragment(
    val eventListener: OnBottomSheetDialogEventListener,
) : BottomSheetDialogFragment() {
    private var movie: Movie? = null

    private var showings: List<Showings>? = null

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
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_theater_category)

        movie = arguments?.getSerializable("movie") as? Movie?
        showings = movie?.let { Theater.findTheatersShowingMovie(it.title) }

        if (movie == null) {
            throw IllegalArgumentException()
        } else {
            showTheaterList(recyclerView)
        }
    }

    private fun showTheaterList(recyclerView: RecyclerView) {
        val theaterAdapter: TheaterAdapter =
            TheaterAdapter(
                object : OnTheaterEventListener {
                    override fun onClickReservation(showings: Showings) {
                        eventListener.onClick(showings)
                        dismiss()
                    }
                },
            )

        recyclerView.adapter = theaterAdapter
        theaterAdapter.submitList(showings)
    }

    companion object {
        fun newInstance(
            movie: Movie,
            eventListener: OnBottomSheetDialogEventListener,
        ): TheaterBottomSheetDialogFragment {
            return TheaterBottomSheetDialogFragment(eventListener).apply {
                arguments =
                    Bundle().apply {
                        putSerializable("movie", movie)
                    }
            }
        }
    }
}
