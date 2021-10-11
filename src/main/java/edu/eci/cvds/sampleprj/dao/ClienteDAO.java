package edu.eci.cvds.sampleprj.dao;

import java.sql.Date;
import java.util.List;

import edu.eci.cvds.samples.entities.Cliente;


public interface ClienteDAO {
	
	public void save(Cliente cl) throws PersistenceException;

	public Cliente loadCliente(int id) throws PersistenceException;
	
	public List<Cliente> loadClientes()throws PersistenceException;
	
	public void saveItemRentadoACliente (int id, int idit, Date fechainicio, Date fechafin)throws PersistenceException;
	
	
}
