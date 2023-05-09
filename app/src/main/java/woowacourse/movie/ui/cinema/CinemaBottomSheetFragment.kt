package woowacourse.movie.ui.cinema

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.databinding.FragmentCinemaBottomSheetBinding
import woowacourse.movie.ui.cinema.presenter.CinemaContract
import woowacourse.movie.ui.cinema.presenter.CinemaPresenter

class CinemaBottomSheetFragment : BottomSheetDialogFragment(), CinemaContract.View {
    override val presenter: CinemaContract.Presenter by lazy { CinemaPresenter(this) }
    private var _binding: FragmentCinemaBottomSheetBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCinemaBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }
}
