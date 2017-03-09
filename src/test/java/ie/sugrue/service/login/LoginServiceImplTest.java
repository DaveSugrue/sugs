package ie.sugrue.service.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import ie.sugrue.service.user.CreateUserService;

@RunWith(SpringRunner.class)
@ContextConfiguration("file:src/test/resources/application-context.xml")
@SpringBootTest
public class LoginServiceImplTest {

	@Autowired
	CreateUserService			createUserServiceImpl;
	@Autowired
	LoginService				loginServiceImpl;
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
	public void testValidateLogin() {
		String pass1 = "password1";
		String pass2 = "password1";
		assertTrue(loginServiceImpl.validateLogin(pass1, pass2));
		pass2 = "password2";
		assertFalse(loginServiceImpl.validateLogin(pass1, pass2));
	}

	@Test
	public void testLoginValidPassword() {
		resp = loginServiceImpl.login(resp, user);
		assertEquals(0, resp.getStatus().getCode());
	}

	@Test
	public void testLoginInvalidPassword() {
		user.setPw("222222");
		resp = loginServiceImpl.login(resp, user);
		assertEquals(1, resp.getStatus().getCode());
		assertEquals("Username or Password are incorrect. Please try again", resp.getStatus().getMessages().get(0));
	}

	@Test
	public void testLoginNoUser() {
		user = new User(1l, "Nobody", "Here", "1981-01-01", "nobody@here.net", "111111");
		resp = loginServiceImpl.login(resp, user);
		assertEquals(1, resp.getStatus().getCode());
		assertEquals("User does not exist. Please try again", resp.getStatus().getMessages().get(0));
	}
}
