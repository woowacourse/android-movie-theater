package woowacourse.movie.home.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterSelectionBinding
import woowacourse.movie.detail.view.MovieDetailActivity
import woowacourse.movie.home.presenter.TheaterSelectionPresenter
import woowacourse.movie.home.presenter.contract.TheaterSelectionContract
import woowacourse.movie.home.view.adapter.theater.TheaterAdapter
import woowacourse.movie.model.Theater
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_MOVIE_ID
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_ID
import woowacourse.movie.util.MovieIntentConstant.KEY_SELECTED_THEATER_POSITION

class TheaterSelectionFragment : BottomSheetDialogFragment(), TheaterSelectionContract.View {
    private lateinit var binding: FragmentTheaterSelectionBinding
    private lateinit var theaterSelectionPresenter: TheaterSelectionPresenter

    private val movieId: Long by lazy { arguments?.getLong(KEY_MOVIE_ID) ?: INVALID_VALUE_MOVIE_ID }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_theater_selection, container, false)

        theaterSelectionPresenter = TheaterSelectionPresenter(this)
        theaterSelectionPresenter.loadTheaters(movieId)
        return binding.root
    }

    override fun setUpTheaterAdapter(theaters: List<Theater>) {
        binding.theaterRecyclerview.adapter =
            TheaterAdapter(theaters) { position ->
                Intent(requireActivity(), MovieDetailActivity::class.java).apply {
                    putExtra(KEY_MOVIE_ID, movieId)
                    putExtra(KEY_SELECTED_THEATER_POSITION, position)
                    startActivity(this)
                }
            }
    }
}
