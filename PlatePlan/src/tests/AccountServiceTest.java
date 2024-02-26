package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.DataBase;
import database.DataBaseFactory;
import dto.Business;
import dto.Customer;
import misc.StubDataBaseRecords;
import service_interfaces.AccountService;
import service_interfaces.ReservationService;
import services.AccountsServiceImpl;
import services.ReservationServiceImpl;

class AccountServiceTest {
	AccountService accountService;
	ReservationService reservationService;
	DataBase db;

	@BeforeEach
	void setUp() throws Exception {
		DataBaseFactory.ENVIRONMENT = "development";
		accountService = new AccountsServiceImpl();
		reservationService = new ReservationServiceImpl();
		db = DataBaseFactory.getDatabase();
		StubDataBaseRecords.reset();

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
}
