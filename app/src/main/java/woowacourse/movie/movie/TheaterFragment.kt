package woowacourse.movie.movie

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.BookingDetailActivity
import woowacourse.movie.BookingDetailActivity.Companion.KEY_MOVIE_DATA
import woowacourse.movie.BookingDetailActivity.Companion.KEY_THEATER_DATA
import woowacourse.movie.R
import woowacourse.movie.movie.adapter.TheaterAdapter

class TheaterFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_theater, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val theaters = initTheaters()

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_theater)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter =
            TheaterAdapter(theaters, initMovie()) { theater ->
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    val intent =
                        Intent(activity, BookingDetailActivity::class.java).apply {
                            putExtra(KEY_THEATER_DATA, theater)
                            putExtra(KEY_MOVIE_DATA, initMovie())
                        }
                    startActivity(intent)
                    addToBackStack(null)
                    dismiss()
                }
            }
    }

    private fun initTheaters(): ArrayList<TheaterUiModel> {
        val theaters: ArrayList<TheaterUiModel>? = arguments?.getParcelableArrayList(KEY_THEATERS)
        if (theaters == null) dismiss()

        return theaters!!
    }

    private fun initMovie(): MovieUiModel {
        val movie: MovieUiModel? = arguments?.getParcelable(KEY_MOVIE)
        if (movie == null) dismiss()

        return movie!!
    }

    companion object {
        const val KEY_THEATERS = "theatersData"
        const val KEY_MOVIE = "theatersMovieData"
    }
}
