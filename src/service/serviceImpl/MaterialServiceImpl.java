package service.serviceImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import controller.EmployeeController;
import dao.MaterialDAO;
import model.Materials;
import model.Party;
import service.MaterialService;

@Service
public class MaterialServiceImpl implements MaterialService {

	static final Logger logger = Logger.getLogger(MaterialServiceImpl.class);
	
	@Autowired
	 private MaterialDAO materialDAO;
	
	@Override
	@Transactional
	public boolean saveMaterial(String materialName, String materialType, double materialUnit, double materialPrice, int active,
			int updateMaterialId,Party party) {
		boolean isSave = false;
		try{							
			isSave = materialDAO.saveMaterial(materialName, materialType, materialUnit, materialPrice, active, updateMaterialId,party) ;
		}catch(Exception e ){
			e.printStackTrace();
			logger.error("Exception save material details :: "+e.getMessage());
		}
		return isSave;
	}

	@Override
	@Transactional
	public List<Materials> listMaterial() {
		return materialDAO.listMaterial();
	}

	@Override
	@Transactional
	public Materials getMaterial(int materialId) {
		return materialDAO.getMaterial(materialId);
	}
	@Override
	@Transactional
	public List<Materials> getMaterialsByPage(int pageid, int total) {
		return materialDAO.getMaterialsByPage(pageid, total);
	}
	
	@Override
	@Transactional
	public int deleteMaterial(int materialId){
		return materialDAO.deleteMaterial(materialId);
	}
}
