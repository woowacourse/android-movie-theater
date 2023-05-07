package woowacourse.movie.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.activity.moviereservation.MovieReservationActivity
import woowacourse.movie.databinding.LayoutDialogTheaterBinding
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.TheaterMovie
import woowacourse.movie.view.mapper.TheaterMovieMapper.toView

class TheaterDialog(val movie: Movie, private val theaters: List<Theater>) :
    BottomSheetDialogFragment() {

    private lateinit var binding: LayoutDialogTheaterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutDialogTheaterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val theaterMovies = findTheaterMovies()
        manageTheaterEmpty(theaterMovies)
        binding.dialogTheaterRecyclerview.adapter = TheaterAdapter(theaterMovies, this::onItemClick)
    }

    private fun manageTheaterEmpty(theaterMovies: List<TheaterMovie>) {
        if (theaterMovies.isEmpty()) {
            Toast.makeText(requireContext(), "상영관이 없습니다.", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    private fun onItemClick(data: TheaterMovie) {
        MovieReservationActivity.from(
            requireContext(), data.toView()
        ).run {
            startActivity(this)
        }
    }

    private fun findTheaterMovies(): List<TheaterMovie> {
        return theaters.mapNotNull {
            it.findMovie(movie)
        }
    }
}
