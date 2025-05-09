package woowacourse.movie.domain.model.item

import woowacourse.movie.domain.model.movie.Movie

sealed class MovieListItem {
    data class MovieItem(
        val movie: Movie,
    ) : MovieListItem()

    data class AdItem(
        val advertisement: Advertisement,
    ) : MovieListItem()

    companion object {
        fun movieListItems(
            movies: List<MovieItem>,
            ads: List<AdItem>,
        ): List<MovieListItem> {
            val adsIterator = ads.iterator()
            val list: List<MovieListItem> =
                buildList {
                    movies.forEachIndexed { index, movie ->
                        add(movie)
                        if (index % 3 == 2 && adsIterator.hasNext()) {
                            add(adsIterator.next())
                        }
                    }
                    while (adsIterator.hasNext()) {
                        add(adsIterator.next())
                    }
                }
            return list
        }
    }
}
