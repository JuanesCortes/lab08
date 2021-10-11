package edu.eci.cvds.sampleprj.dao.mybatis.mappers;


import java.util.List;
import org.apache.ibatis.annotations.Param;

import edu.eci.cvds.samples.entities.TipoItem;

public interface TipoItemMapper {
    
    
    public List<TipoItem> consultarTiposItems();
    
    public TipoItem consultarTipoItem(@Param("idti")int id);
    
    public void agrgarTipoItem(@Param("ti")TipoItem ti);

}
