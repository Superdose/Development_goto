package com.bharath.dating.datingapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bharath.dating.datingapi.controller.UserAccountController;
import com.bharath.dating.datingapi.entity.Interest;
import com.bharath.dating.datingapi.entity.UserAccount;
import com.bharath.dating.datingapi.repository.InterestRepository;
import com.bharath.dating.datingapi.repository.UserAccountRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

// Class for Testing always end with "Test"
// placed inside the test directory
// and corresponding to the class to be tested inside the "same" qualified package
// @SpringBootTest -> starts up application context to be used in a test
@SpringBootTest
class DatingapiApplicationTests {

	// @Mock marks the field to be mocked
	@Mock
	UserAccountRepository userAccountRepository;

	// @Mock marks the field to be mocked
	@Mock
	InterestRepository interestRepository;

	UserAccountController userAccountController;

	// @BeforeEach marks this method to be called before each test method once
	@BeforeEach
	public void setup() {
		this.userAccountController = new UserAccountController(
				this.userAccountRepository,
				this.interestRepository
		);
	}

	// @Test marks the method as a test method
	@Test
	void testRegisterUser_Successful() {
		UserAccount userAccount = this.setupBasicUserAccount();

		int newAssignedId = 123;

		UserAccount savedUserAccount = this.setupBasicUserAccount();
		savedUserAccount.setId(newAssignedId);

		// Mocks the given method
		// and returns for the corresponding call the specified return value
		when(this.userAccountRepository.save(userAccount)).thenReturn(savedUserAccount);

		UserAccount returnedUserAccount = this.userAccountController.registerUser(userAccount);

		// checks whether the value is null or not
		assertNotNull(returnedUserAccount);

		// checks whether the expected and given value are the "same"
		assertEquals(newAssignedId, returnedUserAccount.getId());
		assertEquals(savedUserAccount, returnedUserAccount);

		// Verify checks whether the specified method was called during the test
		// Usually methods of mocked fields are verified
		verify(this.userAccountRepository).save(userAccount);
	}

	// @Test marks the method as a test method
	@Test
	public void testUpdateInterest_Successful() {
		int defaultInterest = 999;
		int userAccountId = 55;
		String name = "Berta";
		Interest newInterest = this.setupBasicInterest(defaultInterest, name);
		newInterest.setUserAccountId(userAccountId);

		UserAccount oldUserAccount = this.setupBasicUserAccount();
		oldUserAccount.setId(userAccountId);
		oldUserAccount.getInterest().setUserAccountId(userAccountId);

		// Mocks the given method
		// and returns for the corresponding call the specified return value
		when(
				this.userAccountRepository.findById(newInterest.getUserAccountId())
		).thenReturn(Optional.of(oldUserAccount));

		// Mocks the given method
		// and returns for the corresponding call the specified return value
		when(
				this.interestRepository.save(newInterest)
		).thenReturn(newInterest);

		Interest savedInterest = this.userAccountController.updateInterest(newInterest);

		// checks whether the value is null or not
		assertNotNull(savedInterest);
		assertNotNull(savedInterest.getUserAccount());

		// checks whether the expected and given value are the "same"
		assertEquals(newInterest, savedInterest);
		assertEquals(newInterest.getUserAccount(), savedInterest.getUserAccount());
		assertEquals(userAccountId, savedInterest.getUserAccount().getId());

		// Verify checks whether the specified method was called during the test
		// Usually methods of mocked fields are verified
		verify(this.userAccountRepository).findById(newInterest.getUserAccountId());
		verify(this.interestRepository).save(newInterest);
	}

	// @Test marks the method as a test method
	@Test
	public void testUpdateInterest_NoUserAccountFound_ThrowsNoSuchElementException() {
		int userAccountId = 777;
		Interest newInterest = new Interest();
		newInterest.setUserAccountId(userAccountId);

		// Mocks the given method
		// and returns for the corresponding call the specified return value
		when(
				this.userAccountRepository.findById(newInterest.getUserAccountId())
		).thenReturn(Optional.empty());

		// Calls the to be tested method in the to be tested class and will assert, whether
		// the specified exception is thrown or not
		assertThrows(NoSuchElementException.class, () -> {
			this.userAccountController.updateInterest(newInterest);
		});

		// Verify checks whether the specified method was called during the test
		// Usually methods of mocked fields are verified
		verify(this.userAccountRepository).findById(newInterest.getUserAccountId());
	}

