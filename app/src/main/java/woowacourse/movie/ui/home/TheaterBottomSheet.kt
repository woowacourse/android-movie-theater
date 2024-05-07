package woowacourse.movie.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Theaters
import woowacourse.movie.ui.detail.ScreenDetailActivity
import woowacourse.movie.ui.home.adapter.TheaterAdapter

class TheaterBottomSheet : BottomSheetDialogFragment() {
    private lateinit var theaterAdapter: TheaterAdapter

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val screen =
            arguments?.getSerializable(SCREEN, Screen::class.java)
                ?: throw IllegalArgumentException()
        val theaters =
            arguments?.getSerializable(THEATERS, Theaters::class.java)
                ?: throw IllegalArgumentException()

        initTheaterAdapter(screen, theaters)

        return inflater.inflate(R.layout.bottom_sheet_theater, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.rv_theater).adapter = theaterAdapter
    }

    private fun initTheaterAdapter(
        screen: Screen,
        theaters: Theaters,
    ) {
        theaterAdapter =
            TheaterAdapter(screen) { screenId, theaterId ->
                ScreenDetailActivity.startActivity(requireContext(), screenId, theaterId)
            }
        theaterAdapter.submitList(theaters.theaters)
    }

    companion object {
        private const val SCREEN = "screen"
        private const val THEATERS = "theaters"
        private const val THEATER_BOTTOM_SHEET = "theaterBottomSheet"

        fun startFragment(
            parentFragmentManager: FragmentManager,
            screen: Screen,
            theaters: Theaters,
        ) {
            val theaterBottomSheet = TheaterBottomSheet()
            val bundle = Bundle()

            bundle.putSerializable(SCREEN, screen)
            bundle.putSerializable(THEATERS, theaters)
            theaterBottomSheet.arguments = bundle

            theaterBottomSheet.show(parentFragmentManager, THEATER_BOTTOM_SHEET)
        }
    }
}
