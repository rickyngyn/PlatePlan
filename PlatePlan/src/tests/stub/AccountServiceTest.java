package tests.stub;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.DataBase;
import database.DataBaseFactory;
import database.StubDataBaseRecords;
import dto.Business;
import dto.Customer;
import main.ServiceFactory;
import service_interfaces.AccountService;
import service_interfaces.ReservationService;
import services.AccountsServiceImpl;
import services.ReservationServiceImpl;

class AccountServiceTest {
	AccountService accountService;
	ReservationService reservationService;
	DataBase db;
	StubDataBaseRecords stubDb;
	@BeforeEach
	void setUp() throws Exception {
		DataBaseFactory.ENVIRONMENT = "development";
		ServiceFactory.setUpServices();
		accountService = AccountsServiceImpl.getInstance();
		reservationService = ReservationServiceImpl.getInstance();
		db = DataBaseFactory.getDatabase();
		stubDb = StubDataBaseRecords.getInstance();
		stubDb.reset();
		
	}

	@Test
	void testLoginSucess() {
		Customer customer = accountService.customerLogin("john", "password");

		assertNotNull(customer);
		assertEquals(customer.getEmail(), "john");
		assertEquals(customer.getFirstName(), "john");
	}

	@Test
	void testLoginFail() {
		Customer customer = accountService.customerLogin("pouya", "password");

		assertNull(customer);
	}

	@Test
	void testLoginFail2() {
		Customer customer = accountService.customerLogin("john", "wrongPass");

		assertNull(customer);
	}

	@Test
	void testBusinessLoginSuccess() {
		Business business = accountService.businessLogin("alfredo", "password");

		assertNotNull(business);
	}

	@Test
	void testBusinessLoginFail() {
		Business business = accountService.businessLogin("alfredo", "wrongpassword");

		assertNull(business);
	}

	@Test
	void testRegisterCustomerSuccess() {
		Customer object = accountService.registerAccount("newCus@email.com", "john", "doe", "password");

		assertNotNull(object);
		assertEquals(object.getEmail(), "newCus@email.com");
	}

	@Test
	void testRegisterCustomerFail() {
		Customer object = accountService.registerAccount("john", "john", "doe", "password");

		assertNull(object);
	}

	@Test
	void testGetCustomerDetailsSuccess() {
		Customer object = accountService.getCustomerAccountDetails("john");

		assertNotNull(object);
		assertEquals(object.getEmail(), "john");
	}

	@Test
	void testGetCustomerDetailsFail() {
		Customer object = accountService.getCustomerAccountDetails("Idonotexist@email.com");

		assertNull(object);

	}
	
	@Test
	void updateBusinessAccount() {
		Business business = db.getBusinessAccount();
		business.setEmail("newemail");
		assertTrue(accountService.updateBusinessAccount(business));
		assertEquals("newemail", stubDb.business.getEmail());

	}
}
