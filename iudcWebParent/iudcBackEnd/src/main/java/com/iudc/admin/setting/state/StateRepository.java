package com.iudc.admin.setting.state;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.iudc.common.entity.Country;
import com.iudc.common.entity.State;

public interface StateRepository extends CrudRepository<State, Integer> {
	
	public List<State> findByCountryOrderByNameAsc(Country country);
}
