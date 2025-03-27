package com.swingy.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MapPanel extends JPanel {
	private int[][] map; // Matriz del mapa

	public void setMap(int[][] map) {
        this.map = map;
        repaint(); // Redibujar el panel cuando se actualiza el mapa
    }

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (map == null) return;

		int cellSize = 80;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 0) {
					g.setColor(Color.WHITE);
				} else if (map[i][j] == 1) {
					g.setColor(Color.RED);
				} else if (map[i][j] == 2) {
					g.setColor(Color.BLUE);
				}
				
				// Dibujar la celda
				g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
				g.setColor(Color.BLACK);
				g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
			}
		}
	}

	public void updateMap(int[][] newMap) {
		this.map = newMap;
		repaint();
	}
}
