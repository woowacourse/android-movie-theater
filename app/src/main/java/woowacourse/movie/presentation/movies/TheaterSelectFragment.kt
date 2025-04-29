package woowacourse.movie.presentation.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.data.TheaterData
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.ui.adapter.TheaterAdapter
import woowacourse.movie.ui.constant.IntentKeys
import woowacourse.movie.ui.util.bundleSerializable

class TheaterSelectFragment : BottomSheetDialogFragment() {
    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.bundleSerializable(IntentKeys.MOVIE, Movie::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.fragment_theater_select, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val theaterRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_theaters)
        theaterRecyclerView.adapter = TheaterAdapter(TheaterData.theaters)
    }

    companion object {
        @JvmStatic
        fun newInstance(movie: Movie) =
            TheaterSelectFragment().apply {
                arguments =
                    Bundle().apply {
                        putSerializable(IntentKeys.MOVIE, movie)
                    }
            }
    }
}
