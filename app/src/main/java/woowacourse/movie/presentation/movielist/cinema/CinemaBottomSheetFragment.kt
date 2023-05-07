package woowacourse.movie.presentation.movielist.cinema

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.data.cinema.MockCinemaData
import woowacourse.movie.databinding.FragmentCinemaBottomSheetBinding
import woowacourse.movie.presentation.booking.BookingActivity
import woowacourse.movie.presentation.model.CinemaModel
import woowacourse.movie.presentation.model.MovieModel

class CinemaBottomSheetFragment(
    private val movieModel: MovieModel,
) : BottomSheetDialogFragment(), CinemaContract.View {

    override val presenter: CinemaContract.Presenter by lazy {
        CinemaPresenter(this, MockCinemaData)
    }

    private var _binding: FragmentCinemaBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCinemaBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setCinemaModels(movieModel)
    }

    override fun setCinemaItemAdapter(cinemaModels: List<CinemaModel>) {
        binding.recyclerCinemaItem.adapter = CinemaItemAdapter(cinemaModels) { clickArrow(it) }
    }

    private fun clickArrow(cinemaModel: CinemaModel) {
        startActivity(BookingActivity.getIntent(requireActivity(), cinemaModel))
    }

    companion object {
        const val TAG = "CinemaBottomSheet"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
