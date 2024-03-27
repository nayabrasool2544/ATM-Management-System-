package bank_management_system;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.sql.*;

public class MiniStatement extends JFrame implements ActionListener{
	String pinNumber;
	MiniStatement(String pinNumber){
		this.pinNumber = pinNumber;
		
		JLabel bank = new JLabel("Indian Bank");
		bank.setBounds(150,20,100,20);
		add(bank);
		
		JLabel card = new JLabel();
		card.setBounds(20,80,300,20);
		add(card);
		
		setTitle("Mini Statement");
		JLabel mini = new JLabel();
		mini.setBounds(20,100,400,200);
		add(mini);
		
		JLabel balance = new JLabel();
		balance.setBounds(20,400,300,20);
		add(balance);
		
		try {
			Conn conn = new Conn();
			ResultSet rs = conn.stm.executeQuery("select * from login where pin= '"+pinNumber+"'");
			while(rs.next()) {
				card.setText("Card Number : " + rs.getString("cardnumber").substring(0,4) + "XXXXXXXX" + rs.getString("cardnumber").substring(12));
			}
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		try {
			Conn conn = new Conn();
			int bal=0;
			ResultSet rs = conn.stm.executeQuery("select * from bank where pin = '"+pinNumber+"'");
			while(rs.next()) {
				mini.setText(mini.getText() + "<html>" + rs.getString("date") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString("type") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +rs.getString("amount") +"<br><br><html>");
				if(rs.getString("type").equals("Deposit")) {
					bal += Integer.parseInt(rs.getString("amount"));
				}
				else{
					bal -= Integer.parseInt(rs.getString("amount"));
				}
			}
			balance.setText("Your current account balance is RS "+bal);
			}
			catch(Exception e) {
				System.out.println(e);
			}
		
		setLayout(null);
		setSize(400,600);
		setLocation(20,20);
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent ae){
		
	}
	public static void main(String[] args) {
		new MiniStatement("");

	}

}
