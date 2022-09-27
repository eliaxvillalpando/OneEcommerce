package com.iudc.admin.setting;
import com.iudc.common.entity.Currency;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface CurrencyRepository extends CrudRepository <Currency, Integer> {
    
    
    public List<Currency> findAllByOrderByNameAsc();
    
    
    
    
    
}
