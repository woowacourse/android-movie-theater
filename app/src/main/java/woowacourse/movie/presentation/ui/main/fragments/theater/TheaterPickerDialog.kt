package woowacourse.movie.presentation.ui.main.fragments.theater

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.woowacourse.data.database.theater.dao.TheaterLocalDBDao
import com.woowacourse.data.datasource.theater.local.LocalTheaterDataSource
import com.woowacourse.data.repository.theater.local.LocalTheaterRepository
import woowacourse.movie.R
import woowacourse.movie.databinding.DialogTheaterPickerBinding
import woowacourse.movie.presentation.base.BaseFragmentDialog
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.model.movieitem.Movie
import woowacourse.movie.presentation.ui.main.fragments.theater.adapter.TheaterListAdapter
import woowacourse.movie.presentation.ui.main.fragments.theater.contract.TheaterContract
import woowacourse.movie.presentation.ui.main.fragments.theater.contract.presenter.TheaterPresenter
import woowacourse.movie.presentation.ui.ticketing.TicketingActivity

class TheaterPickerDialog : BaseFragmentDialog<DialogTheaterPickerBinding>(), TheaterContract.View {
    override val layoutResId: Int = R.layout.dialog_theater_picker
    override val presenter: TheaterContract.Presenter by lazy {
        TheaterPresenter(
            view = this,
            movie = requireArguments().getParcelableCompat(MOVIE_KEY)!!,
            theaterRepository = LocalTheaterRepository(
                LocalTheaterDataSource(TheaterLocalDBDao(requireContext()))
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setCanceledOnTouchOutside(false)
        binding.presenter = presenter
        binding.rvAdapter = TheaterListAdapter()
    }

    override fun showTheaterList(items: List<ListItem>) {
        binding.rvAdapter?.appendAll(items)
    }

    override fun showTicketingScreen(movie: Movie, theater: ListItem) {
        startActivity(TicketingActivity.getIntent(requireContext(), movie, theater))
        dismiss()
    }

    companion object {
        internal const val TAG = "TheaterPickerDialog"
        private const val MOVIE_KEY = "movie_id_key"

        fun getInstance(movie: Movie): TheaterPickerDialog = TheaterPickerDialog().apply {
            arguments = bundleOf(MOVIE_KEY to movie)
        }
    }
}
