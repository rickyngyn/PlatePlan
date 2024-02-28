package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import customerPanels.Constants;
import customerPanels.InitialView;
import database.DataBaseFactory;

public class PlatePlanMain {

	private static JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		DataBaseFactory.getDatabase();
		ServiceFactory.setUpServices();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlatePlanMain window = new PlatePlanMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void refreshPage() {
		try {
			JPanel contentPane = (JPanel) frame.getContentPane();
			contentPane.revalidate();
			contentPane.repaint();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public PlatePlanMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, Constants.WINDOW_MAX_WIDTH, Constants.WINDOW_MAX_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Start with the CustomerSignIn panel
		switchPanels(new InitialView());
	}

	/**
	 * Method to switch between panels
	 */
	public static void switchPanels(JPanel panel) {
		JPanel contentPane = (JPanel) frame.getContentPane();
		contentPane.removeAll();
		contentPane.add(panel);
		contentPane.revalidate();
		contentPane.repaint();
	}

}
