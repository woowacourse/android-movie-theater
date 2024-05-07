package woowacourse.movie.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.databinding.FragmentTheaterSelectionBinding
import woowacourse.movie.detail.view.MovieDetailActivity
import woowacourse.movie.home.presenter.TheaterSelectionPresenter
import woowacourse.movie.home.presenter.contract.TheaterSelectionContract
import woowacourse.movie.home.view.adapter.theater.TheaterAdapter
import woowacourse.movie.home.view.adapter.theater.listener.TheaterClickListener
import woowacourse.movie.model.Theater
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_MOVIE_ID
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_ID

class TheaterSelectionFragment :
    BottomSheetDialogFragment(),
    TheaterSelectionContract.View,
    TheaterClickListener {
    private lateinit var binding: FragmentTheaterSelectionBinding
    private lateinit var theaterSelectionPresenter: TheaterSelectionPresenter

    private val movieId: Long by lazy { arguments?.getLong(KEY_MOVIE_ID) ?: INVALID_VALUE_MOVIE_ID }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentTheaterSelectionBinding.inflate(inflater, container, false)

        theaterSelectionPresenter = TheaterSelectionPresenter(this)
        theaterSelectionPresenter.loadTheaters(movieId)
        return binding.root
    }

    override fun setUpTheaterAdapter(theaters: List<Theater>) {
        binding.recyclerViewTheater.adapter =
            TheaterAdapter(theaters, this)
    }

    override fun onTheaterClick(position: Int) {
        val intent = MovieDetailActivity.createIntent(requireActivity(), movieId, position)
        startActivity(intent)
        dialog?.dismiss()
    }

    companion object {
        fun newInstance(movieId: Long): TheaterSelectionFragment {
            return TheaterSelectionFragment().apply {
                arguments =
                    Bundle().apply {
                        putLong(KEY_MOVIE_ID, movieId)
                    }
            }
        }
    }
}
