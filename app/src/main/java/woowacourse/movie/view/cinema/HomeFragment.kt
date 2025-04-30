package woowacourse.movie.view.cinema

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.contract.cinema.ScreeningContract
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.domain.reservation.ScreeningContent
import woowacourse.movie.presenter.cinema.ScreeningPresenter
import woowacourse.movie.view.cinema.adapter.ScreeningAdapter

class HomeFragment : Fragment(), ScreeningContract.View {
    private lateinit var binding: FragmentHomeBinding
    private val presenter: ScreeningContract.Presenter = ScreeningPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.presentScreeningContents()
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

        fun testInstance(screeningContents: List<ScreeningContent>) =
            HomeFragment().apply {
                arguments =
                    Bundle().apply {
                        putSerializable(ARGUMENT_SCREENING_CONTENTS, ArrayList(screeningContents))
                    }
            }
    }
}
