import javax.swing.JFrame;

public class GameFrame extends JFrame {
		
	GameFrame(){
		GamePannel pannel = new GamePannel();	
		this.add(pannel);
		// could be written as -- this.add(GamePannel());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);// to make the window visible in the middle of the screen
		
	}

}
