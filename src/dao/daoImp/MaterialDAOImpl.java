package dao.daoImp;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.MaterialDAO;
import model.Materials;
import model.Party;
import model.Users;

@Repository
public class MaterialDAOImpl implements MaterialDAO {
	static final Logger logger = Logger.getLogger(MaterialDAOImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean saveMaterial(String materialName, String materialType,double materialUnit, double materialPrice,int active, int updateMaterialId,Party party) {
		boolean issave = false;
		Session session = sessionFactory.getCurrentSession();
		logger.info("materialName " + materialName + " materialType ::" + materialType + " materialUnit :"
				+ materialUnit + " materialPrice :" + materialPrice + " isActive :" + active+" party :"+party+" party id : "+party.getParty_id() + " updateMaterialId ::" + updateMaterialId);
		Materials material = null;
		try {
			if (updateMaterialId > 0) {
				material = (Materials) sessionFactory.getCurrentSession().get(Materials.class, updateMaterialId);
			} else {
				material = new Materials();
			}
			material.setMaterialName(materialName);
			material.setMaterialType(materialType);
			material.setUnit(materialUnit);
			material.setPrice(materialPrice);
			material.setIsActive(active);
			material.setParty(party);
			session.saveOrUpdate(material);
			issave = true;

		} catch (Exception e) {
			issave = false;
			e.printStackTrace();
			logger.error("Exception in saveOrUpdate material details ::" + e.getMessage());
		}

		return issave;
	}

	@Override
	public List<Materials> listMaterial() {
		return (List<Materials>) sessionFactory.getCurrentSession().createCriteria(Materials.class).list();
	}

	@Override
	@SuppressWarnings({"unchecked" })
	public List<Materials> getMaterialsByPage(int PageNumber, int PageSize){
		List<Materials> list = null;
		try{
			PageNumber = PageSize*(PageNumber-1);
			
			Session session = sessionFactory.getCurrentSession();
			String SQL_QUERY =" from Materials material order by material.id ";
			Query<Materials> query = session.createQuery(SQL_QUERY);
			
			list = query.list();
			
			
			/*Session session = sessionFactory.getCurrentSession();
			OFFSET :PageNumber ROWS  FETCH NEXT :PageSize ROWS ONLY
			String SQL_QUERY =" from Materials  order by id ";
			Query query = session.createQuery(SQL_QUERY);
			query.setParameter("PageNumber",PageNumber);
			query.setParameter("PageSize",PageSize); 
			list = ((Criteria) query).list();*/
			
		}catch(Exception e ){
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public Materials getMaterial(int materialId) {
		return (Materials) sessionFactory.getCurrentSession().get(Materials.class, materialId);
	}
	
	@Override
	public int deleteMaterial(int materialid){
		int flag = 0;
		Session session = sessionFactory.getCurrentSession();
		String SQL_QUERY ="DELETE FROM Materials WHERE id= :materialid";
		Query query = session.createQuery(SQL_QUERY);
		query.setParameter("materialid",materialid);
		flag = query.executeUpdate();
		return flag;
	}
	
}
