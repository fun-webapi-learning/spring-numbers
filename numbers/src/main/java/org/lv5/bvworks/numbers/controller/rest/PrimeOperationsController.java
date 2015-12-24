package org.lv5.bvworks.numbers.controller.rest;

import java.util.Map;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prime")
public class PrimeOperationsController {

	@RequestMapping(value = "/numbers", method = RequestMethod.GET)
	public Map<String, Object> listPrimeNumbersOperation(
			@RequestParam(value = "count", defaultValue = "100") int count) {

		// output will be:
		// * numbers: <int[]>
		// * elapsed: <int>

		long begin, end;

		begin = System.nanoTime();
		int[] numbers = listPrimeNumbers(count);
		end = System.nanoTime();

		Map<String, Object> output = new HashMap<String, Object>();
		output.put("numbers", numbers);
		output.put("elapsed", (end - begin) / 1000000.0);
		return output;
	}
	
	@RequestMapping(value = "/number", method = RequestMethod.GET)
	public Map<String,Object> primeAtOperation(@RequestParam("number") int number) {
		
		Map<String,Object> numbersMap = listPrimeNumbersOperation(number);
		
		Map<String,Object> output = new HashMap<String,Object>();
		output.put("number", ((int[])numbersMap.get("numbers"))[((int[])numbersMap.get("numbers")).length-1]);
		output.put("elapsed", numbersMap.get("elapsed"));
		return output;
	}
	
	@RequestMapping(value = "/is-prime", method = RequestMethod.GET)
	public Map<String, Object> isPrimeOperation(@RequestParam("number") int number) {
		Map<String, Object> output = new HashMap<String, Object>();

		long begin, end;
		begin = System.nanoTime();
		boolean result = isPrime(number);
		end = System.nanoTime();

		output.put("answer", result);
		output.put("elapsed", (end - begin) / 1000000.0);

		return output;
	}

	public boolean isPrime(int number) {
		if (number <= 1) {
			return false;
		}
		if (number % 2 == 0 && number > 2) {
			return false;
		}
		
		for (int i = 3; i <= Math.sqrt(number); i += 2) {
			if (number % i == 0) {
				return false;
			}
		}
		
		return true;
	}

	public boolean primeDivisibleByAny(int dividend, int[] divisors, int size) {
		for (int i = 0; i < size && divisors[i] <= Math.sqrt(dividend); i++) {
			if (dividend % divisors[i] == 0) {
				return true;
			}
		}
		return false;
	}

	public int[] listPrimeNumbers(int count) {
		int[] result = new int[count];
		result[0] = 2;
		int i = 1;
		int number = 3;
		//int gap = 2;
		while (i < count) {
			if (!primeDivisibleByAny(number, result, i)) {
				result[i] = number;
				i++;
				double predicted_gap = 0; // Math.log(number);
				number += Math.max(1, (int)predicted_gap);
				
			} else {
				number += (number % 2 == 0 ? 1 : 2);
			}
			//number += 2;
		}
		return result;
	}
}
