package dao.daoImp;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.PartyDAO;
import model.Party;

@Repository
public class PartyDAOImpl implements PartyDAO{
	static final Logger logger = Logger.getLogger(PartyDAOImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean saveParty(String name, String contact_no, String tin_number, String address, int isActive,
			int updatePartyId) {
		boolean issave = false;
		Session session = sessionFactory.getCurrentSession();
		logger.info("Party name "+name+" contact_no ::"+contact_no+" tin_number :"+tin_number+" address :"+address
				+" isActive :"+isActive+" updatePartyId ::"+updatePartyId );
		Party party = null;
		try{
	        if(updatePartyId>0){
	        	party = (Party) sessionFactory.getCurrentSession().get(Party.class, updatePartyId);
	        }else{
	        	party = new Party();
	        }
	        party.setName(name);
	        party.setContact_no(contact_no);
	        party.setTin_number(tin_number);
	        party.setAddress(address);
	        party.setIsActive(isActive);;
	        
	        session.saveOrUpdate(party);
	        issave =true;
        
		}catch(Exception e ){
			issave = false;
			e.printStackTrace();
			logger.error("Exception in saveOrUpdate party details ::"+e.getMessage());
		}
       
		return issave;
	}

	@Override
	public List<Party> listParty() {
		return (List<Party>) sessionFactory.getCurrentSession().createCriteria(Party.class).list();
	}

	@Override
	public Party getParty(int partyId) {
		return (Party) sessionFactory.getCurrentSession().get(Party.class, partyId);
	}

	@Override
	public int deleteParty(int partyid){
		int flag = 0;
		Session session = sessionFactory.getCurrentSession();
		String SQL_QUERY ="DELETE FROM Party WHERE party_id= :party_id";
		Query query = session.createQuery(SQL_QUERY);
		query.setParameter("party_id",partyid);
		flag = query.executeUpdate();
		return flag;
	}
}
