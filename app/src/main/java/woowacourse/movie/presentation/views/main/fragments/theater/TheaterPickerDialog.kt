package woowacourse.movie.presentation.views.main.fragments.theater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.woowacourse.data.database.theater.dao.TheaterDao
import com.woowacourse.data.datasource.theater.local.LocalTheaterDataSource
import com.woowacourse.data.repository.theater.local.LocalTheaterRepository
import woowacourse.movie.databinding.DialogTheaterPickerBinding
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.model.movieitem.Movie
import woowacourse.movie.presentation.model.theater.PresentationTheater
import woowacourse.movie.presentation.views.main.fragments.theater.adapter.TheaterListAdapter
import woowacourse.movie.presentation.views.main.fragments.theater.contract.TheaterContract
import woowacourse.movie.presentation.views.main.fragments.theater.contract.presenter.TheaterPresenter
import woowacourse.movie.presentation.views.ticketing.TicketingActivity

class TheaterPickerDialog : BottomSheetDialogFragment(), TheaterContract.View {
    override val presenter: TheaterContract.Presenter by lazy { makePresenter() }

    private var _binding: DialogTheaterPickerBinding? = null
    private val binding get() = _binding!!

    private var _adapter: TheaterListAdapter? = null
    private val adapter get() = _adapter!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        dialog?.setCanceledOnTouchOutside(false)
        _binding = DialogTheaterPickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _adapter = TheaterListAdapter(presenter::onTheaterClick)
        binding.theaterRv.adapter = adapter

        setupPresenter()
    }

    private fun setupPresenter() {
        binding.presenter = presenter
    }

    override fun showTheaterList(items: List<PresentationTheater>) {
        adapter.appendAll(items)
    }

    override fun showTicketingScreen(movie: Movie, theater: PresentationTheater) {
        startActivity(TicketingActivity.getIntent(requireContext(), movie, theater))
        dismiss()
    }

    private fun makePresenter(): TheaterContract.Presenter {
        val movie: Movie = requireArguments().getParcelableCompat(MOVIE_KEY)!!
        return TheaterPresenter(
            view = this,
            movie = movie,
            theaterRepository = LocalTheaterRepository(
                LocalTheaterDataSource(TheaterDao(requireContext()))
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
        _binding = null
    }

    companion object {
        internal const val TAG = "TheaterPickerDialog"
        private const val MOVIE_KEY = "movie_id_key"

        fun getInstance(movie: Movie): TheaterPickerDialog = TheaterPickerDialog().apply {
            arguments = bundleOf(MOVIE_KEY to movie)
        }
    }
}