	// @Test marks the method as a test method
	@Test
	public void testGetUser_NotEmpty() {

		UserAccount userAccount1 = this.setupBasicUserAccount();

		UserAccount userAccount2 = this.setupBasicUserAccount();
		userAccount2.setUserName("Mallok");
		userAccount2.setCity("Manhattan");

		List<UserAccount> foundUserAccounts = new ArrayList<>();
		foundUserAccounts.add(userAccount1);
		foundUserAccounts.add(userAccount2);

		// Mocks the given method
		// and returns for the corresponding call the specified return value
		when(
				this.userAccountRepository.findAll()
		).thenReturn(foundUserAccounts);

		List<UserAccount> allFoundUserAccounts = this.userAccountController.getUser();

		// checks whether the value is null or not
		assertNotNull(allFoundUserAccounts);

		// checks whether the value is false
		assertFalse(allFoundUserAccounts.isEmpty());

		// checks whether the expected and given value are the "same"
		assertEquals(2, allFoundUserAccounts.size());
		assertEquals(foundUserAccounts, allFoundUserAccounts);

		// Verify checks whether the specified method was called during the test
		// Usually methods of mocked fields are verified
		verify(this.userAccountRepository).findAll();

	}

	// @Test marks the method as a test method
	@Test
	public void testGetUser_IsEmpty() {

		List<UserAccount> noUserAccountsFound = new ArrayList<>();

		// Mocks the given method
		// and returns for the corresponding call the specified return value
		when(
				this.userAccountRepository.findAll()
		).thenReturn(noUserAccountsFound);

		List<UserAccount> allFoundUserAccounts = this.userAccountController.getUser();

		// checks whether the value is null or not
		assertNotNull(allFoundUserAccounts);

		// checks whether the value is true
		assertTrue(allFoundUserAccounts.isEmpty());

		// Verify checks whether the specified method was called during the test
		// Usually methods of mocked fields are verified
		verify(this.userAccountRepository).findAll();
	}

	// @Test marks the method as a test method
	@Test
	public void testDeleteInterest_Successful() {
		int interestId = 111;

		this.userAccountController.deleteInterest(interestId);

		// Verify checks whether the specified method was called during the test
		// Usually methods of mocked fields are verified
		verify(this.interestRepository).deleteById(interestId);
	}

	// @Test marks the method as a test method
	@Test
	public void testFindMatches_NotEmpty() {

		int age = 22;
		String city = "Kannburg";
		String country = "Germany";
		int id = 13;

		UserAccount singleUserAccount = this.createSpecificUserAccount(age, city, country, id);

		UserAccount userAccount1 = this.setupBasicUserAccount();

		UserAccount userAccount2 = this.setupBasicUserAccount();
		userAccount2.setId(1234);
		userAccount2.setUserName("Kevin");
		userAccount2.setEmail("Kevin@kevin.com");
		userAccount2.setCity("Kannburg");

		List<UserAccount> correspondingUserAccounts = new ArrayList<>();
		correspondingUserAccounts.add(userAccount1);
		correspondingUserAccounts.add(userAccount2);

		// Mocks the given method
		// and returns for the corresponding call the specified return value
		when(
				this.userAccountRepository.findById(id)
		).thenReturn(Optional.of(singleUserAccount));

		// Mocks the given method
		// and returns for the corresponding call the specified return value
		when(
				this.userAccountRepository.findMatches(
						singleUserAccount.getAge(),
						singleUserAccount.getCity(),
						singleUserAccount.getCountry(),
						singleUserAccount.getId()
				)
		).thenReturn(correspondingUserAccounts);

		List<UserAccount> foundUserAccounts = this.userAccountController.findMatches(id);

		// checks whether the value is null or not
		assertNotNull(foundUserAccounts);

		// checks whether the value is false
		assertFalse(foundUserAccounts.isEmpty());

		// checks whether the expected and given value are the "same"
		assertEquals(2, foundUserAccounts.size());
		assertEquals(correspondingUserAccounts, foundUserAccounts);

		// Verify checks whether the specified method was called during the test
		// Usually methods of mocked fields are verified
		verify(this.userAccountRepository).findById(id);
		verify(this.userAccountRepository).findMatches(
				singleUserAccount.getAge(),
				singleUserAccount.getCity(),
				singleUserAccount.getCountry(),
				singleUserAccount.getId()
		);
	}

