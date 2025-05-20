package woowacourse.movie.ui.view.screening

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.domain.reservation.ScreeningContent
import woowacourse.movie.ui.util.ErrorMessage
import woowacourse.movie.ui.view.cinema.CinemaSelectionBottomSheetDialogFragment
import woowacourse.movie.ui.view.screening.adapter.ScreeningAdapter

class ScreeningFragment :
    Fragment(),
    ScreeningContract.View {
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() =
            _binding ?: error(
                ErrorMessage("_binding").notProvided(),
            )

    private val presenter: ScreeningContract.Presenter = ScreeningPresenter(this)
    private lateinit var screeningAdapter: ScreeningAdapter

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
        initScreeningAdapter()
        presenter.presentScreeningContents()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initScreeningAdapter() {
        screeningAdapter = ScreeningAdapter(presenter::selectScreening)
        binding.screeningAdapter = screeningAdapter
    }

    override fun setScreeningContents(screeningContents: List<ScreeningContent>) {
        val arguments = arguments?.screenings
        if (arguments == null) {
            screeningAdapter.submitList(screeningContents)
        } else {
            screeningAdapter.submitList(arguments)
        }
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
