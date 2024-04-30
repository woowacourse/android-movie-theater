package woowacourse.movie.presentation.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.model.Theater
import woowacourse.movie.presentation.movieList.adapter.TheaterAdapter

class TheaterBottomSheetDialogFragment(
    private val theaters: List<Theater>,
    private val movieId: Long,
    private val listener: (Long, Long) -> Unit,
) : BottomSheetDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val adapter =
            TheaterAdapter(theaters, movieId) { theaterId, movieId ->
                listener(theaterId, movieId)
            }
        view.findViewById<RecyclerView>(R.id.theater_list_rv).adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_theater_bottom_sheet_dialog, container, false)
    }
}
