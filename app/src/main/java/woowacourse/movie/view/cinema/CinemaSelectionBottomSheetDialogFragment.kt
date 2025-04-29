package woowacourse.movie.view.cinema

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.contract.cinema.CinemaSelectionContract
import woowacourse.movie.domain.cinema.Cinema
import woowacourse.movie.presenter.cinema.CinemaSelectionPresenter
import woowacourse.movie.view.cinema.adapter.CinemaAdapter

class CinemaSelectionBottomSheetDialogFragment :
    BottomSheetDialogFragment(),
    CinemaSelectionContract.View {
    private lateinit var cinemasView: RecyclerView
    private val cinemaAdapter = CinemaAdapter({})
    private val presenter = CinemaSelectionPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(
            R.layout.fragment_cinema_selection_bottom_sheet_dialog,
            container,
            false,
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        cinemasView = view.findViewById(R.id.recycler_view_cinema_selection)
        cinemasView.adapter = cinemaAdapter
        presenter.presentCinemas()
    }

    override fun setCinemas(cinemas: List<Cinema>) {
        cinemaAdapter.submitList(cinemas)
    }
}
