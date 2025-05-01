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
import woowacourse.movie.view.dialog.DialogFactory
import woowacourse.movie.view.home.movies.OnBottomSheetDialogEventListener
import woowacourse.movie.view.home.movies.adapter.TheaterAdapter

class TheaterBottomSheetDialogFragment(
    val eventListener: OnBottomSheetDialogEventListener,
) : BottomSheetDialogFragment(), TheaterContract.View {
    private val presenter: TheaterContract.Presenter by lazy {
        TheaterPresenter(this)
    }

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

        val movie = arguments?.getSerializable("movie") as? Movie?

        if (movie == null) {
            handleInvalidTicket()
        } else {
            presenter.fetchData(movie)
        }
    }

    override fun handleInvalidTicket() {
        DialogFactory().showError(requireContext()) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun showTheaterList(showings: List<Showings>) {
        val recyclerView: RecyclerView? = view?.findViewById(R.id.rv_theater_category)

        val theaterAdapter: TheaterAdapter =
            TheaterAdapter(
                object : OnTheaterEventListener {
                    override fun onClickReservation(showings: Showings) {
                        eventListener.onClick(showings)
                        dismiss()
                    }
                },
            )

        recyclerView?.adapter = theaterAdapter
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
