package edu.eci.cvds.samples.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.ItemRentadoDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;

@Singleton
public class ServiciosAlquilerImpl implements ServiciosAlquiler {

   @Inject
   private ItemDAO itemDAO;
   
   private ClienteDAO clienteDAO;
   
   private ItemRentadoDAO itemRentadoDAO;
   
   private TipoItemDAO tipoItemDAO;

   @Override
   public long valorMultaRetrasoxDia(int itemId) {
	   long vMult = 0;
       try {
		vMult = itemDAO.load(itemId).getTarifaxDia();
		
	} catch (PersistenceException e) {
		e.printStackTrace();
	}
       return vMult;
   }

   @Override
   public Cliente consultarCliente(long docu) throws ExcepcionServiciosAlquiler {
       try {
		return clienteDAO.loadCliente((int) docu);
	} catch (PersistenceException e) {
		throw new ExcepcionServiciosAlquiler(e.getMessage());
	}
   }

   @Override
   public List<ItemRentado> consultarItemsCliente(long idcliente) throws ExcepcionServiciosAlquiler {
       try {
		return clienteDAO.loadCliente((int) idcliente).getRentados();
	} catch (PersistenceException e) {
		throw new ExcepcionServiciosAlquiler(e.getMessage());
	}
   }

   @Override
   public List<Cliente> consultarClientes() throws ExcepcionServiciosAlquiler {
       try {
		return clienteDAO.loadClientes();
	} catch (PersistenceException e) {
		throw new ExcepcionServiciosAlquiler(e.getMessage());
	}
       
   }

   @Override
   public Item consultarItem(int id) throws ExcepcionServiciosAlquiler {
       try {
           return itemDAO.load(id);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar el item "+id);
       }
   }

   @Override
   public List<Item> consultarItemsDisponibles() {
	   List<Item> items = null;
       try {
		 items = itemDAO.consultarItemsDisponibles();
	} catch (PersistenceException e) {
		e.printStackTrace();
	}
       return items;
   }

   @Override
   public long consultarMultaAlquiler(int iditem, Date fechaDevolucion) throws ExcepcionServiciosAlquiler {
       try {
    	   long multa = 0;
    	   List<ItemRentado> itemsR = itemRentadoDAO.loadItemsRentados();
			for (ItemRentado i: itemsR) {
				long dias = ChronoUnit.DAYS.between(i.getFechafinrenta().toLocalDate(), fechaDevolucion.toLocalDate());
				if(i.getItem().getId() == iditem && dias > 0) {
					multa = dias * valorMultaRetrasoxDia(iditem);
				}
			}
			return multa;
		}catch (PersistenceException e) {
			throw new ExcepcionServiciosAlquiler("Error al consultar la multa de alquiler del Item" + iditem);
		}
   }

   @Override
   public TipoItem consultarTipoItem(int id) throws ExcepcionServiciosAlquiler {
       try {
		return tipoItemDAO.loadTipoItem(id);
	} catch (PersistenceException e) {
		throw new ExcepcionServiciosAlquiler("Error al consultar el tipo Item" + id);
	}
   }

   @Override
   public List<TipoItem> consultarTiposItem() throws ExcepcionServiciosAlquiler {
       try {
		return tipoItemDAO.loadTiposItem();
	} catch (PersistenceException e) {
		throw new ExcepcionServiciosAlquiler("Error al consultar los tipos de Item");
	}
   }

   @Override
   public void registrarAlquilerCliente(Date date, long docu, Item item, int numdias) throws ExcepcionServiciosAlquiler {
	   if(numdias < 1) throw new ExcepcionServiciosAlquiler("El numero de dias no puede ser menor que 1");
	   try{
	        
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.DAY_OF_YEAR, numdias);
	        clienteDAO.saveItemRentadoACliente((int) docu,item.getId(),date,new java.sql.Date(calendar.getTime().getTime()));
	    }  catch (PersistenceException e) {
	        throw new ExcepcionServiciosAlquiler("Error al agregar alquiler al cliente" + docu);
	    }
	   
   }

   @Override
   public void registrarCliente(Cliente c) throws ExcepcionServiciosAlquiler {
       try {
		clienteDAO.save(c);
	} catch (PersistenceException e) {
		throw new ExcepcionServiciosAlquiler(e.getMessage());
	}
   }

   @Override
   public long consultarCostoAlquiler(int iditem, int numdias) throws ExcepcionServiciosAlquiler {
	   if(numdias < 1) throw new ExcepcionServiciosAlquiler("El numero de dias no puede ser menor que 1");
       return valorMultaRetrasoxDia(iditem) * numdias;
   }

   @Override
   public void actualizarTarifaItem(int id, long tarifa) throws ExcepcionServiciosAlquiler {
       try {
		itemDAO.actualizarTarifaItem((int) tarifa, id);
	} catch (PersistenceException e) {
		throw new ExcepcionServiciosAlquiler(e.getMessage());
	}
   }
   @Override
   public void registrarItem(Item i) throws ExcepcionServiciosAlquiler {
       try {
		itemDAO.save(i);
	} catch (PersistenceException e) {
		throw new ExcepcionServiciosAlquiler(e.getMessage());
	}
   }

   @Override
   public void vetarCliente(long docu, boolean estado) throws ExcepcionServiciosAlquiler {
	   try {
		clienteDAO.setEstadoCliente(docu, estado);
	} catch (PersistenceException e) {
		throw new ExcepcionServiciosAlquiler(e.getMessage());
	}
   }
}
