package services;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import database.DataBase;
import database.DataBaseFactory;
import database.SQLTables;
import dto.Customer;
import dto.Feedback;
import service_interfaces.AccountService;
import service_interfaces.FeedbackService;
import service_interfaces.MenuService;
import service_interfaces.ReservationService;
import service_interfaces.ServerService;
import service_interfaces.TablesService;

public class FeedbackServiceImpl implements FeedbackService {

	private static FeedbackService instance;

	private DataBase db;
	private ReservationService reservationService;
	private TablesService tablesService;
	private ServerService serviceUtils;
	private AccountService accountService;
	private MenuService menuService;

	private FeedbackServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	public static FeedbackService getInstance() {
		// Create the instance if it does not exist
		if (instance == null) {
			instance = new FeedbackServiceImpl();
		}
		// Return the existing instance
		return instance;

	}
	
	@Override
	public void initializeDependency(ReservationService reservationService, TablesService tablesService,
			ServerService serviceUtils, AccountService accountService, MenuService menuService) {

		db = DataBaseFactory.getDatabase();
		this.reservationService = reservationService;
		this.serviceUtils = serviceUtils;
		this.tablesService = tablesService;
		this.accountService = accountService;
		this.menuService = menuService;
	}
	
	@Override
	public Feedback addNewFeedback(String givenString, int rating, Customer customer) {
		String customerName = customer.getFirstName() + " " + customer.getLastName();
		Feedback feedback = new Feedback(UUID.randomUUID().toString(), 
				customerName, rating, LocalDateTime.now(), givenString);
		if (db.insertRecord(SQLTables.FEEDBACKS_TABLE, feedback))
		{
			return feedback;
		}
		return null;
	}
	
	@Override
	public Feedback addNewAnonymousFeedback(String givenString, int rating) {
		Feedback feedback = new Feedback(UUID.randomUUID().toString(), 
				"Anonymous", rating, LocalDateTime.now(), givenString);
		if (db.insertRecord(SQLTables.FEEDBACKS_TABLE, feedback))
		{
			return feedback;
		}
		return null;
	}

	@Override
	public boolean deleteFeedback(Feedback feedback) {
		return db.deleteDataBaseEntry(SQLTables.FEEDBACKS_TABLE, feedback.getId());
	}

	@Override
	public List<Feedback> getAllFeedbacks() {
		return db.getAllFeedbacks();
	}
	
	@Override
	public double getAverageRating() {
		double average = 0;
		List<Feedback> feedbacks = db.getAllFeedbacks();
		for (Feedback feedback: feedbacks)
		{
			average += feedback.getRating();
		}
		
		average = average/feedbacks.size();
		
		DecimalFormat df = new DecimalFormat("#.#");
		
		return Double.valueOf(df.format(average));
	}

}
