package woowacourse.movie.presentation.view.main.home

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.presentation.view.main.home.data.MovieData

class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListView(view)
    }

    private fun setListView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_movie_list)
        val movies = MovieData.getData()
        val adapter = MovieListAdapter(
            movies,
            ContextCompat.getDrawable(requireContext(), R.drawable.advertise_wooteco)!!
        )
        recyclerView.adapter = adapter
    }
}
