package service.serviceImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import controller.EmployeeController;
import dao.PartyDAO;
import model.Party;
import service.PartyService;

@Service
public class PartyServiceImpl implements PartyService {

	static final Logger logger = Logger.getLogger(PartyServiceImpl.class);
	
	@Autowired
	private PartyDAO partyDAO;
	
	@Override
	@Transactional
	public boolean saveParty(String name, String contact_no, String tin_number, String address, int active,
			int updatePartyId) {
		boolean isSave = false;
		try{							
			isSave = partyDAO.saveParty(name, contact_no, tin_number, address, active, updatePartyId);
		}catch(Exception e ){
			e.printStackTrace();
			logger.error("Exception save party details :: "+e.getMessage());
		}
		return isSave;
	}

	@Override
	@Transactional
	public List<Party> listParty() {
		return partyDAO.listParty();
	}

	@Override
	@Transactional
	public Party getParty(int partyId) {
		return partyDAO.getParty(partyId);
	}
	
	@Override
	@Transactional
	public int deleteParty(int partyid){
		return partyDAO.deleteParty(partyid);
	}
}
