package ie.sugrue.service.user;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface DeleteUserService {

	ResponseWrapper deleteUser(ResponseWrapper resp, User user);

}
