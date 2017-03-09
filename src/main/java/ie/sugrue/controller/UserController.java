package ie.sugrue.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.User;
import ie.sugrue.service.user.CreateUserService;
import ie.sugrue.service.user.GetUserService;

@CrossOrigin(origins = "http://desktop:5000", maxAge = 3600)

@RestController
@RequestMapping("/user")
@Scope("request")
public class UserController extends PrimaryController {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	ResponseWrapper			resp;
	@Autowired
	CreateUserService		createUserServiceImpl;
	@Autowired
	GetUserService			getUserServiceImpl;

	@RequestMapping("")
	public ResponseWrapper user(@RequestParam(value = "id", defaultValue = "1") long id) {
		return getUserServiceImpl.getUser(resp, id);
	}

	@RequestMapping("/user/delete")
	public long deleteUser(@RequestParam(value = "id", defaultValue = "1") long id) {
		// mySQLUserRepositoryImpl.deleteUser(id);

		return id;
	}

	@RequestMapping(value = "/user/update", method = RequestMethod.POST)
	public void updateUser(@ModelAttribute User user, Model model) {
		System.out.println("Posting...");
		model.addAttribute("user", user);
		// mySQLUserRepositoryImpl.updateUser(user);
		return;
	}

	/*
	 * @RequestMapping(value = "/user/create", method = RequestMethod.POST) public void createUser(@ModelAttribute User user, Model model) {
	 * System.out.println("Posting..."); model.addAttribute("user", user); MySQLUserRepositoryImpl.createUser(user); return; }
	 */

	@RequestMapping(value = "/create", method = RequestMethod.PUT)
	public ResponseWrapper create(@RequestBody User user) {
		log.info("Here is my user object -> {}", user);

		return createUserServiceImpl.createUser(resp, user);
	}

}