package edu.eci.cvds.sampleprj.dao.mybatis;

import java.sql.Date;
import java.util.List;

import com.google.inject.Inject;

import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;

public class MyBATISClienteDao implements ClienteDAO{
	
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
	public Cliente loadCliente(int id) throws PersistenceException {
		try{
			return clienteMapper.consultarCliente(id);
			
		}catch(org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al cargar el Cliente "+id);
		}
		
	}

	@Override
	public List<Cliente> loadClientes() throws PersistenceException {
		try {
			return clienteMapper.consultarClientes();
		}catch(org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al cargar los Clientes ");
		}
	}

	@Override
	public void saveItemRentadoACliente(int id, int idit, Date fechainicio, Date fechafin) throws PersistenceException {
		try {
			clienteMapper.agregarItemRentadoACliente(id, idit, fechainicio, fechafin);
		}catch(org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("Error al salvar el item rentado "+idit);
		}
	}

}
