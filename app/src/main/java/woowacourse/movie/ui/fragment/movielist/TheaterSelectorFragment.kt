package woowacourse.movie.ui.fragment.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.ui.activity.detail.view.MovieDetailActivity
import woowacourse.movie.ui.fragment.movielist.adapter.TheaterAdapter
import woowacourse.movie.ui.model.MovieModel
import woowacourse.movie.ui.model.TheaterModel
import woowacourse.movie.ui.utils.getParcelableByKey

class TheaterSelectorFragment : BottomSheetDialogFragment() {
    private lateinit var movie: MovieModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getParcelableByKey(MOVIE_EXTRA_KEY)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_theater_selector, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val theatersView = view.findViewById<RecyclerView>(R.id.rv_theater)
        theatersView.adapter = TheaterAdapter(
            movie.theaters,
            onTheaterItemClick = { moveToDetailActivity(it) }
        )
    }

    private fun moveToDetailActivity(theater: TheaterModel) {
        val intent = MovieDetailActivity.createIntent(requireActivity(), movie, theater)
        startActivity(intent)
        dismiss()
    }

    companion object {
        const val TAG = "TheaterSelectorFragment"
        const val MOVIE_EXTRA_KEY = "movie"
    }
}
