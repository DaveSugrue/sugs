package ie.sugrue.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.User;
import ie.sugrue.repository.UserRepository;

@Service("updateUserService")
@Scope("prototype")
public class UpdateUserServiceImpl implements UpdateUserService {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository	userRepo;

	@Override
	public ResponseWrapper updateUser(ResponseWrapper resp, User user) {

		try {
			userRepo.updateUser(user);
		} catch (DataAccessException dae) {
			log.error("Data Access exception encountered trying to save {} to Database", user, dae);
			resp.getStatus().updateStatus(2, "We encountered a problem saving your details to our database. Please try again.");
		} catch (Exception e) {
			log.error("Unknown exception encountered trying to save {} to Database", user, e);
			resp.getStatus().updateStatus(2, "We encountered a problem saving your details to our database. Please try again.");
		}

		return resp;
	}

}
