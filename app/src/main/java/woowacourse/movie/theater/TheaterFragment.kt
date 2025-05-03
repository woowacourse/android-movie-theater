package woowacourse.movie.theater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_theater, container, false)
        return binding.root
    }

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
                    val intent = BookingDetailActivity.newIntent(requireActivity(), initMovie(), theater)
                    startActivity(intent)
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
        const val KEY_THEATERS = "THEATERS_DATA"
        const val KEY_MOVIE = "MOVIE_DATA"
    }
}
