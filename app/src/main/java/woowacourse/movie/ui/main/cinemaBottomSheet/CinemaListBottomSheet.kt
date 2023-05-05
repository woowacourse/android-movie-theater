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
import woowacourse.movie.util.getParcelableCompat

class CinemaListBottomSheet : BottomSheetDialogFragment(R.layout.fragment_cinema_bottom_sheet), CinemaListContract.View {
    private lateinit var presenter: CinemaListContract.Presenter
    private lateinit var binding: FragmentCinemaBottomSheetBinding

    private lateinit var movie: MovieState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = arguments?.getParcelableCompat(KEY_MOVIE) ?: throw IllegalArgumentException()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding()
        initPresenter()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getCinemaList(movie)
    }

    private fun initBinding() {
        binding = FragmentCinemaBottomSheetBinding.inflate(layoutInflater)
    }

    private fun initPresenter() {
        presenter = CinemaListPresenter(this)
    }

    override fun setAdapter(cinemas: List<CinemaState>) {
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
