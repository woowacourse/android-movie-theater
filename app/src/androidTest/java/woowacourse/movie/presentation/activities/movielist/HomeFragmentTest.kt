package woowacourse.movie.presentation.activities.movielist

//
// @RunWith(AndroidJUnit4::class)
// @LargeTest
// class HomeFragmentTest {
//    private val adInterval = 3
//
//    @get:Rule
//    internal val activityRule = ActivityScenarioRule(HomeFragment::class.java)
//
//    @Before
//    fun setup() {
//        Intents.init()
//    }
//
//    @After
//    fun tearDown() {
//        Intents.release()
//    }
//
//    /**
//     * [Movie] is fake constructor, not real constructor
//     */
//    private fun Movie(movieTitle: String, runningTime: Int): Movie =
//        Movie(
//            movieTitle,
//            LocalDate.now(),
//            LocalDate.now(),
//            runningTime,
//            "",
//            R.drawable.img_sample_movie_thumbnail1,
//        )
//
//    private fun setCustomAdapter(movieSize: Int) {
//        activityRule.scenario.onActivity { activity ->
//            val movieRecyclerView = activity.findViewById<RecyclerView>(R.id.movies_rv)
//            val adapter = MovieListAdapter(adInterval, Ad.provideDummy())
//
//            adapter.appendAll(List(movieSize) { Movie("title", 120) })
//            movieRecyclerView.adapter = adapter
//        }
//    }
//
//    @Test
//    internal fun 리사이클러뷰는_영화_정보_3개당_광고_1개를_나타낸다() {
//        val movieSize = 20
//        val adSize = 6
//        val expected = movieSize + adSize
//
//        setCustomAdapter(movieSize)
//
//        onView(withId(R.id.movies_rv))
//            .check(matchItemCount(expected))
//    }
//
//    @Test
//    internal fun 세_번에_한_번씩_광고가_등장한다() {
//        activityRule.scenario.onActivity { activity ->
//            val recyclerView = activity.findViewById<RecyclerView>(R.id.movies_rv)
//
//            val expected = recyclerView.adapter?.getItemViewType(adInterval)
//            val actual = MovieViewType.AD.type
//
//            assertEquals(expected, actual)
//        }
//    }
//
//    @Test
//    internal fun 세_번에_한_번_외에는_영화_정보가_등장한다() {
//        activityRule.scenario.onActivity { activity ->
//            val recyclerView = activity.findViewById<RecyclerView>(R.id.movies_rv)
//
//            for (position in 0 until adInterval) {
//                val expected = recyclerView.adapter?.getItemViewType(position)
//                val actual = MovieViewType.MOVIE.type
//
//                assertEquals(expected, actual)
//            }
//        }
//    }
//
//    @Test
//    internal fun 영화를_클릭하면_티켓팅_화면으로_이동한다() {
//        // given
//        onView(withId(R.id.movies_rv))
//            .check(matches(isDisplayed()))
//
//        // when
//        onView(withId(R.id.movies_rv))
//            .perform(
//                actionOnItemAtPosition<RecyclerView.ViewHolder>(
//                    0,
//                    clickViewWithId(R.id.book_btn),
//                ),
//            )
//
//        // then
//        intended(hasComponent(TicketingActivity::class.java.name))
//    }
//
//    @Test
//    internal fun `광고를_클릭하면_웹사이트가_열린다`() {
//        // given
//        onView(withId(R.id.movies_rv))
//            .check(matches(isDisplayed()))
//
//        // when
//        onView(withId(R.id.movies_rv))
//            .perform(
//                actionOnHolderItem(
//                    `is`(instanceOf(NativeAdViewHolder::class.java)),
//                    clickViewWithId(R.id.native_ad_iv),
//                ).atPosition(0),
//            )
//
//        // then
//        intended(hasAction(Intent.ACTION_VIEW))
//    }
// }
