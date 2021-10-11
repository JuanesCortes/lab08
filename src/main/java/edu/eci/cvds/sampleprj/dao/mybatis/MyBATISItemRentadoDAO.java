package edu.eci.cvds.sampleprj.dao.mybatis;

import java.util.List;

import com.google.inject.Inject;

import edu.eci.cvds.sampleprj.dao.ItemRentadoDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemRentadoMapper;
import edu.eci.cvds.samples.entities.ItemRentado;

public class MyBATISItemRentadoDAO implements ItemRentadoDAO{
	
	@Inject
	private ItemRentadoMapper itemRentadoMapper;

	@Override
	public ItemRentado loadItemRentado(int id) throws PersistenceException {
		try {
			return itemRentadoMapper.consultarItemRentado(id);
		}catch(org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al cargar el item rentado "+id);
		}
	}

	@Override
	public List<ItemRentado> loadItemsRentados() throws PersistenceException{
		try {
			return itemRentadoMapper.consultarItemsRentados();
		}catch(org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al cargar los items rentados rentados ");
		}

	}

}
