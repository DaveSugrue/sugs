package ie.sugrue.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.Status;
import ie.sugrue.domain.User;
import ie.sugrue.repository.UserRepository;

@Service("getUserService")
@Scope("prototype")
public class GetUserServiceImpl implements GetUserService {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository	userRepo;

	@Override
	public User getUser(long id) {

		User user = new User();

		try {
			user = userRepo.getUser(id);
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting user with id of {} from DB - ", id, erdae);
		} catch (Exception e) {
			log.error("Problem occured getting user with id of {} from DB - ", id, e);
		}

		return user;
	}

	@Override
	public ResponseWrapper getUser(ResponseWrapper resp, long id) {

		try {
			User user = userRepo.getUser(id);
			resp.addObject(user);
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting user with id of {} from DB - ", id, erdae);
			Status status = new Status(1, "User Does Not Exist");
			resp.setStatus(status);
		} catch (Exception e) {
			log.error("Problem occured getting user with id of {} from DB - ", id, e);
			Status status = new Status(2, "Problem getting User data from DB");
			resp.setStatus(status);
		}

		return resp;
	}

	@Override
	public User getUser(String email) {

		User user = new User();

		try {
			user = userRepo.getUser(email);
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting user with email of {} from DB - ", email, erdae);
		} catch (Exception e) {
			log.error("Problem occured getting user with email of {} from DB - ", email, e);
		}

		return user;
	}

	@Override
	public ResponseWrapper getUser(ResponseWrapper resp, String email) {

		try {
			User user = userRepo.getUser(email);
			resp.addObject(user);
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting user with email of {} from DB - ", email, erdae);
			Status status = new Status(1, "User Does Not Exist");
			resp.setStatus(status);
		} catch (Exception e) {
			log.error("Problem occured getting user with id of email from DB - ", email, e);
			Status status = new Status(2, "Problem getting User data from DB");
			resp.setStatus(status);
		}

		return resp;
	}

}
