package dao.daoImp;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.UsersDAO;
import model.Users;

@Repository
public class UsersDAOImpl implements UsersDAO{
	static final Logger logger = Logger.getLogger(UsersDAOImpl.class);
	 
	@Autowired
	SessionFactory sessionFactory;

	@Override
	 public boolean saveUser(Users user){
		boolean issave = false;
		Session session = sessionFactory.getCurrentSession();
		logger.info("Passwrod "+user.getPassword()+" UserName:"+user.getUsername()
				+" Address :: "+user.getAddress()+" DateOfBirth :: "+user.getDob()+" Contactnumber ::"+user.getContact_no()
				+" updateid ::"+user.getId());
		int isActive =0;
		int updateid =user.getId();
		Users newUser = null;
		try{
	        if(updateid>0){
	        	newUser = (Users) sessionFactory.getCurrentSession().get(Users.class, updateid);
	        }else{
	        	newUser = new Users();
	        }
	        newUser.setAddress(user.getAddress());
	        newUser.setContact_no(user.getContact_no());
	        newUser.setDob(user.getDob());
	        newUser.setUsername(user.getUsername());
	        newUser.setIsActive(isActive);
	        newUser.setPassword(user.getPassword());
	        session.saveOrUpdate(newUser);
	        issave =true;
        
		}catch(Exception e ){
			issave = false;
			e.printStackTrace();
			logger.error("Exception in saveOrUpdate user details ::"+e.getMessage());
		}
       
		return issave;
	 }
	
	@Override
	public List<Users> listUsers() {
		return (List<Users>) sessionFactory.getCurrentSession().createCriteria(Users.class).list();
	}
	
	@Override
	public Users getUser(int userid) {
		return (Users) sessionFactory.getCurrentSession().get(Users.class, userid);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public Users checkUserLogin(String userName, String userPassword) {
		Users loginuser  = null;
		/*Session session = sessionFactory.getCurrentSession();
		String SQL_QUERY =" from Users login where login.username= :uname and login.password= :pwd";
		Query<Users> query = session.createQuery(SQL_QUERY);
		query.setParameter("uname",userName);
		query.setParameter("pwd",userPassword);
		List<Users> list = query.list();
		if ((list != null) && (list.size() > 0)) {
			loginuser = list.get(0);
		}*/
		
		
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(Users.class);
		cr.add(Restrictions.eq("username", userName)).add(Restrictions.eq("password", userPassword));
		loginuser = (Users) cr.uniqueResult();
		
		return loginuser;
	}
	
	public int deleteUser(int userid){
		int flag = 0;
		flag = sessionFactory.getCurrentSession().createQuery("DELETE FROM Users WHERE id = "+userid).executeUpdate();
		return flag;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<Users> getUsersByPage(int PageNumber, int PageSize) {
		List<Users> list = null;
		try{
			PageNumber = PageSize*(PageNumber-1);
			
			Session session = sessionFactory.getCurrentSession();
			String SQL_QUERY =" from Users login order by login.id ";
			Query<Users> query = session.createQuery(SQL_QUERY);
			
			list = query.list();
			
			
		//	String SQL_QUERY =" from Users user order by user.id OFFSET :PageNumber ROWS  FETCH NEXT :PageSize ROWS ONLY";
			/*query.setParameter("PageNumber",PageNumber);
			query.setParameter("PageSize",PageSize);
			list = query.list();*/
			
			
		}catch(Exception e ){
			e.printStackTrace();
		}
		return list;
	}
	
}