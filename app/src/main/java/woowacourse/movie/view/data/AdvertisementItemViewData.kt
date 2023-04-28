package woowacourse.movie.view.data

import java.io.Serializable

class AdvertisementItemViewData(
    val banner: ImageViewData,
    override val viewType: MovieListViewType = MovieListViewType.ADVERTISEMENT
) : Serializable, MovieListItemViewData
