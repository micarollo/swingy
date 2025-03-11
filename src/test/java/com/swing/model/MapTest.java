package com.swing.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.swingy.model.Map;

public class MapTest {

    private Map map;

    @BeforeEach
    void setUp() {
        map = new Map(5); // Un mapa 5x5
    }

    @Test
    void testMapInitialization() {
        assertEquals(5, map.getSize());
    }

    @Test
    void testGetCellValueWithinBounds() {
        assertDoesNotThrow(() -> map.getCell(2, 2));
    }

    @Test
    void testGetCellValueOutOfBounds() {
        assertThrows(IllegalArgumentException.class, () -> map.getCell(10, 10));
    }

    @Test
    void testSetCellValue() {
        map.setCell(3, 3, 1);
        assertEquals(1, map.getCell(3, 3));
    }

    @Test
    void testVillainsAreGenerated() {
        int countVillains = 0;
        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                if (map.getCell(i, j) == 1) {
                    countVillains++;
                }
            }
        }
        assertTrue(countVillains > 0);
    }
}
