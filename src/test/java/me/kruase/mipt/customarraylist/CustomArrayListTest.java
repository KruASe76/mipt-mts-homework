package me.kruase.mipt.customarraylist;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CustomArrayListTest {
    static final int FIRST_NUMBER = 42;
    static final int SECOND_NUMBER = 69;
    static final int THIRD_NUMBER = 228;

    CustomArrayList<Integer> customArrayList;

    void addThreeNumbers() {
        customArrayList.add(FIRST_NUMBER);
        customArrayList.add(SECOND_NUMBER);
        customArrayList.add(THIRD_NUMBER);
    }

    void addThreeNumbers(CustomArrayList<Integer> customArrayList) {
        customArrayList.add(FIRST_NUMBER);
        customArrayList.add(SECOND_NUMBER);
        customArrayList.add(THIRD_NUMBER);
    }

    @BeforeEach
    void setUp() {
        customArrayList = new CustomArrayList<>();
    }

    @Test
    void testSize() {
        addThreeNumbers();

        assertEquals(3, customArrayList.size());

        customArrayList.remove(0);

        assertEquals(2, customArrayList.size());
    }

    @Test
    void testIsEmpty() {
        assertTrue(customArrayList.isEmpty());

        customArrayList.add(FIRST_NUMBER);

        assertFalse(customArrayList.isEmpty());

        customArrayList.remove(0);

        assertTrue(customArrayList.isEmpty());
    }

    @Test
    void testAdd() {
        addThreeNumbers();

        assertEquals(FIRST_NUMBER, customArrayList.get(0));
        assertEquals(SECOND_NUMBER, customArrayList.get(1));
        assertEquals(THIRD_NUMBER, customArrayList.get(2));
    }

    @Test
    void testGet() {
        testAdd();

        customArrayList.remove(0);

        assertEquals(SECOND_NUMBER, customArrayList.get(0));
        assertEquals(THIRD_NUMBER, customArrayList.get(1));
    }

    @Test
    void testGetTrows() {
        addThreeNumbers();

        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.get(3));
    }

    @Test
    void testRemove() {
        addThreeNumbers();

        assertEquals(FIRST_NUMBER, customArrayList.remove(0));
        assertEquals(THIRD_NUMBER, customArrayList.remove(1));
        assertEquals(SECOND_NUMBER, customArrayList.remove(0));

        assertTrue(customArrayList.isEmpty());
    }

    @Test
    void testRemoveThrows() {
        addThreeNumbers();

        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> customArrayList.remove(3));
    }

    @Test
    void testToArray() {
        addThreeNumbers();

        Integer[] expected = new Integer[]{FIRST_NUMBER, SECOND_NUMBER, THIRD_NUMBER};

        assertArrayEquals(expected, customArrayList.toArray());
    }

    @Test
    void testHashCode() {
        addThreeNumbers();

        CustomArrayList<Integer> anotherCustomArrayList = new CustomArrayList<>();

        assertNotEquals(customArrayList.hashCode(), anotherCustomArrayList.hashCode());

        addThreeNumbers(anotherCustomArrayList);

        assertEquals(customArrayList.hashCode(), anotherCustomArrayList.hashCode());
    }

    @Test
    void testEquals() {
        addThreeNumbers();

        CustomArrayList<Integer> anotherCustomArrayList = new CustomArrayList<>();

        assertNotEquals(customArrayList, anotherCustomArrayList);

        addThreeNumbers(anotherCustomArrayList);

        assertEquals(customArrayList, anotherCustomArrayList);
    }

    @Test
    void testToString() {
        addThreeNumbers();

        String expected = "[" + FIRST_NUMBER + ", " + SECOND_NUMBER + ", " + THIRD_NUMBER + "]";

        assertEquals(expected, customArrayList.toString());

        customArrayList.remove(1);

        expected = "[" + FIRST_NUMBER + ", " + THIRD_NUMBER + "]";

        assertEquals(expected, customArrayList.toString());
    }

    @Test
    void testGrow() {
        int repetitionCount = 1000;

        for (int i = 0; i < repetitionCount; i++) {
            int finalI = i;
            assertDoesNotThrow(() -> customArrayList.add(finalI));
        }

        assertEquals(repetitionCount, customArrayList.size());
        assertEquals(FIRST_NUMBER, customArrayList.get(FIRST_NUMBER));

        // removing elements at odd indices (all odd elements)
        for (int i = 1; i < customArrayList.size(); i++) {
            customArrayList.remove(i);
        }

        assertEquals(repetitionCount / 2, customArrayList.size());
        assertEquals(FIRST_NUMBER * 2, customArrayList.get(FIRST_NUMBER));
    }
}
