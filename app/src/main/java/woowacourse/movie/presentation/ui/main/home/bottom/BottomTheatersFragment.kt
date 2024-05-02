package woowacourse.movie.presentation.ui.main.home.bottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.domain.model.TheaterCount

class BottomTheatersFragment(
    private val theaterCounts: List<TheaterCount> = mutableListOf(),
    private val actionHandler: BottomTheaterActionHandler,
    private val movieId: Int,
) : BottomSheetDialogFragment() {
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
}
