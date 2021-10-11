package edu.eci.cvds.sampleprj.dao;

import java.util.List;

import edu.eci.cvds.samples.entities.TipoItem;

public interface TipoItemDAO {
	
	public void saveTipoItem(TipoItem ti) throws PersistenceException;
	
	public TipoItem loadTipoItem(int id) throws PersistenceException;
	
	public List<TipoItem> loadTiposItem() throws PersistenceException;
}
