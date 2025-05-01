package woowacourse.movie.theater

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.booking.detail.BookingDetailActivity
import woowacourse.movie.databinding.FragmentTheaterBinding
import woowacourse.movie.ui.model.MovieUiModel
import woowacourse.movie.ui.model.TheaterUiModel

class TheaterFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentTheaterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_theater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val theaters = initTheaters()

        val recyclerView: RecyclerView = binding.rvTheater
        recyclerView.adapter =
            TheaterAdapter(theaters) { theater ->
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    val intent =
                        Intent(activity, BookingDetailActivity::class.java).apply {
                            putExtra(BookingDetailActivity.Companion.KEY_THEATER_DATA, theater)
                            putExtra(BookingDetailActivity.Companion.KEY_MOVIE_DATA, initMovie())
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
