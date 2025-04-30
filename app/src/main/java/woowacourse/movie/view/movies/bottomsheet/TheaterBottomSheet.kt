package woowacourse.movie.view.movies.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.domain.model.theater.Theaters
import woowacourse.movie.view.movies.adapter.TheaterAdapter

class TheaterBottomSheet(
    private val theaters: Theaters,
    private val movieId: Int,
    private val onclick: (String) -> Unit,
) : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_theater_bottom_sheet, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val theaterAdapter = TheaterAdapter(theaters, movieId, onclick)
        val adapter = view.findViewById<RecyclerView>(R.id.rv)
        adapter.adapter = theaterAdapter
        adapter.layoutManager = LinearLayoutManager(view.context)
    }
}
