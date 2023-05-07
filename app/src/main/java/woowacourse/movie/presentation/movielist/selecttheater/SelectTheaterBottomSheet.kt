package woowacourse.movie.presentation.movielist.selecttheater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.databinding.FragmentSelectTheaterBottomSheetBinding

class SelectTheaterBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentSelectTheaterBottomSheetBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectTheaterBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val MOVIE_ID = "movieId"

        fun getBundle(movieId: Long) = Bundle().apply { putLong(MOVIE_ID, movieId) }
    }
}
