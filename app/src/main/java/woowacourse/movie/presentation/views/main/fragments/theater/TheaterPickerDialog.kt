package woowacourse.movie.presentation.views.main.fragments.theater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.woowacourse.data.database.theater.dao.TheaterLocalDBDao
import com.woowacourse.data.datasource.theater.local.LocalTheaterDataSource
import com.woowacourse.data.repository.theater.local.LocalTheaterRepository
import woowacourse.movie.databinding.DialogTheaterPickerBinding
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.model.movieitem.Movie
import woowacourse.movie.presentation.model.theater.Theater
import woowacourse.movie.presentation.views.main.fragments.theater.adapter.TheaterListAdapter
import woowacourse.movie.presentation.views.main.fragments.theater.contract.TheaterContract
import woowacourse.movie.presentation.views.main.fragments.theater.contract.presenter.TheaterPresenter
import woowacourse.movie.presentation.views.ticketing.TicketingActivity

class TheaterPickerDialog : BottomSheetDialogFragment(), TheaterContract.View {
    override val presenter: TheaterContract.Presenter by lazy {
        TheaterPresenter(
            view = this,
            movie = requireArguments().getParcelableCompat(MOVIE_KEY)!!,
            theaterRepository = LocalTheaterRepository(
                LocalTheaterDataSource(TheaterLocalDBDao(requireContext()))
            )
        )
    }

    private var _binding: DialogTheaterPickerBinding? = null
    private val binding get() = _binding!!

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
        binding.presenter = presenter
        binding.rvAdapter = TheaterListAdapter()
    }

    override fun showTheaterList(items: List<Theater>) {
        binding.rvAdapter?.appendAll(items)
    }

    override fun showTicketingScreen(movie: Movie, theater: Theater) {
        startActivity(TicketingActivity.getIntent(requireContext(), movie, theater))
        dismiss()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        internal const val TAG = "TheaterPickerDialog"
        private const val MOVIE_KEY = "movie_id_key"

        fun getInstance(movie: Movie): TheaterPickerDialog = TheaterPickerDialog().apply {
            arguments = bundleOf(MOVIE_KEY to movie)
        }
    }
}
