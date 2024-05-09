package woowacourse.movie.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.databinding.BottomSheetTheaterBinding
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Theaters
import woowacourse.movie.domain.repository.DummyScreens
import woowacourse.movie.domain.repository.DummyTheaters
import woowacourse.movie.ui.detail.ScreenDetailActivity
import woowacourse.movie.ui.home.adapter.TheaterAdapter

class TheaterBottomSheet : BottomSheetDialogFragment(), TheaterContract.View {
    private var _binding: BottomSheetTheaterBinding? = null
    private val binding: BottomSheetTheaterBinding
        get() = requireNotNull(_binding)

    private lateinit var theaterAdapter: TheaterAdapter

    private val presenter: TheaterContract.Presenter by lazy {
        TheaterPresenter(this, DummyScreens(), DummyTheaters())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screenId = arguments?.getInt(SCREEN_ID) ?: throw IllegalArgumentException("ScreenId is required")
        presenter.saveScreenId(screenId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = BottomSheetTheaterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.initTheaterAdapter()
        presenter.loadTheaters()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun initTheaterAdapter(screen: Screen) {
        theaterAdapter =
            TheaterAdapter(screen) { theaterId ->
                presenter.onTheaterSelected(theaterId)
            }
        binding.rvTheater.adapter = theaterAdapter
    }

    override fun showTheaters(
        screen: Screen,
        theaters: Theaters,
    ) {
        theaterAdapter.submitList(theaters.theaters)
    }

    override fun navigateToScreenDetail(
        screenId: Int,
        theaterId: Int,
    ) {
        ScreenDetailActivity.startActivity(requireContext(), screenId, theaterId)
    }

    companion object {
        private const val SCREEN_ID = "screenId"
        private const val THEATER_BOTTOM_SHEET = "theaterBottomSheet"

        fun startFragment(
            parentFragmentManager: FragmentManager,
            screenId: Int,
        ) {
            val theaterBottomSheet = TheaterBottomSheet()
            val bundle = Bundle()

            bundle.putSerializable(SCREEN_ID, screenId)
            theaterBottomSheet.arguments = bundle

            theaterBottomSheet.show(parentFragmentManager, THEATER_BOTTOM_SHEET)
        }
    }
}
