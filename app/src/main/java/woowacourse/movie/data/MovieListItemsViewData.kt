package woowacourse.movie.data

data class MovieListItemsViewData(val value: List<MovieListItemViewData>) {
    companion object {
        fun from(
            movie: List<MovieViewData>,
            advertisement: List<AdvertisementItemViewData>,
            movieCount: Int
        ): MovieListItemsViewData {
            return mutableListOf<MovieListItemViewData>().apply {
                for (index in movie.indices) {
                    if (index > 0 && index % movieCount == 0) add(advertisement[0])
                    add(movie[index])
                }
            }.let {
                MovieListItemsViewData(it)
            }
        }
    }
}
