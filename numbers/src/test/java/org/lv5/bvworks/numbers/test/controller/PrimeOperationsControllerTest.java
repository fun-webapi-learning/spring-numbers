package org.lv5.bvworks.numbers.test.controller;

import static org.junit.Assert.*;
import static org.junit.Assume.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.lv5.bvworks.numbers.controller.rest.PrimeOperationsController;


public class PrimeOperationsControllerTest {
	
	private Connection connection;
	
	@Before
	public void dbConnect() {
		connection = null;
		
		
		try {
			Class.forName("org.sqlite.JDBC");
			String path=this.getClass().getClassLoader().getResource("primes.db").getPath();
			connection = DriverManager.getConnection("jdbc:sqlite:"+path);
		} catch (Exception e) {
			assumeNoException(e);
		}
	}
	
	@After
	public void dbDisconnect() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			assumeNoException(e);
		}
	}
	
	@Test
	public void testListPrimes() {
		int count = 100000;
		
		PrimeOperationsController controller = new PrimeOperationsController();
		int[] primes = controller.listPrimeNumbers(count);
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM primes WHERE number BETWEEN 1 AND " + count + ";");
			while (rs.next()) {
				int number = rs.getInt("number");
				int prime = rs.getInt("prime");
				assertEquals(primes[number-1], prime);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			fail("sqlexception");
		}
	}
	
	@Test
	public void testIsPrime() {
		int count = 1000000;
		PrimeOperationsController controller = new PrimeOperationsController();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT prime FROM primes WHERE number BETWEEN 1 AND " + count + ";");
			while (rs.next()) {
				int prime = rs.getInt("prime");
				assertTrue(controller.isPrime(prime));
			}
		} catch (SQLException e) {
			fail("sqlexception");
		}
	}
	
	@Test
	public void testNotPrime() {
		int count = 10000;
		PrimeOperationsController controller = new PrimeOperationsController();
		List<Integer> primes = new ArrayList<Integer>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT prime FROM primes WHERE number BETWEEN 1 AND " + count + ";");
			while (rs.next()) {
				primes.add(rs.getInt("prime"));
			}
			stmt.close();
			for (int i=0; i<primes.get(primes.size()-1); i++) {
				if (primes.contains(i))
					continue;
				assertFalse(controller.isPrime(i));
			}
		} catch (SQLException e) {
			fail("sqlexception");
		}
	}
}
