package service;

import java.util.List;

import model.Users;

public interface UsersService{    
	public Users checkUserLogin(String userName, String userPassword);
	public boolean saveUser(Users user);
	public List<Users> listUsers();	
	public List<Users> getUsersByPage(int pageid, int total);
	public Users getUser(int userid);
	public int deleteUser(int userid);
}