package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.DataBase;
import database.DataBaseFactory;
import database.SQLTables;
import database.StubDataBaseRecords;
import dto.Customer;
import dto.Feedback;
import dto.MenuItem;
import dto.Server;
import dto.Table;
import dto.TimeSlot;
import main.ServiceFactory;
import service_interfaces.FeedbackService;
import service_interfaces.MenuService;
import service_interfaces.ReservationService;
import service_interfaces.ServerService;
import service_interfaces.TablesService;
import services.FeedbackServiceImpl;
import services.MenuServiceImpl;
import services.ReservationServiceImpl;
import services.ServerServiceImpl;
import services.TablesServiceImpl;

class FeedbackServiceTest {
	private FeedbackService feedbackService;
	private DataBase db;
	private StubDataBaseRecords stubDb;

	@BeforeEach
	void setUp() {
		DataBaseFactory.ENVIRONMENT = "development";
		ServiceFactory.setUpServices();
		feedbackService = FeedbackServiceImpl.getInstance();
		db = DataBaseFactory.getDatabase();
		stubDb = StubDataBaseRecords.getInstance();
		stubDb.reset();
	}

	@Test
	void addFeedback_customer() {
		Customer customer = stubDb.customers.get(0);
		Feedback feedback = new Feedback("1", customer.getFirstName() + " " + customer.getLastName(), 2,
				LocalDateTime.now(), "so good");

		Feedback returned = feedbackService.addNewFeedback("so good", 2, customer);

		assertEquals(feedback.getFeedback(), returned.getFeedback());
		assertEquals(feedback.getRating(), returned.getRating());
		assertEquals(feedback.getCustomer_id(), returned.getCustomer_id());

	}

	@Test
	void addFeedback_anonymous() {
		Feedback feedback = new Feedback("1", "Anonymous", 2, LocalDateTime.now(), "so good");

		Feedback returned = feedbackService.addNewAnonymousFeedback("so good", 2);

		assertEquals(feedback.getFeedback(), returned.getFeedback());
		assertEquals(feedback.getRating(), returned.getRating());
		assertEquals(feedback.getCustomer_id(), returned.getCustomer_id());

	}

	@Test
	void getAllFeedback() {
		assertEquals(stubDb.feedbacks, feedbackService.getAllFeedbacks());

	}

	@Test
	void deleteFeedback() {
		Feedback feedback = stubDb.feedbacks.get(0);

		assertTrue(feedbackService.deleteFeedback(feedback));
		assertFalse(stubDb.feedbacks.contains(feedback));

	}
	
	@Test
	void getAllFeedbacks() {

		double avg = 0.0;
		
		for (Feedback feedback: stubDb.feedbacks)
		{
			avg += feedback.getRating();
		}
		avg = avg/((double)stubDb.feedbacks.size());
		DecimalFormat df = new DecimalFormat("#.#");

		avg = Double.valueOf(df.format(avg));
		
		assertEquals(avg, feedbackService.getAverageRating());


	}

}
