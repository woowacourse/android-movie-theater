package woowacourse.movie.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private val theaterMovies = mutableListOf<TheaterMovie>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutDialogTheaterBinding.inflate(inflater, container, false)
        theaters.forEach {
            it.findMovie(movie).apply {
                if (this != null) theaterMovies.add(this)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dialogTheaterRecyclerview.adapter = TheaterAdapter(theaterMovies) {
            MovieReservationActivity.from(
                requireContext(), it.toView()
            ).run {
                startActivity(this)
            }
        }
    }
}
