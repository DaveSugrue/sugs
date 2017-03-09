package ie.sugrue.service.user;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.User;

public interface GetUserService {

	public User getUser(long id);

	public ResponseWrapper getUser(ResponseWrapper resp, long id);

	public User getUser(String email);

	public ResponseWrapper getUser(ResponseWrapper resp, String email);
}
