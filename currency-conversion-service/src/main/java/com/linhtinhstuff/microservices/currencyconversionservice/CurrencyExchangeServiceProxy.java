package com.linhtinhstuff.microservices.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="netflix-zuul-api-gateway-server")
//@RibbonClient(name="currency-exchange-service")
public interface CurrencyExchangeServiceProxy {
	
	@GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
	public ExchangeValueBean retrieveExchangeValue(@PathVariable String from, @PathVariable String to);

}
