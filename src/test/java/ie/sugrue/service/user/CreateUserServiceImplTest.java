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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.User;

@RunWith(SpringRunner.class)
@ContextConfiguration("file:src/test/resources/application-context.xml")
@Rollback
@SpringBootTest
public class CreateUserServiceImplTest {

	@Autowired
	CreateUserService			createUserServiceImpl;
	@Autowired
	private ResponseWrapper		resp;
	@Autowired
	protected JdbcTemplate		jdbc;

	private final Logger		log							= LoggerFactory.getLogger(this.getClass());

	private static final String	CREATE_USER_TABLE_SCRIPT	= "scripts/create/userCreate.sql";
	private static final String	DROP_USER_TABLE_SCRIPT		= "scripts/drop/userDrop.sql";

	@Before
	public void setUp() throws Exception {
		ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(), new ClassPathResource(CREATE_USER_TABLE_SCRIPT));
	}

	@After
	public void tearDown() throws Exception {
		createUserServiceImpl = null;
		resp = null;
	}

	@Test
	public void testCreateUser() {
		User user = new User(1l, "Mike", "Cleary", "1981-01-01", "mike99@cleary.net", "111111");
		resp = createUserServiceImpl.createUser(resp, user);
		assertEquals(0, resp.getStatus().getCode());
		resp = createUserServiceImpl.createUser(resp, user);
		assertEquals(1, resp.getStatus().getCode());
		assertEquals("The email address 'mike99@cleary.net' is already in use.", resp.getStatus().getMessages().get(0));
	}
}
