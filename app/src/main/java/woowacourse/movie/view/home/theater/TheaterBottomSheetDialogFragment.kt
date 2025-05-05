package woowacourse.movie.view.home.theater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.databinding.FragmentTheaterBottomSheetDialogBinding
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Showings
import woowacourse.movie.util.getSerializableCompat
import woowacourse.movie.view.dialog.DialogFactory
import woowacourse.movie.view.home.movies.OnBottomSheetDialogEventListener
import woowacourse.movie.view.home.movies.adapter.TheaterAdapter

class TheaterBottomSheetDialogFragment : BottomSheetDialogFragment(), TheaterContract.View {
    private var _binding: FragmentTheaterBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    private var eventListener: OnBottomSheetDialogEventListener? = null

    private val presenter: TheaterContract.Presenter by lazy {
        TheaterPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTheaterBottomSheetDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val movie = arguments?.getSerializableCompat("movie", Movie::class.java)

        if (movie == null) {
            handleInvalidTicket()
        } else {
            presenter.fetchData(movie)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun handleInvalidTicket() {
        DialogFactory().showError(requireContext()) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun showTheaterList(showings: List<Showings>) {
        val recyclerView = binding.rvTheaterCategory

        val theaterAdapter =
            TheaterAdapter(
                object : OnTheaterEventListener {
                    override fun onClickReservation(showings: Showings) {
                        eventListener?.onClick(showings)
                        dismiss()
                    }
                },
            )

        recyclerView.adapter = theaterAdapter
        theaterAdapter.submitList(showings)
    }

    companion object {
        fun newInstance(
            movie: Movie,
            eventListener: OnBottomSheetDialogEventListener,
        ): TheaterBottomSheetDialogFragment {
            val fragment = TheaterBottomSheetDialogFragment()
            fragment.eventListener = eventListener
            return fragment.apply {
                arguments =
                    Bundle().apply {
                        putSerializable("movie", movie)
                    }
            }
        }
    }
}
