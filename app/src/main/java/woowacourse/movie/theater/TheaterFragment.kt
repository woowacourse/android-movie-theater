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
//    private lateinit var binding: FragmentTheaterBinding
    private var _binding: FragmentTheaterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_theater, container, false)
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
        return theaters ?: run {
            dismiss()
            throw IllegalArgumentException(ERROR_NOT_FOUND_DATA.format(KEY_THEATERS))
        }
    }

    private fun initMovie(): MovieUiModel {
        return arguments?.getParcelable(KEY_MOVIE) ?: run {
            dismiss()
            throw IllegalArgumentException(ERROR_NOT_FOUND_DATA.format(KEY_MOVIE))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ERROR_NOT_FOUND_DATA = "%s 데이터를 찾을 수 없습니다"
        const val KEY_THEATERS = "THEATERS_DATA"
        const val KEY_MOVIE = "MOVIE_DATA"
    }
}
