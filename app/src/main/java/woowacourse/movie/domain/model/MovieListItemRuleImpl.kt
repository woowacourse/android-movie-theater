package woowacourse.movie.domain.model

class MovieListItemRuleImpl : MovieListItemRule<MovieListItem> {
    override fun whenIndex(index: Int): Class<out MovieListItem> {
        return if ((index + 1) % 4 == 0) {
            MovieListItem.AdItem::class.java
        } else {
            MovieListItem.MovieItem::class.java
        }
    }
}
