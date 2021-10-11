package edu.eci.cvds.sampleprj.dao.mybatis;

import java.util.List;

import com.google.inject.Inject;

import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.TipoItemMapper;
import edu.eci.cvds.samples.entities.TipoItem;

public class MyBATISTipoItemDAO implements TipoItemDAO{
	
	@Inject
	private TipoItemMapper tipoItemMapper;

	@Override
	public void saveTipoItem(TipoItem ti) throws PersistenceException {
		try {
			tipoItemMapper.agrgarTipoItem(ti);
		}catch(org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al registrar el tipo de item "+ ti.toString());
		}
		
	}

	@Override
	public TipoItem loadTipoItem(int id) throws PersistenceException{
		try {
			return tipoItemMapper.consultarTipoItem(id);
		}catch(org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al cargar el tipo de item " + id);
		}
	}

	@Override
	public List<TipoItem> loadTiposItem() throws PersistenceException{
		try {
			return tipoItemMapper.consultarTiposItems();
		}catch(org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al cargar los tipos de item ");
		}
	}

}
