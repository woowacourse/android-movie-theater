package woowacourse.movie.view.moviemain.movielist

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R

class MovieTheaterDialog(
    private val adapter: MovieTheaterAdapter
) : BottomSheetDialogFragment(R.layout.bottom_sheet_theater) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.theater_recyclerview).adapter = adapter
    }
}
