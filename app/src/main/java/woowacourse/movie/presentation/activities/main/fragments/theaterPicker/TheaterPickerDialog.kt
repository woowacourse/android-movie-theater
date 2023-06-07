package woowacourse.movie.presentation.activities.main.fragments.theaterPicker

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterPickerDialogBinding
import woowacourse.movie.presentation.activities.ticketing.TicketingActivity
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.model.item.Movie
import woowacourse.movie.presentation.model.item.Theater

class TheaterPickerDialog : BottomSheetDialogFragment(), TheaterPickerDialogContract.View {
    override lateinit var presenter: TheaterPickerDialogPresenter
    private lateinit var binding: FragmentTheaterPickerDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_theater_picker_dialog, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        val movie: Movie by lazy { requireArguments().getParcelableCompat(MOVIE_KEY)!! }

        presenter = TheaterPickerDialogPresenter(this, movie)
        val theaterAdapter = TheaterListAdapter(Theater.provideDummyData(), presenter::moveTicketingActivity)
        binding.theaterRecyclerView.adapter = theaterAdapter
    }

    override fun startTicketingActivity(theater: Theater) {
        val intent = Intent(requireContext(), TicketingActivity::class.java)
            .putExtra(MOVIE_KEY, presenter.movie)
            .putExtra(THEATER_KEY, theater)
        startActivity(intent)
    }

    companion object {
        const val TAG = "TheaterPickerDialog"
        internal const val MOVIE_KEY = "movie_key"
        internal const val THEATER_KEY = "theater_key"

        fun getInstance(movie: Movie): TheaterPickerDialog = TheaterPickerDialog().apply {
            arguments = bundleOf(MOVIE_KEY to movie)
        }
    }
}
