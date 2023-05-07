package woowacourse.movie.presentation.movielist.selecttheater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.databinding.FragmentSelectTheaterBottomSheetBinding
import woowacourse.movie.model.data.remote.DummyMovieTheaterStorage
import woowacourse.movie.presentation.booking.BookingActivity
import kotlin.properties.Delegates

class SelectTheaterBottomSheet : BottomSheetDialogFragment(), SelectTheaterContract.View {

    override lateinit var presenter: SelectTheaterContract.Presenter

    private var movieId by Delegates.notNull<Long>()
    private var screeningState by Delegates.notNull<Boolean>()
    private lateinit var selectTheaterAdapter: SelectTheaterAdapter

    private var _binding: FragmentSelectTheaterBottomSheetBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectTheaterBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initArgumentData()
        initPresenter()
        checkScreeningState()
        initAdapter()
        initRecyclerViewMovieTheater()
    }

    private fun initArgumentData() {
        movieId = arguments?.getLong(MOVIE_ID) ?: return this.dismiss()
    }

    private fun initPresenter() {
        presenter = SelectTheaterPresenter(this, DummyMovieTheaterStorage(), movieId)
    }

    override fun setViewByScreeningState(screeningState: Boolean) {
        binding.screeningState = screeningState
    }

    private fun checkScreeningState() {
        screeningState = presenter.checkScreeningState()
    }

    private fun initAdapter() {
        selectTheaterAdapter = SelectTheaterAdapter(movieId, ::clickBook, presenter)
    }

    private fun initRecyclerViewMovieTheater() {
        if (screeningState) {
            binding.rvMovieTheaterSelect.adapter = selectTheaterAdapter
            selectTheaterAdapter.submitList(presenter.getTheatersByMovieId(movieId))
        }
    }

    private fun clickBook(theater: String) {
        startActivity(BookingActivity.getIntent(requireActivity(), movieId, theater))
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val MOVIE_ID = "movieId"

        fun getBundle(movieId: Long) = Bundle().apply { putLong(MOVIE_ID, movieId) }
    }
}
