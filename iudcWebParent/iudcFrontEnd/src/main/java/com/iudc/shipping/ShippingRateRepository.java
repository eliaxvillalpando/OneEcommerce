package com.iudc.shipping;

import org.springframework.data.repository.CrudRepository;

import com.iudc.common.entity.Country;
import com.iudc.common.entity.ShippingRate;

public interface ShippingRateRepository extends CrudRepository<ShippingRate, Integer> {
	
	public ShippingRate findByCountryAndState(Country country, String state);
}
