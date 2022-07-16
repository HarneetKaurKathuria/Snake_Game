

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java . util.Random;

//import javax.swing.JPanel;

public class GamePannel extends JPanel implements ActionListener {
	
	static final int SCREEN_WIDTH= 600;
	static final int SCREEN_HEIGHT= 600;
	static final int UNIT_SIZE= 25 ;// how big the size of the item should be;
	static final int GAME_UNITS= (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
	static final int DELAY= 75;
	// higher the number of delay, slower the game will be  && visa versa
	
	// arrays to hold all the co-ordinates of the snake body parts and head
	final int x[] = new int [GAME_UNITS];// hold the x cordinates of snake body parts including head;
	final int y[] = new int [GAME_UNITS];// holds the y - cordinates of snake body parts including head;
	
	int bodyParts= 6;
	int applesEaten;
	int appleX;// it hold the X cordinates where the apple is going to appear and it will be random where 	
	int appleY; // snake eats an apple.
	char direction= 'R'; // snake begun by going right;
	boolean running = false;
	Timer timer;
	Random random; 
	
	GamePannel(){
		random = new Random();
		// set prefered size of pannel
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		
		startGame();
		
	}
	
	public void startGame() {
		newApple();
		running= true;
		timer= new Timer(DELAY, this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		// drawing of apple
		if(running) {// if the game is running
			/*for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE; i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);// for x-axis
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);// for y-axis
			}
			*/
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			// draw the body parts of snake
					
			for(int i=0; i<bodyParts; i++) {
				if (i==0) {
					// if i==0, means we just have head at the moment
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}else {
					// we also have body that we need to fill
					g.setColor(new Color(45, 180, 0));
					g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				//score
				g.setColor(Color.red);
				g.setFont(new Font("Ink Free",Font.BOLD, 25));
				FontMetrics metrics = getFontMetrics(g.getFont());
				g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
			
			}
		}else {
			gameOver(g);
		}
		
	}
	
	public void newApple() {
		// purpose of this method - to generate the cordinates of the new apple that appears
		// in beginining or score
		appleX= random.nextInt((int) (SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY= random.nextInt((int) (SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
		
	}
	
	public void move() {
		//moving the snake
		for(int i=bodyParts;i>0; i-- ) {
			x[i]= x[i-1];
			y[i]= y[i-1];
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
		
	}
	
	public void checkApple() {
		if((x[0]== appleX) && (y[0]== appleY)) {
			bodyParts++;//increase the length of body
			applesEaten++;// increase the score
			newApple();
		}
		
	}
	
	public void checkCollisions() {
		for(int i=bodyParts; i>0;i--) {
			if((x[0]== x[i]) && (y[0]== y[i])) {// this checks if head collides with body
				running = false;//this trigger the gameOver method	
			}
		}
		// this checks if head touches left border
		if(x[0]<0) {
			running = false;
		}
		// this checks if head touches right border
		if(x[0]>SCREEN_WIDTH) {
			running = false;
		}
		
		// this checks if head touches top border
		if(y[0]<0) {
			running = false;
		}
		// this checks if head touches bottom border
		if(y[0]>SCREEN_HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
		
		
	}
	public void gameOver(Graphics g) {
		// game Over text// display the scrore
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		//Game Over text
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		// inner class
		// to control the snake
		
		@Override
		public  void keyPressed(KeyEvent e) {
			
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
			
		}
		
	}

}
