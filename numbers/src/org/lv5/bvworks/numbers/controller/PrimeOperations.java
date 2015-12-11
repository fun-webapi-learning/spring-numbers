package org.lv5.bvworks.numbers.controller;

import java.util.Map;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prime")
public class PrimeOperations {
	
	@RequestMapping(value = "/numbers", method = RequestMethod.GET)
	public Map<String, Object> listPrimeNumbersOperation(@RequestParam(value = "count", defaultValue = "100")int count) {
		
		// output will be:
		//  * numbers: <int[]>
		//	* elapsed: <int>
		
		long begin, end;
		
		begin = System.nanoTime();
		int[] numbers = listPrimeNumbers(count);
		end = System.nanoTime();
		
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("numbers", numbers);
		output.put("elapsed", (end - begin) / 1000000.0);
		return output;
	}
	
	@RequestMapping(value = "/is-prime", method = RequestMethod.GET)
	public Map<String, Object> isPrimeOperation(@RequestParam("number")int number) {
		Map<String, Object> output = new HashMap<String, Object>();
		
		long begin, end;
		begin = System.nanoTime();
		boolean result = isPrime(number);
		end = System.nanoTime();
		
		output.put("answer", result);
		output.put("elapsed", (end - begin) / 1000000.0);
		
		return output;
	}
	
	private boolean isPrime(int number) {
		if (number <= 1) {
			return false;
		}
		for (int i=2; i <= Math.sqrt(number); i++) {
			if (number % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	private int[] listPrimeNumbers(int count) {
		int[] result = new int[count];
		result[0] = 2;
		int i = 1;
		int number = 3;
		while (i < count) {
			if (isPrime(number)) {
				result[i] = number;
				i++;
			}
			number += 2;
		}
		return result;
	}
}
