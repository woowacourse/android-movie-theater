package woowacourse.movie.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Theaters
import woowacourse.movie.domain.repository.DummyScreens
import woowacourse.movie.domain.repository.DummyTheaters
import woowacourse.movie.ui.detail.ScreenDetailActivity
import woowacourse.movie.ui.home.adapter.TheaterAdapter

class TheaterBottomSheet : BottomSheetDialogFragment(), TheatersBottomSheetContract.View {
    private val presenter: TheatersBottomSheetContract.Presenter by lazy {
        TheaterBottomSheetPresenter(this, DummyScreens(), DummyTheaters())
    }

    private lateinit var theaterAdapter: TheaterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val screenId = arguments?.getInt(ARGUMENT_SCREEN_ID) ?: throw IllegalArgumentException("ScreenId is required")
        presenter.saveScreenId(screenId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_theater, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadTheaters()

        view.findViewById<RecyclerView>(R.id.rv_theater).adapter = theaterAdapter
    }

    override fun showTheaters(
        screen: Screen,
        theaters: Theaters,
    ) {
        theaterAdapter =
            TheaterAdapter(screen) { theaterId ->
                presenter.onTheaterSelected(theaterId)
            }
        theaterAdapter.submitList(theaters.screeningTheater(screen).theaters)
    }

    override fun navigateToScreenDetail(
        screenId: Int,
        theaterId: Int,
    ) {
        ScreenDetailActivity.startActivity(requireContext(), screenId, theaterId)
    }

    companion object {
        private const val ARGUMENT_SCREEN_ID = "screenId"

        fun newInstance(screenId: Int): TheaterBottomSheet {
            val fragment = TheaterBottomSheet()
            val args = Bundle()
            args.putInt(ARGUMENT_SCREEN_ID, screenId)
            fragment.arguments = args

            return fragment
        }
    }
}
