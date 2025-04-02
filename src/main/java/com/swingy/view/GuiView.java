package com.swingy.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Point;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;  // Para el parámetro del actionPerformed
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.JWindow;

import com.swingy.controller.GameController;
import com.swingy.model.Hero;

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
		showMainMenu();
	}

	public void showMainMenu() {
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
		// JDialog controlsDialog = new JDialog(this, "Game Controls", true);
		// controlsDialog.setSize(400, 300);
		// controlsDialog.setLayout(new BorderLayout());

		// // Panel principal con márgenes
		// JPanel contentPanel = new JPanel();
		// contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		// contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

		// // Panel de controles con grid
		// JPanel controlsPanel = new JPanel(new GridLayout(0, 1, 5, 10));
		
		// // Añadir cada control con formato
		// addControlLine(controlsPanel, "WASD", "Move your hero");
		// addControlLine(controlsPanel, "E", "Open hero stats");
		// addControlLine(controlsPanel, "Q", "Exit game");

		// contentPanel.add(controlsPanel);
		// contentPanel.add(Box.createVerticalGlue());

		// // Botón OK centrado
		// JButton okButton = new JButton("OK");
		// okButton.setFont(new Font("Arial", Font.BOLD, 14));
		// okButton.setPreferredSize(new Dimension(100, 30));
		// okButton.addActionListener(e -> controlsDialog.dispose());
		
		// JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		// buttonPanel.add(okButton);

		// // Ensamblar diálogo
		// controlsDialog.add(contentPanel, BorderLayout.CENTER);
		// controlsDialog.add(buttonPanel, BorderLayout.SOUTH);
		// controlsDialog.setLocationRelativeTo(this);
		// controlsDialog.setVisible(true);
		JDialog controlsDialog = new JDialog(this, "Game Controls", true);
		controlsDialog.setSize(350, 350);  // Mismo tamaño que stats
		controlsDialog.setLayout(new BorderLayout());

		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

		JPanel controlsPanel = new JPanel(new GridLayout(0, 1, 8, 12));
		Font statsFont = new Font("Arial", Font.PLAIN, 20);
		controlsPanel.add(createStatLabel("WASD - Move your hero", statsFont));
		controlsPanel.add(createStatLabel("E - Open hero stats", statsFont));
		controlsPanel.add(createStatLabel("Q - Exit game", statsFont));

		contentPanel.add(controlsPanel);
		contentPanel.add(Box.createVerticalGlue());

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton okButton = new JButton("OK");
		okButton.setFont(new Font("Arial", Font.BOLD, 16));
		okButton.setPreferredSize(new Dimension(120, 40));
		okButton.addActionListener(e -> controlsDialog.dispose());
		buttonPanel.add(okButton);

		controlsDialog.add(contentPanel, BorderLayout.CENTER);
		controlsDialog.add(buttonPanel, BorderLayout.SOUTH);
		controlsDialog.setLocationRelativeTo(this);
		controlsDialog.setVisible(true);
    }

	// private void addControlLine(JPanel panel, String key, String description) {
	// 	JPanel linePanel = new JPanel(new BorderLayout());
		
	// 	JLabel keyLabel = new JLabel(key);
	// 	keyLabel.setFont(new Font("Arial", Font.BOLD, 16));
	// 	keyLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		
	// 	JLabel descLabel = new JLabel(description);
	// 	descLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		
	// 	linePanel.add(keyLabel, BorderLayout.WEST);
	// 	linePanel.add(descLabel, BorderLayout.CENTER);
	// 	panel.add(linePanel);
	// }

	private void styleHelpButton() {
        helpButton.setFont(new Font("Arial", Font.BOLD, 14));
        helpButton.setPreferredSize(new Dimension(100, 25));
        helpButton.setMargin(new Insets(0, 0, 0, 0));
        helpButton.setToolTipText("Mostrar controles");
        helpButton.addActionListener(e -> showInitialControlsDialog());
    }

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
		helpButton = new JButton("? Help");
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

		//activar controles
		setupKeyBindings();
		revalidate();
		repaint();
	}

	public void repaintMap(int[][] map) {
		mapPanel.updateMap(map);
	}

	private void setupKeyBindings() {
		InputMap inputMap = mapPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = mapPanel.getActionMap();
		inputMap.clear();

		String[] keys = {"W", "A", "S", "D", "E", "Q", "w", "a", "s", "d", "e", "q"};
		for (String key : keys) {
			inputMap.put(KeyStroke.getKeyStroke(key), key);
			actionMap.put(key, new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// if (currentState == GameState.MAP) {
					System.out.println(key.charAt(0));
						gameController.handleInput(Character.toLowerCase(key.charAt(0)));
					// }
				}
			});	
		}
	}

	public void displayHeroStats(Hero hero) {
		JDialog statsDialog = new JDialog(this, "Hero Stats", true);
		statsDialog.setSize(350, 350);  // Ventana más grande
		statsDialog.setLayout(new BorderLayout());

		// Panel para los stats con letra más grande
		JPanel statsPanel = new JPanel(new GridLayout(6, 1, 8, 12));
		statsPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
		
		Font statsFont = new Font("Arial", Font.PLAIN, 20);
		
		statsPanel.add(createStatLabel("Name: " + hero.getName(), statsFont));
		statsPanel.add(createStatLabel("Class: " + hero.getHeroClass(), statsFont));
		statsPanel.add(createStatLabel("Level: " + hero.getLevel(), statsFont));
		statsPanel.add(createStatLabel("HP: " + hero.getHitPoints(), statsFont));
		statsPanel.add(createStatLabel("Attack: " + hero.getAttack(), statsFont));
		statsPanel.add(createStatLabel("Exp: " + hero.getExperience() + "/" + hero.calculateLevelUp(hero.getLevel()), statsFont));

		// Botón OK centrado
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton okButton = new JButton("OK");
		okButton.setFont(new Font("Arial", Font.BOLD, 16));
		okButton.setPreferredSize(new Dimension(120, 40));
		okButton.addActionListener(e -> statsDialog.dispose());
		buttonPanel.add(okButton);

		statsDialog.add(statsPanel, BorderLayout.CENTER);
		statsDialog.add(buttonPanel, BorderLayout.SOUTH);
		statsDialog.setLocationRelativeTo(this);
		statsDialog.setVisible(true);
	}

	private JLabel createStatLabel(String text, Font font) {
		JLabel label = new JLabel(text, SwingConstants.CENTER);
		label.setFont(font);
		return label;
	}

	public void showHpRecoveryMessage(int hpRecovered, int currentHp) {
		// JDialog popup = new JDialog(this);
		// popup.setUndecorated(true); // Sin bordes
		// popup.setSize(250, 100);
		// popup.setLayout(new BorderLayout());
		
		// JLabel message = new JLabel(
		// 	"<html><center>🧪 +" + hpRecovered + " HP<br>Total: " + currentHp + "</center></html>", 
		// 	SwingConstants.CENTER
		// );
		// message.setFont(new Font("Arial", Font.BOLD, 14));
		// popup.add(message, BorderLayout.CENTER);
		
		// // Posición: esquina superior derecha
		// Point loc = getLocation();
		// popup.setLocation(loc.x + getWidth() - 260, loc.y + 30);
		
		// // Temporizador para auto-cierre (3 segundos)
		// new Timer(2000, e -> popup.dispose()).start();
		
		// popup.setVisible(true);
		JPanel toastPanel = new JPanel();
		toastPanel.setBackground(new Color(50, 50, 50, 220)); // Fondo oscuro semi-transparente
		toastPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		
		JLabel label = new JLabel("+" + hpRecovered + " HP  (Total: " + currentHp + ")");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Arial", Font.BOLD, 12));
		toastPanel.add(label);
		
		JWindow toast = new JWindow();
		toast.getContentPane().add(toastPanel);
		toast.pack();
		
		// Posición inferior centrada
		Point loc = getLocation();
		toast.setLocation(
			loc.x + (getWidth() - toast.getWidth()) / 2,
			loc.y + getHeight() - 100
		);
		
		toast.setVisible(true);
		
		// Auto-cierre después de 2 segundos
		new Timer(2000, e -> {
			toast.dispose();
		}).start();
	}
}