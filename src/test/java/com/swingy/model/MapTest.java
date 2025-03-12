package com.swingy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class MapTest {
    @Test
    public void testCalculateMap() {
        Map map = new Map(1); // Al nivel 1
        assertEquals(9, map.calculateMap(1));
        
        map = new Map(2); // Al nivel 2
        assertEquals(15, map.calculateMap(2));
    }

    @Test
    public void testCalculateVillains() {
        Map map = new Map(1);
        assertEquals(24, map.calculateVillains());

        map = new Map(2);
        assertEquals(67, map.calculateVillains());
    }
}