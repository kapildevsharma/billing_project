package service.serviceImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import controller.EmployeeController;
import dao.UsersDAO;
import model.Users;
import service.UsersService;

@Service
public class UsersServiceImp implements UsersService {

	static final Logger logger = Logger.getLogger(UsersServiceImp.class);
	
	 @Autowired
	 private UsersDAO userDAO;

	@Override
	@Transactional
	public Users checkUserLogin(String userName, String userPassword) {
		 Users existUser = null;
		 try{
			 existUser = userDAO.checkUserLogin(userName, userPassword);
		 }catch(Exception e ){
			 e.printStackTrace();
			 logger.error("Exception :: "+e.getMessage());
		 }
         return existUser;
	}
	
	@Transactional 
	@Override
	public boolean saveUser(Users user){
		boolean isSave = false;
		try{		
			isSave = userDAO.saveUser(user);
		}catch(Exception e ){
			e.printStackTrace();
			logger.info("Exception save user details :: "+e.getMessage());
		}
		return isSave;
	}

	@Transactional 
	@Override
	public List<Users> listUsers() {
		 return userDAO.listUsers();
	}
	@Transactional 
	@Override
	public Users getUser(int userid) {
		return userDAO.getUser(userid);
	}
	@Transactional 
	@Override
	public int deleteUser(int userid){
		return userDAO.deleteUser(userid);
	}
	@Transactional 
	@Override
	public List<Users> getUsersByPage(int pageid, int total){
		return userDAO.getUsersByPage(pageid, total);
	}
}