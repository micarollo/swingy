package com.swingy.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import com.swingy.controller.GameController;

public class GuiView extends JFrame {
	private GameController gameController;

	public GuiView(GameController gameController) {
		this.gameController = gameController;		
		setTitle("Swingy RPG");
		setSize(1500, 1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		// init();
	}

	public void init() {
		setLayout(new BorderLayout());

		JLabel titleLabel = new JLabel("Bienvenido a Swingy RPG", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
		add(titleLabel, BorderLayout.NORTH);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

		JButton existingHeroButton = new JButton("Play with an existing hero");
		JButton newHeroButton = new JButton("Play with a new hero");

		buttonPanel.add(existingHeroButton);
		buttonPanel.add(newHeroButton);
		existingHeroButton.addActionListener(e -> gameController.selectHeroGuiMode());

		add(buttonPanel, BorderLayout.CENTER);
	}

	//move outside view
	public void showHeroSelectionDialog(List<Object[]> heroesData) {
		String[][] heroData = new String[heroesData.size()][4];
		for (int i = 0; i < heroesData.size(); i++) {
			Object[] hero = heroesData.get(i);
			heroData[i][0] = String.valueOf(hero[0]);
			heroData[i][1] = (String) hero[1];
			heroData[i][2] = (String) hero[2];
			heroData[i][3] = String.valueOf(hero[3]);
		}
		String[] columnNames = {"ID", "Name", "Class", "Level"};

		JTable heroTable = new JTable(heroData, columnNames);
		heroTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(heroTable);

		JDialog dialog = new JDialog(this, "Select a Hero", true);
		dialog.setSize(600, 500);
		dialog.setLayout(new BorderLayout());

		// confirm selection
		JButton selectButton = new JButton("Select Hero");
		selectButton.addActionListener(e -> {
			int selectedRow = heroTable.getSelectedRow();
			if (selectedRow != -1) {
				String heroName = heroTable.getValueAt(selectedRow, 1).toString();
				JOptionPane.showMessageDialog(this, "You selected: " + heroName);
				dialog.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Please select a hero.");
			}
		});

		dialog.add(scrollPane, BorderLayout.CENTER);
		dialog.add(selectButton, BorderLayout.SOUTH);
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	
		//return id?
	}
}