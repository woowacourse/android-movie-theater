package woowacourse.movie.view.result

class ReservationResultActivityTest {
//    @get:Rule
//    val activityRule = ActivityScenarioRule(MainActivity::class.java)
//
//    private lateinit var repository: MockReservationTicketRepository
//
//    @Before
//    fun setUp() {
//        activityRule.scenario.onActivity {
//            repository = MockReservationTicketRepository(it)
//            Thread {
//                val reservationTicket = makeMockTicket()
//                repository.clearReservationTickets()
//                repository.saveReservationTicket(reservationTicket)
//            }.start()
//            Thread.sleep(1000)
//            it.fragmentChangeToReservationHistoryFragment()
//        }
//        Thread.sleep(1000)
//        onView(withId(R.id.recycler_view_history))
//            .perform(
//                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
//                    0,
//                    ViewActions.click(),
//                ),
//            )
//        Thread.sleep(1000)
//    }
//
//    @After
//    fun clearDB() {
//        Thread {
//            repository.clearReservationTickets()
//        }.start()
//    }
//
//    @Test
//    fun `예매한_영화의_제목을_보여준다`() {
//        onView(withId(R.id.text_view_reservation_finished_title)).check(matches(withText(movies[FIRST_MOVIE_ITEM_POSITION].title)))
//    }
//
//    @Test
//    fun `예매한_영화의_상영일을_보여준다`() {
//        onView(
//            withId(R.id.text_view_reservation_finished_screening_date),
//        ).check(matches(withText(makeMockTicket().screeningDateTime.date)))
//    }
//
//    @Test
//    fun `예매한_영화의_관람인원을_보여준다`() {
//        onView(withId(R.id.text_view_reservation_finished_number_of_tickets)).check(
//            matches(
//                withText(
//                    "2",
//                ),
//            ),
//        )
//    }
//
//    @Test
//    fun `예매한_영화의_총_결제금액을_보여준다`() {
//        onView(withId(R.id.text_view_reservation_finished_ticket_price)).check(
//            matches(
//                withText(
//                    "25,000",
//                ),
//            ),
//        )
//    }
//
//    @Test
//    fun `영화_예매_완료_화면은_영화_상세_화면의_예매_완료_버튼을_누르면_보여진다`() {
//        val intent =
//            Intent(
//                ApplicationProvider.getApplicationContext(),
//                ReservationDetailActivity::class.java,
//            ).apply {
//                putExtra(HomeFragment.MOVIE_ID, 0)
//                putExtra(TheaterSelectionFragment.THEATER_ID, 0)
//            }
//
//        ActivityScenario.launch<ReservationDetailActivity>(intent)
//
//        onView(withId(R.id.button_reservation_detail_finished)).perform(ViewActions.click())
//        onView(withId(R.id.constraint_layout_seat_selection)).check(matches(ViewMatchers.isDisplayed()))
//    }
}
