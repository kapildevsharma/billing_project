package service;

import java.util.List;

import model.Materials;
import model.Party;

public interface MaterialService {
	public boolean saveMaterial(String materialName, String materialType,double materialUnit, double materialPrice,int active, int updateMaterialId,Party party);
	public List<Materials> listMaterial();	
	public Materials getMaterial(int materialId);
	public List<Materials> getMaterialsByPage(int pageid, int total);
	public int deleteMaterial(int materialId);
}
