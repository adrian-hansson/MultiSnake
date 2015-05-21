package ClientSide;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.GridLayout;
import java.awt.BorderLayout;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;

public class View implements KeyListener, ActionListener{
	
	private JFrame mainFrame;
	JTextField textField;
	Socket socket = null;
	boolean connected = false;
	Level level;

	public static void main(String[] args) {
		try {
			View window = new View();
			window.mainFrame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public View() {
		initialize();
	}

	private void initialize() {
		connectToServer();
		
		mainFrame = new JFrame();
		mainFrame.setResizable(false);
		mainFrame.setTitle("MultiSnake");
		mainFrame.setBounds(100, 100, 516, 539);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		level = new Level(socket);
		level.setToolTipText("");
		level.setBackground(new Color(165, 98, 67));
		mainFrame.getContentPane().add(level, BorderLayout.CENTER);
		mainFrame.addKeyListener(this);
	}
	
	private void connectToServer() {
		JFrame jf = new JFrame();
		jf.setResizable(false);
		jf.setBounds(10, 10, 300, 50);
		jf.setTitle("Connect to server");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		textField = new JTextField(50);
		textField.addActionListener(this);
        jf.add(textField);
        jf.setVisible(true);
		while(!connected) {
			try {
				Thread.sleep(500);
			} catch(InterruptedException e) {
				System.out.println("Could not pause Thread");
			}
		}
		jf.dispose();
	}

	//------------------------------------------------
	//        ACTION LISTENER PART
	//------------------------------------------------
	public void actionPerformed(ActionEvent evt) {
		try{
			socket = new Socket(textField.getText(), 30000);
			InputStream is = socket.getInputStream();
			byte[] msg = new byte[60000];
			is.read(msg);
			String message = new String(msg);
			if(message.startsWith("Connected to server")) {
				textField.setText("Connected to server");
				connected = true;
			} else {
				textField.setText("That server is full");
			}
		} catch(IOException ie) {
			textField.setText("Could not connect to that IP-adress");
			System.out.println("Caught IO");
		}
	}
	
	//------------------------------------------------
	//        KEY LISTENER PART
	//------------------------------------------------
	public void keyPressed(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_UP){
			level.pressUp();
		}
		else if(key.getKeyCode() == KeyEvent.VK_DOWN){
			level.pressDown();
		}
		else if(key.getKeyCode() == KeyEvent.VK_LEFT){
			level.pressLeft();
		}
		else if(key.getKeyCode() == KeyEvent.VK_RIGHT){
			level.pressRight();
		}
		
	}
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
}
