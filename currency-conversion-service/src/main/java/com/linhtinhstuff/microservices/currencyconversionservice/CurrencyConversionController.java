package com.linhtinhstuff.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeServiceProxy currencyExchangeProxy;

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<ExchangeValueBean> response = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", ExchangeValueBean.class, uriVariables);
		ExchangeValueBean exchangeValue = response.getBody();

		return new CurrencyConversionBean(exchangeValue.getId(), from, to, exchangeValue.getConversionMultiple(),
				quantity, quantity.multiply(exchangeValue.getConversionMultiple()),
				exchangeValue.getPort());
	}
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		ExchangeValueBean exchangeValue = currencyExchangeProxy.retrieveExchangeValue(from, to);

		return new CurrencyConversionBean(exchangeValue.getId(), from, to, exchangeValue.getConversionMultiple(),
				quantity, quantity.multiply(exchangeValue.getConversionMultiple()),
				exchangeValue.getPort());
	}

}
