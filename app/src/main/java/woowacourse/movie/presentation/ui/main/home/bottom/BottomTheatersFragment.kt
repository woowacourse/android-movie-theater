package woowacourse.movie.presentation.ui.main.home.bottom

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.domain.model.TheaterCount

class BottomTheatersFragment(
    private val theaterCounts: List<TheaterCount> = mutableListOf(),
    private val actionHandler: BottomTheaterActionHandler,
    private val movieId: Int,
) : BottomSheetDialogFragment() {
    private var _binding: ViewBinding? = null
    val binding: ViewBinding
        get() = _binding!!

    private lateinit var rvTheater: RecyclerView
    private lateinit var adapter: BottomTheatersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        adapter = BottomTheatersAdapter(actionHandler, theaterCounts, movieId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_theaters, container, false)
        rvTheater = view.findViewById(R.id.rv_bottom_theaters)
        rvTheater.adapter = adapter

        return view
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

    override fun onDestroy() {
        Log.d("테스트", "지금이니?")
        super.onDestroy()
    }
}
