package woowacourse.movie.presentation.ui.main.home.bottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentBottomTheatersBinding
import woowacourse.movie.domain.model.TheaterCount
import woowacourse.movie.presentation.ui.main.home.adapter.BottomTheatersAdapter

class BottomTheatersFragment(
    private val theaterCounts: List<TheaterCount> = mutableListOf(),
    private val actionHandler: BottomTheaterActionHandler,
    private val movieId: Int,
) : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomTheatersBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var adapter: BottomTheatersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_theaters, container, false)

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        adapter = BottomTheatersAdapter(actionHandler, theaterCounts, movieId)
        binding.rvBottomTheaters.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
