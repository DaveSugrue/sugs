package ie.sugrue.service.user;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.User;

@RunWith(SpringRunner.class)
@ContextConfiguration("file:src/test/resources/application-context.xml")
@SpringBootTest
public class UpdateUserServiceImplTest {

	@Autowired
	CreateUserService			createUserServiceImpl;
	@Autowired
	GetUserService				getUserServiceImpl;
	@Autowired
	UpdateUserService			updateUserServiceImpl;
	@Autowired
	private ResponseWrapper		resp;
	@Autowired
	protected JdbcTemplate		jdbc;

	private final Logger		log							= LoggerFactory.getLogger(this.getClass());

	private static final String	CREATE_USER_TABLE_SCRIPT	= "scripts/create/userCreate.sql";
	private static final String	DROP_USER_TABLE_SCRIPT		= "scripts/drop/userDrop.sql";

	private User				user;

	@Before
	public void setUp() throws Exception {
		ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(), new ClassPathResource(CREATE_USER_TABLE_SCRIPT));
		user = new User(1l, "Mike", "Cleary", "1981-01-01", "mike99@cleary.net", "111111");
		resp = createUserServiceImpl.createUser(resp, user);
		assertEquals(0, resp.getStatus().getCode());
	}

	@After
	public void tearDown() throws Exception {
		ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(), new ClassPathResource(DROP_USER_TABLE_SCRIPT));
	}

	@Test
	public void testUpdateUser() {
		User updatedUserValues = new User(1l, "Mike", "Cleary", "1981-01-01", "mike99@cleary.net", "222222");

		resp = updateUserServiceImpl.updateUser(resp, updatedUserValues);
		assertEquals(0, resp.getStatus().getCode());

		User updatedUser = getUserServiceImpl.getUser(1l);
		assertEquals(updatedUserValues, updatedUser);
	}

	@Test
	public void testUpdateInvalidUser() {
		User updatedUserValues = new User(2l, "Mike", "Cleary", "1981-01-01", "mike99@cleary.net", "222222");

		resp = updateUserServiceImpl.updateUser(resp, updatedUserValues);
		assertEquals(2, resp.getStatus().getCode());

		// Should be the same as original user
		User updatedUser = getUserServiceImpl.getUser(1l);
		assertEquals(user, updatedUser);
	}

}
