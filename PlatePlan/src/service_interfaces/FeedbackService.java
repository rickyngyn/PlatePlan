package service_interfaces;

import java.util.List;

import dto.Customer;
import dto.Feedback;

public interface FeedbackService {

	public Feedback addNewFeedback(String feedback, int rating, Customer customer);

	public boolean deleteFeedback(Feedback feedback);

	public List<Feedback> getAllFeedbacks();

	void initializeDependency(ReservationService reservationService, TablesService tablesService,
			ServerService serviceUtils, AccountService accountService, MenuService menuService);

	Feedback addNewAnonymousFeedback(String feedback, int rating);

	double getAverageRating();

}
