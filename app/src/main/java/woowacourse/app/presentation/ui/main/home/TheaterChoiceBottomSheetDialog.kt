package woowacourse.app.presentation.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.app.data.movie.MovieDao
import woowacourse.app.data.theater.MovieTimeDao
import woowacourse.app.data.theater.TheaterDao
import woowacourse.app.data.theater.TheaterRepositoryImpl
import woowacourse.domain.theater.Theater
import woowacourse.domain.theater.TheaterRepository
import woowacourse.movie.databinding.BottomSheetDialogTheaterItemBinding

class TheaterChoiceBottomSheetDialog : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val layout = ConstraintLayout(requireContext())
        val theaterRepo: TheaterRepository = TheaterRepositoryImpl(
            TheaterDao(requireContext()),
            MovieTimeDao(requireContext()),
            MovieDao(requireContext()),
        )
        val theaters: List<Theater> = theaterRepo.getTheaters()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun makeItem(
        theater: Theater,
        layout: ConstraintLayout,
    ): BottomSheetDialogTheaterItemBinding {
        val binding = BottomSheetDialogTheaterItemBinding.inflate(layoutInflater, layout, false)
        binding.textItemTheaterName.text = theater.name + " 극장"
        return binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
