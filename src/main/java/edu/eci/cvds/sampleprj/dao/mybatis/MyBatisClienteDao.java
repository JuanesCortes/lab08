package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;

import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;

public class MyBatisClienteDao implements ClienteDAO{
	
	@Inject
	private ClienteMapper clienteMapper;
	@Override
	public void save(Cliente cl) throws PersistenceException {
		try {
			clienteMapper.agregarCliente(cl);
		}catch(org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al registrar el Cliente "+cl.toString());
		}
		
	}

	@Override
	public Cliente load(int id) throws PersistenceException {
		try{
			return clienteMapper.consultarCliente(id);
			
		}catch(org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al cargar el Cliente "+id);
		}
		
	}

}
