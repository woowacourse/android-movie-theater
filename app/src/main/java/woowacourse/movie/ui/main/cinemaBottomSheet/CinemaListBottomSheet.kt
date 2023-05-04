package woowacourse.movie.ui.main.cinemaBottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentCinemaBottomSheetBinding
import woowacourse.movie.model.CinemaState
import woowacourse.movie.model.MovieState
import woowacourse.movie.ui.adapter.CinemaListAdapter
import woowacourse.movie.ui.itemModel.CinemaItemModel
import woowacourse.movie.ui.reservation.MovieDetailActivity

class CinemaListBottomSheet(
    private val movie: MovieState
) : BottomSheetDialogFragment(R.layout.fragment_cinema_bottom_sheet), CinemaListContract.View {
    private lateinit var presenter: CinemaListContract.Presenter

    private lateinit var binding: FragmentCinemaBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpBinding()
        setUpPresenter()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getCinemaList(movie)
    }

    override fun setAdapter(cinemas: List<CinemaState>) {
        val adapter = CinemaListAdapter(
            cinemas = cinemas.map { CinemaItemModel(it, movie) }
        ) { itemModel ->
            navigateMovieDetail(itemModel)
        }
        binding.bottomSheetMovieList.adapter = adapter
    }

    private fun setUpBinding() {
        binding = FragmentCinemaBottomSheetBinding.inflate(layoutInflater)
    }

    private fun setUpPresenter() {
        presenter = CinemaListPresenter(this)
    }

    private fun navigateMovieDetail(cinemaItemModel: CinemaItemModel) {
        MovieDetailActivity.startActivity(
            requireContext(),
            cinemaItemModel.cinema.name,
            cinemaItemModel.movie
        )
    }

    companion object {
        const val TAG_CINEMA_LIST_BOTTOM_SHEET = "tag_cinema_list_bottom_sheet"
    }
}
