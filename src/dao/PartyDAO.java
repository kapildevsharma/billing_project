package dao;

import java.util.List;

import model.Party;

public interface PartyDAO {
	public boolean saveParty(String name, String contact_no,String tin_number, String address,int active, int updatePartyId);
	public List<Party> listParty();	
	public Party getParty(int partyId);
	public int deleteParty(int partyId);
}
