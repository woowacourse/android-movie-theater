package woowacourse.movie.ui.view.cinema

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.domain.reservation.ScreeningContent
import woowacourse.movie.ui.contract.cinema.ScreeningContract
import woowacourse.movie.ui.presenter.cinema.ScreeningPresenter
import woowacourse.movie.ui.view.cinema.adapter.ScreeningAdapter

class HomeFragment :
    Fragment(),
    ScreeningContract.View {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() =
        _binding ?: error(
            woowacourse.movie.ui.view.util.ErrorMessage("_binding").notProvided(),
        )

    private val presenter: ScreeningContract.Presenter = ScreeningPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.presentScreeningContents()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun setScreeningContents(screeningContents: List<ScreeningContent>) {
        initListView(screeningContents)
    }

    private fun initListView(screeningContents: List<ScreeningContent>) {
        val screenings: List<ScreeningContent> = arguments?.screenings ?: screeningContents
        val moviesView = binding.recyclerViewHomeScreeningMovies
        val movieAdapter = ScreeningAdapter(screenings, presenter::selectScreening)
        moviesView.adapter = movieAdapter
    }

    @Suppress("DEPRECATION")
    private val Bundle.screenings: List<ScreeningContent>?
        get() =
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ->
                    getSerializable(
                        ARGUMENT_SCREENING_CONTENTS,
                        ArrayList::class.java,
                    ) as? List<ScreeningContent>

                else -> getSerializable(ARGUMENT_SCREENING_CONTENTS) as? List<ScreeningContent>
            }

    override fun showCinemas(screening: Screening) {
        val dialog = CinemaSelectionBottomSheetDialogFragment.newInstance(screening)
        dialog.show(parentFragmentManager, dialog.tag)
    }

    companion object {
        private const val ARGUMENT_SCREENING_CONTENTS =
            "woowacourse.movie.ARGUMENT_SCREENING_CONTENTS"

        fun arguments(screeningContents: List<ScreeningContent>): Bundle =
            Bundle().apply {
                putSerializable(ARGUMENT_SCREENING_CONTENTS, ArrayList(screeningContents))
            }
    }
}
