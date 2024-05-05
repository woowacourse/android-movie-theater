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

class BottomTheatersFragment(
    private val theaterCounts: List<TheaterCount> = mutableListOf(),
    private val actionHandler: BottomTheaterActionHandler,
    private val movieId: Int,
) : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomTheatersBinding? = null
    val binding: FragmentBottomTheatersBinding
        get() = _binding!!

    private lateinit var adapter: BottomTheatersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
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
        adapter = BottomTheatersAdapter(actionHandler, theaterCounts, movieId)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
