package woowacourse.movie.presentation.ui.main.home.bottom

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.databinding.FragmentBottomTheatersBinding
import woowacourse.movie.domain.model.TheaterCount
import woowacourse.movie.domain.repository.DummyScreens
import woowacourse.movie.presentation.ui.detail.ScreenDetailActivity

class BottomTheatersFragment : BottomSheetDialogFragment(), BottomTheaterContract.View {
    private var _binding: FragmentBottomTheatersBinding? = null
    val binding: FragmentBottomTheatersBinding
        get() = _binding!!

    private var movieId: Int? = null
    private lateinit var adapter: BottomTheatersAdapter
    private lateinit var presenter: BottomTheaterContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(MOVIE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBottomTheatersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        presenter = BottomTheaterPresenter(this, DummyScreens())
        movieId?.let { presenter.fetchTheaterCounts(it) }
    }

    override fun showBottomTheater(
        theaterCounts: List<TheaterCount>,
        movieId: Int,
    ) {
        adapter = BottomTheatersAdapter(this, theaterCounts, movieId)
        binding.rvBottomTheaters.adapter = adapter
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener {
            val bottomSheet =
                dialog.findViewById<View>(
                    com.google.android.material.R.id.design_bottom_sheet,
                ) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        return dialog
    }

    override fun onTheaterClick(
        movieId: Int,
        theaterId: Int,
    ) {
        ScreenDetailActivity.startActivity(requireActivity(), movieId, theaterId)
        this.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val MOVIE_ID = "movie_id"

        @JvmStatic
        fun newInstance(movieId: Int) =
            BottomTheatersFragment().apply {
                arguments =
                    Bundle().apply {
                        putInt(MOVIE_ID, movieId)
                    }
            }
    }
}
