package bank_management_system;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Withdraw extends JFrame implements ActionListener{
	
	JTextField amount;
	JButton withdraw,back;
	String pinNumber;
	Withdraw(String pinNumber){
		this.pinNumber = pinNumber;
		setLayout(null);
		ImageIcon i1 = new ImageIcon(getClass().getResource("./atm.jpg"));
		Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel image = new JLabel(i3);
		image.setBounds(0,0,900,900);
		add(image);
		
		JLabel text = new JLabel("Enter the amount you want to Withdraw");
		text.setForeground(Color.WHITE);
		text.setFont(new Font("System",Font.BOLD,16));
		text.setBounds(170,300,400,20);
		image.add(text);
		
		amount = new JTextField();
		amount.setFont(new Font("Raleway",Font.BOLD,20));
		amount.setBounds(170,350,320,25);
		image.add(amount);
		
		withdraw = new JButton("Withdraw");
		withdraw.setBounds(355,485,150,30);
		withdraw.addActionListener(this);
		image.add(withdraw);
		
		back = new JButton("Back");
		back.setBounds(355,520,150,30);
		back.addActionListener(this);
		image.add(back);
		
		setSize(900,900);
		setLocation(300,0);
		setResizable(false);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == withdraw) {
			String number = amount.getText();
			Date date = new Date();
			if(number.equals("")) {
				JOptionPane.showMessageDialog(null,"Please Enter Amount To Withdraw");
			}
			
			else {
				Conn c = new Conn();
				int bal=0;
				try {
					ResultSet rs = c.stm.executeQuery("select * from bank where pin = '"+pinNumber+"'");
					while(rs.next()) {
						if(rs.getString("type").equals("Deposit")) {
							bal += Integer.parseInt(rs.getString("amount"));
						}else {
							bal -= Integer.parseInt(rs.getString("amount"));
						}
					}
				} 
				catch (SQLException e) {
					System.out.println(e);
				}
				if(bal>=Integer.parseInt(number)) {
					String query = "insert into bank values('"+pinNumber+"', '"+date+"', 'Withdraw', '"+number+"')";
					try {
						c.stm.executeUpdate(query);
						JOptionPane.showMessageDialog(null, "Withdraw Successfull.");
						setVisible(false);
						new Transaction(pinNumber).setVisible(true);
					} 
					catch (Exception e) {
						
						System.out.println(e);
					}	
				}
				else {
					JOptionPane.showMessageDialog(null, "Balance insufficient");
				}
			}
		}
		else if(ae.getSource() == back) {
			setVisible(false);
			new Transaction(pinNumber).setVisible(true);
		}
	}
	public static void main(String[] args) {
		new Withdraw("");

	}

}