	// @Test marks the method as a test method
	@Test
	public void testFindMatches_IsEmpty() {

		int age = 24;
		String city = "Paris";
		String country = "France";
		int id = 56789;

		UserAccount singleUserAccount = this.createSpecificUserAccount(age, city, country, id);

		// Mocks the given method
		// and returns for the corresponding call the specified return value
		when(
				this.userAccountRepository.findById(id)
		).thenReturn(Optional.of(singleUserAccount));

		// Mocks the given method
		// and returns for the corresponding call the specified return value
		when(
				this.userAccountRepository.findMatches(
						singleUserAccount.getAge(),
						singleUserAccount.getCity(),
						singleUserAccount.getCountry(),
						singleUserAccount.getId())
		).thenReturn(new ArrayList<>());

		List<UserAccount> foundUserAccounts = this.userAccountController.findMatches(id);

		// checks whether the value is null or not
		assertNotNull(foundUserAccounts);

		// checks whether the value is true
		assertTrue(foundUserAccounts.isEmpty());

		// Verify checks whether the specified method was called during the test
		// Usually methods of mocked fields are verified
		verify(this.userAccountRepository).findById(id);
		verify(this.userAccountRepository).findMatches(
				singleUserAccount.getAge(),
				singleUserAccount.getCity(),
				singleUserAccount.getCountry(),
				singleUserAccount.getId()
		);
	}

	// @Test marks the method as a test method
	@Test
	public void testFindMatches_NoUserAccountFound_ThrowsNoSuchElementException() {

		int id = 99999;

		// Mocks the given method
		// and returns for the corresponding call the specified return value
		when(
				this.userAccountRepository.findById(id)
		).thenReturn(Optional.empty());

		// Calls the to be tested method in the to be tested class and will assert, whether
		// the specified exception is thrown or not
		assertThrows(NoSuchElementException.class, () -> {
			this.userAccountController.findMatches(id);
		});
	}

	private UserAccount setupBasicUserAccount() {
		UserAccount userAccount = new UserAccount();

		String name = "Herbert";

		userAccount.setUserName(name);
		userAccount.setPassword("Password123");
		userAccount.setAge(25);
		userAccount.setEmail("herbert@email.com");
		userAccount.setPhoneNumber("1234567890");
		userAccount.setGender("Man");
		userAccount.setCity("Los Angeles");
		userAccount.setCountry("United States of America");

		int interestVersion = 0;
		Interest interest = this.setupBasicInterest(interestVersion, name);
		interest.setUserAccount(userAccount);

		userAccount.setInterest(interest);

		return userAccount;
	}

	private Interest setupBasicInterest(int interestVersion, String name) {

		Interest interest = new Interest();

		if(interestVersion == 0) {

			interest.setLikes("Food");
			interest.setDislikes("Driving");
			interest.setHobbies("Flying one engine planes");

		} else {

			interest.setLikes("Everything");
			interest.setDislikes("Nothing");
			interest.setHobbies("Breathing");

		}

		interest.setProfileUrl("/dating/" + name);
		interest.setAbout("Im " + name + ".");

		return interest;

	}

	private UserAccount createSpecificUserAccount(
			int age,
			String city,
			String country,
			int id
	) {

		UserAccount singleUserAccount = this.setupBasicUserAccount();
		singleUserAccount.setAge(age);
		singleUserAccount.setCity(city);
		singleUserAccount.setCountry(country);
		singleUserAccount.setId(id);

		return singleUserAccount;
	}

}
