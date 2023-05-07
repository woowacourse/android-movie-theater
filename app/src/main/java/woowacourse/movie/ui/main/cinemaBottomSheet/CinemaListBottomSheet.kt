package woowacourse.movie.ui.main.cinemaBottomSheet

import android.os.Bundle
import android.view.View
import com.example.domain.repository.CinemaRepository
import woowacourse.movie.databinding.FragmentCinemaBottomSheetBinding
import woowacourse.movie.model.CinemaState
import woowacourse.movie.model.MovieState
import woowacourse.movie.ui.BaseBottomSheetDialogFragment
import woowacourse.movie.ui.adapter.CinemaListAdapter
import woowacourse.movie.ui.itemModel.CinemaItemModel
import woowacourse.movie.ui.reservation.MovieDetailActivity
import woowacourse.movie.util.getParcelableCompat

class CinemaListBottomSheet : BaseBottomSheetDialogFragment(), CinemaListContract.View {
    override lateinit var presenter: CinemaListContract.Presenter
    override lateinit var binding: FragmentCinemaBottomSheetBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setUpCinemaList()
    }

    override fun initBinding() {
        binding = FragmentCinemaBottomSheetBinding.inflate(layoutInflater)
    }

    override fun initPresenter() {
        presenter = CinemaListPresenter(
            this,
            CinemaRepository,
            arguments?.getParcelableCompat(KEY_MOVIE) ?: throw IllegalArgumentException()
        )
    }

    override fun setCinemaList(cinemas: List<CinemaState>, movie: MovieState) {
        val adapter = CinemaListAdapter(
            cinemas = cinemas.map { CinemaItemModel(it, movie) }
        ) { itemModel ->
            navigateMovieDetail(itemModel)
        }
        binding.bottomSheetMovieList.adapter = adapter
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
        const val KEY_MOVIE = "key_movie"

        fun newInstance(movie: MovieState): CinemaListBottomSheet {
            return CinemaListBottomSheet().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_MOVIE, movie)
                }
            }
        }
    }
}
