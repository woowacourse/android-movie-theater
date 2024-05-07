package woowacourse.movie.list.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.databinding.FragmentTheaterBinding
import woowacourse.movie.detail.view.MovieInformationDetailActivity.Companion.makeIntentInstance
import woowacourse.movie.list.adapter.TheaterAdapter
import woowacourse.movie.list.contract.TheaterContract
import woowacourse.movie.list.model.TheaterData.theaters
import woowacourse.movie.list.presenter.TheaterPresenter

class TheaterBottomSheetFragment : BottomSheetDialogFragment(), TheaterContract.View {
    private lateinit var binding: FragmentTheaterBinding
    private lateinit var presenter: TheaterPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTheaterBinding.inflate(inflater, container, false)
        presenter = TheaterPresenter(this)

        val movieId = arguments?.getLong(EXTRA_MOVIE_ID_KEY)
        setupRecyclerView(movieId ?: INVALID_MOVIE_ID)

        return binding.root
    }

    private fun setupRecyclerView(movieId: Long) {
        binding.theaterRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TheaterAdapter().also {
                it.initTheatersInfo(movieId, theaters)
                it.setItemClickListener(object : TheaterAdapter.OnItemClickListener {
                    override fun onClick(movieId: Long, theaterId: Long) {
                        presenter.itemClicked(movieId, theaterId)
                    }
                })
            }
        }
    }

    override fun navigateToDetailActivity(movieId: Long, theaterId: Long) {
        val intent = makeIntentInstance(activity as Context, movieId, theaterId,)
        startActivity(intent)
    }

    companion object {
        const val INVALID_MOVIE_ID = -99L
        const val EXTRA_MOVIE_ID_KEY = "movie_id_key"
        const val EXTRA_THEATER_ID_KEY = "theater_id_key"

        fun newFragmentInstance(movieId: Long): TheaterBottomSheetFragment {
            return TheaterBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putLong(EXTRA_MOVIE_ID_KEY, movieId)
                }
            }
        }
    }
}
