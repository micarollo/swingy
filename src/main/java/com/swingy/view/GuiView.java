package com.swingy.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.swingy.controller.GameController;

public class GuiView extends JFrame {
	private final GameController gameController;
	private MapPanel mapPanel;
	private JButton helpButton;

	public GuiView(GameController gameController) {
		this.gameController = gameController;		
		setTitle("Swingy RPG");
		setSize(720, 820); //check!!!!
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
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
		newHeroButton.addActionListener(e -> gameController.createNewHeroGuiMode());
		add(buttonPanel, BorderLayout.CENTER);

		mapPanel = new MapPanel();
		add(mapPanel, BorderLayout.SOUTH);
	}

	//move outside view
	public Integer showHeroSelectionDialog(List<Object[]> heroesData) {
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

		final Integer[] selectedHeroId = {null};     
		// confirm selection
		JButton selectButton = new JButton("Select Hero");
		selectButton.addActionListener(e -> {
			int selectedRow = heroTable.getSelectedRow();
			if (selectedRow != -1) {
				String heroName = heroTable.getValueAt(selectedRow, 1).toString();
				// selectedHeroId[0] = (Integer) heroTable.getValueAt(selectedRow, 0);
				selectedHeroId[0] = Integer.parseInt((String) heroTable.getValueAt(selectedRow, 0));
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
	
		return selectedHeroId[0];
	}

	public String[] showNewHeroDialog() {
		System.out.println("Here");
		JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Create a New Hero", true);
		dialog.setSize(400, 300);
		dialog.setLayout(new BorderLayout());

		JPanel panel = new JPanel(new GridLayout(3, 2));

		panel.add(new JLabel("Enter Hero Name:"));
		JTextField nameField = new JTextField();
		panel.add(nameField);

		panel.add(new JLabel("Choose Hero Class:"));
		String[] classes = {"Warrior", "Mage"};
		JComboBox<String> classBox = new JComboBox<>(classes);
		panel.add(classBox);

		final String[] heroData = new String[2];
		JButton createButton = new JButton("Create Hero");
		createButton.addActionListener(e -> {
			String heroName = nameField.getText().trim();
			String heroClass = (String) classBox.getSelectedItem();

			if (!heroName.isEmpty()) {
				// gameController.createHero(heroName, heroClass);
				heroData[0] = heroClass;
				heroData[1] = heroName;
				dialog.dispose();
			} else {
				JOptionPane.showMessageDialog(dialog, "Please enter a hero name.");
			}
		});

		dialog.add(panel, BorderLayout.CENTER);
		dialog.add(createButton, BorderLayout.SOUTH);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		return heroData;
	}

	private void showInitialControlsDialog() {
        String controls = "<html><div style='text-align:center;'><h2>Game Controls</h2>"
                + "<p><b>WASD</b> - To move yout Hero</p>"
                + "<p><b>E</b> - Open Hero Stats</p>"
                + "<p><b>Q</b> - Exit</p>"
                + "<p>Then you can check this using: <b>?</b> button </p></div></html>";

			JOptionPane.showOptionDialog(
            this,
            controls,
            "Game Controls",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new Object[]{"Ok"},
            "Ok"
        );
    }

	private void styleHelpButton() {
        helpButton.setFont(new Font("Arial", Font.BOLD, 14));
        helpButton.setPreferredSize(new Dimension(30, 25)); // Tamaño pequeño
        helpButton.setMargin(new Insets(0, 0, 0, 0));
        helpButton.setToolTipText("Mostrar controles");
        helpButton.addActionListener(e -> showInitialControlsDialog());
    }

	// public void drawMap(int[][] map) {
	// 	getContentPane().removeAll();
	// 	mapPanel.setMap(map);

	// 	int width = map[0].length * 80;
	// 	int height = map.length * 80 + 20;

	// 	setSize(width, height);
	// 	setResizable(false);

	// 	add(mapPanel, BorderLayout.CENTER);

	// 	// actualizar UI
	// 	revalidate();
	// 	repaint();
	// }

	public void drawMap(int[][] map) {
		showInitialControlsDialog();
		getContentPane().removeAll();
		mapPanel = new MapPanel();
		mapPanel.setMap(map);

		int cellSize = 80;
		mapPanel.setPreferredSize(
			new Dimension(
				map[0].length * cellSize,
				map.length * cellSize
			)
		);
		helpButton = new JButton("?");
        styleHelpButton();
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(helpButton);
		add(mapPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		pack();
		//bordes internos del JFrame
		Insets insets = getInsets();
		//calc tamaño final sumando los bordes
		int totalWidth = map[0].length * cellSize + insets.left + insets.right;
		int totalHeight = map.length * cellSize + insets.top + insets.bottom + 40;
		setMinimumSize(new Dimension(totalWidth, totalHeight));
		setSize(totalWidth, totalHeight);
		setLocationRelativeTo(null);

		revalidate();
		repaint();
	}
}