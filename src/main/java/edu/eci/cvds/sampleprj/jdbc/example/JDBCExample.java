/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.cvds.sampleprj.jdbc.example;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class JDBCExample {
    
    public static void main(String args[]){
        try {
            String url="jdbc:mysql://desarrollo.is.escuelaing.edu.co:3306/bdprueba";
            String driver="com.mysql.jdbc.Driver";
            String user="bdprueba";
            String pwd="prueba2019";
                        
            Class.forName(driver);
            Connection con=DriverManager.getConnection(url,user,pwd);
            con.setAutoCommit(false);
                 
            
            System.out.println("Valor total pedido 1:"+valorTotalPedido(con, 1));
            
            List<String> prodsPedido=nombresProductosPedido(con, 1);
            
            
            System.out.println("Productos del pedido 1:");
            System.out.println("-----------------------");
            for (String nomprod:prodsPedido){
                System.out.println(nomprod);
            }
            System.out.println("-----------------------");
            
            
            int suCodigoECI=2134391;
            registrarNuevoProducto(con, suCodigoECI, "Juan Cortes", 666666);            
            con.commit();
                        
            
            con.close();
                                   
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(JDBCExample.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    /**
     * Agregar un nuevo producto con los parámetros dados
     * @param con la conexión JDBC
     * @param codigo
     * @param nombre
     * @param precio
     * @throws SQLException 
     */
    public static void registrarNuevoProducto(Connection con, int codigo, String nombre,int precio) throws SQLException{
        //Crear preparedStatement
        //Asignar parámetros
        //usar 'execute'

        
        con.commit();
        
    }
    
    /**
     * Consultar los nombres de los productos asociados a un pedido
     * @param con la conexión JDBC
     * @param codigoPedido el código del pedido
     * @return 
     */
    public static List<String> nombresProductosPedido(Connection con, int codigoPedido){
        List<String> np=new LinkedList<>();
        
        //Crear prepared statement
        //asignar parámetros
        //usar executeQuery
        //Sacar resultados del ResultSet
        //Llenar la lista y retornarla
        
        String conString = "SELECT ORD_PRODUCTOS.nombre FROM ORD_PEDIDOS "
        		+ "JOIN ORD_DETALLE_PEDIDO ON ORD_PEDIDOS.codigo = ORD_DETALLE_PEDIDO.pedido_fk "
        		+ "JOIN ORD_PRODUCTOS ON ORD_DETALLE_PEDIDO.producto_fk = ORD_PRODUCTOS.codigo "
        		+ "WHERE ORD_PEDIDOS.codigo = ORD_DETALLE_PEDIDO.pedido_fk and ORD_DETALLE_PEDIDO.producto_fk = ORD_PRODUCTOS.codigo"
        		+ " and ORD_PEDIDOS.codigo = ? "
        		+ "GROUP BY ORD_PRODUCTOS.nombre ";
        
        try {
			PreparedStatement conPedido = con.prepareStatement(conString);
			con.setAutoCommit(false);
			conPedido.setInt(1, codigoPedido);
			ResultSet res = conPedido.executeQuery();
			
			while (res.next()) {
				np.add(res.getString(1));
			}
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        return np;
    }

    
    /**
     * Calcular el costo total de un pedido
     * @param con
     * @param codigoPedido código del pedido cuyo total se calculará
     * @return el costo total del pedido (suma de: cantidades*precios)
     */
    public static int valorTotalPedido(Connection con, int codigoPedido){
        
        //Crear prepared statement
        //asignar parámetros
        //usar executeQuery
        //Sacar resultado del ResultSet
    	
    	int valTot = 0;
    	String conString = "SELECT sum(ORD_DETALLE_PEDIDO.cantidad * ORD_PRODUCTOS.precio) FROM ORD_PEDIDOS "
        		+ "JOIN ORD_DETALLE_PEDIDO ON ORD_PEDIDOS.codigo = ORD_DETALLE_PEDIDO.pedido_fk "
        		+ "JOIN ORD_PRODUCTOS ON ORD_DETALLE_PEDIDO.producto_fk = ORD_PRODUCTOS.codigo "
        		+ "WHERE ORD_PEDIDOS.codigo = ORD_DETALLE_PEDIDO.pedido_fk and ORD_DETALLE_PEDIDO.producto_fk = ORD_PRODUCTOS.codigo"
        		+ " and ORD_PEDIDOS.codigo = ?";
    	
    	try {
			PreparedStatement conPedido = con.prepareStatement(conString);
			con.setAutoCommit(false);
			conPedido.setInt(1,codigoPedido);
			ResultSet res = conPedido.executeQuery();
			while (res.next()) {
				valTot = res.getInt(1);
			}
			
			
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return valTot;
    }
}