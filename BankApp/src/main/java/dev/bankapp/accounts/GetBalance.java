package dev.bankapp.accounts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Scanner;

import dev.bankapp.authentication.User;
import dev.bankapp.utils.Logger;
import dev.bankapp.utils.LoggingLevels;


public class GetBalance {

	User user;
	int accountnum;
	Logger log = Logger.getLogger();
	
	public GetBalance(User user) {	}
	
	public void getBalances(Scanner scan,User user) {
		log.log("User is viewing all account balances", LoggingLevels.TRACE);
		String jbdcURL = System.getenv("DB_URL");
		String username = System.getenv("DB_USER");
		String password = System.getenv("DB_PASS");
		
		System.out.println();
		System.out.println("---====Balances====---");
		System.out.println("1. Savings Balance");
		System.out.println("2. Checkings Balance");
		System.out.println("3. Total Balance");
		System.out.println("4. Back");
		System.out.print("Choice: ");
		
		int choice = scan.nextInt();
		
		switch(choice) {
				case 1:
					try {
						log.log("User choose to view savings balance", LoggingLevels.TRACE);
						double totalBalance;
						Connection connection = DriverManager.getConnection(jbdcURL, username, password);
						PreparedStatement ps;
						String sql1 = "select sum(balance) as balance from savings join bankuser on bankuser.userid = savings.userid where bankuser.username ="+ "'" +user.getUsername()+"'"+" ;";
						ps = connection.prepareStatement(sql1);
						ResultSet rs = ps.executeQuery();
						if (rs.next() == false) {
							log.log("User does not own a savings account and tried to access one", LoggingLevels.DEBUG);
							System.out.println();
							System.out.println("You currently dont have a Savings account");
							System.out.println();
							return;
						} else {
							totalBalance = rs.getDouble("balance");
						}
				        DecimalFormat df = new DecimalFormat(".00");

						System.out.println();
						System.out.println("*******************************************");
						System.out.println();
						System.out.println("Saving Accounts Total: $"+df.format(totalBalance)+"");
						System.out.println();
						System.out.println("*******************************************");
						System.out.println();
					} catch (SQLException e) {
						log.log("DB connection error", LoggingLevels.ERROR);
						e.printStackTrace();
					}
					break;
				case 2:
					try {
						log.log("User choose to view checkings balance", LoggingLevels.TRACE);
						double totalBalance;
						Connection connection = DriverManager.getConnection(jbdcURL, username, password);
						PreparedStatement ps;
						String sql1 = "select sum(balance) as balance from checkings join bankuser on bankuser.userid = checkings.userid where bankuser.username ="+ "'" +user.getUsername()+"'"+" ;";
						ps = connection.prepareStatement(sql1);
						ResultSet rs = ps.executeQuery();
						if (rs.next() == false) {
							log.log("User does not own a checkings account and tried to access one", LoggingLevels.DEBUG);
							System.out.println();
							System.out.println("You currently dont have a Checkings account");
							System.out.println();
							return;
						} else {
							totalBalance = rs.getDouble("balance");
						}
				        DecimalFormat df = new DecimalFormat(".00");

						System.out.println();
						System.out.println("*******************************************");
						System.out.println();
						System.out.println("Checking Accounts Total: $"+df.format(totalBalance)+"");
						System.out.println();
						System.out.println("*******************************************");
						System.out.println();
					} catch (SQLException e) {
						log.log("DB connection error", LoggingLevels.ERROR);
						e.printStackTrace();
					}
					break;
				case 3:
					try {
						log.log("User choose to view both balances", LoggingLevels.TRACE);
					double totalBalance = 0;
					Connection connection = DriverManager.getConnection(jbdcURL, username, password);
					PreparedStatement ps;
					PreparedStatement ps1;

					String sql1 = "select sum(savings.balance) as sbalance from savings join bankuser on bankuser.userid = savings.userid where bankuser.username = "+"'"+user.getUsername()+"'"+";";
					ps = connection.prepareStatement(sql1);
					ResultSet rs = ps.executeQuery();
					if (rs.next() == false) {
						log.log("User does not own a savings account and tried to access one", LoggingLevels.DEBUG);
						System.out.println();
						System.out.println("You need a Savings Account for this to work");
						System.out.println();
						return;
					} else {
						double savingsBalance = rs.getDouble("sbalance");
						totalBalance = totalBalance + savingsBalance;
					}
					String sql2 = "select sum(checkings.balance) as cbalance from checkings join bankuser on bankuser.userid = checkings.userid where bankuser.username = "+"'"+user.getUsername()+"'"+";";
					ps1 = connection.prepareStatement(sql2);
					ResultSet rs1 = ps1.executeQuery();
					if (rs1.next() == false) {
						log.log("User does not own a checkings account and tried to access one", LoggingLevels.DEBUG);
						System.out.println();
						System.out.println("You need a Checkings Account for this to work");
						System.out.println();
						return;
					} else {
						double checkingsBalance = rs1.getDouble("cbalance");
						totalBalance = totalBalance + checkingsBalance;
					}
			        DecimalFormat df = new DecimalFormat(".00");

					System.out.println();
					System.out.println("*******************************************");
					System.out.println();
					System.out.println("Total Balance: $"+df.format(totalBalance)+"");
					System.out.println();
					System.out.println("*******************************************");
					System.out.println();
					} catch (SQLException e) {
						log.log("DB connection error", LoggingLevels.ERROR);
						e.printStackTrace();
					}
					break;
				case 4:
					break;
				default:
					log.log("User choose an option thats not specified", LoggingLevels.TRACE);
					System.out.println("Choose a number above");
					System.out.println();
					getBalances(scan, user);
					break;
			
		}
		
	}
	
	
	
	
	
}
