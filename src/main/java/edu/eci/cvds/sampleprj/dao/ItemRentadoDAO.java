package edu.eci.cvds.sampleprj.dao;

import java.util.List;

import edu.eci.cvds.samples.entities.ItemRentado;

public interface ItemRentadoDAO {
	
	public ItemRentado  loadItemRentado(int id) throws PersistenceException;
	
	public List<ItemRentado> loadItemsRentados() throws PersistenceException;
}
