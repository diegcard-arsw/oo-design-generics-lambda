package edu.escuelaing.arsw.statistics;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * Unit tests for the CustomLinkedList class.
 * Tests compliance with Java's Collections API and LinkedList functionality.
 */
@DisplayName("Custom LinkedList Tests")
class CustomLinkedListTest {

    private CustomLinkedList<String> list;
    private CustomLinkedList<Integer> intList;

    @BeforeEach
    void setUp() {
        list = new CustomLinkedList<>();
        intList = new CustomLinkedList<>();
    }

    @Test
    @DisplayName("New list should be empty")
    void testNewListIsEmpty() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("Add elements and check size")
    void testAddElements() {
        list.add("first");
        list.add("second");
        list.add("third");

        assertEquals(3, list.size());
        assertFalse(list.isEmpty());
    }

    @Test
    @DisplayName("Get elements by index")
    void testGetElements() {
        list.add("first");
        list.add("second");
        list.add("third");

        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
        assertEquals("third", list.get(2));
    }

    @Test
    @DisplayName("Set elements by index")
    void testSetElements() {
        list.add("first");
        list.add("second");
        
        String oldValue = list.set(1, "modified");
        
        assertEquals("second", oldValue);
        assertEquals("modified", list.get(1));
    }

    @Test
    @DisplayName("Add elements at specific index")
    void testAddAtIndex() {
        list.add("first");
        list.add("third");
        list.add(1, "second");

        assertEquals(3, list.size());
        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
        assertEquals("third", list.get(2));
    }

    @Test
    @DisplayName("Remove elements by index")
    void testRemoveByIndex() {
        list.add("first");
        list.add("second");
        list.add("third");

        String removed = list.remove(1);

        assertEquals("second", removed);
        assertEquals(2, list.size());
        assertEquals("first", list.get(0));
        assertEquals("third", list.get(1));
    }

    @Test
    @DisplayName("Remove elements by object")
    void testRemoveByObject() {
        list.add("first");
        list.add("second");
        list.add("third");

        boolean removed = list.remove("second");

        assertTrue(removed);
        assertEquals(2, list.size());
        assertFalse(list.contains("second"));
    }

    @Test
    @DisplayName("Contains method works correctly")
    void testContains() {
        list.add("first");
        list.add("second");

        assertTrue(list.contains("first"));
        assertTrue(list.contains("second"));
        assertFalse(list.contains("third"));
    }

    @Test
    @DisplayName("IndexOf method works correctly")
    void testIndexOf() {
        list.add("first");
        list.add("second");
        list.add("first");

        assertEquals(0, list.indexOf("first"));
        assertEquals(1, list.indexOf("second"));
        assertEquals(-1, list.indexOf("notfound"));
    }

    @Test
    @DisplayName("LastIndexOf method works correctly")
    void testLastIndexOf() {
        list.add("first");
        list.add("second");
        list.add("first");

        assertEquals(2, list.lastIndexOf("first"));
        assertEquals(1, list.lastIndexOf("second"));
        assertEquals(-1, list.lastIndexOf("notfound"));
    }

    @Test
    @DisplayName("Clear method empties the list")
    void testClear() {
        list.add("first");
        list.add("second");
        list.clear();

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("Iterator works correctly")
    void testIterator() {
        list.add("first");
        list.add("second");
        list.add("third");

        List<String> collected = new ArrayList<>();
        for (String item : list) {
            collected.add(item);
        }

        assertEquals(Arrays.asList("first", "second", "third"), collected);
    }

    @Test
    @DisplayName("ListIterator works correctly")
    void testListIterator() {
        list.add("first");
        list.add("second");
        list.add("third");

        ListIterator<String> iterator = list.listIterator();
        
        assertTrue(iterator.hasNext());
        assertEquals("first", iterator.next());
        assertEquals("second", iterator.next());
        
        assertTrue(iterator.hasPrevious());
        assertEquals("second", iterator.previous());
    }

    @Test
    @DisplayName("ToArray method works correctly")
    void testToArray() {
        list.add("first");
        list.add("second");
        list.add("third");

        Object[] array = list.toArray();
        
        assertEquals(3, array.length);
        assertEquals("first", array[0]);
        assertEquals("second", array[1]);
        assertEquals("third", array[2]);
    }

    @Test
    @DisplayName("ToArray with provided array works correctly")
    void testToArrayWithProvidedArray() {
        list.add("first");
        list.add("second");

        String[] array = new String[3];
        String[] result = list.toArray(array);

        assertSame(array, result);
        assertEquals("first", result[0]);
        assertEquals("second", result[1]);
        assertNull(result[2]);
    }

    @Test
    @DisplayName("AddAll collection works correctly")
    void testAddAll() {
        List<String> toAdd = Arrays.asList("first", "second", "third");
        list.addAll(toAdd);

        assertEquals(3, list.size());
        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
        assertEquals("third", list.get(2));
    }

    @Test
    @DisplayName("Constructor with collection works correctly")
    void testConstructorWithCollection() {
        List<String> initial = Arrays.asList("first", "second", "third");
        CustomLinkedList<String> newList = new CustomLinkedList<>(initial);

        assertEquals(3, newList.size());
        assertEquals("first", newList.get(0));
        assertEquals("second", newList.get(1));
        assertEquals("third", newList.get(2));
    }

    @Test
    @DisplayName("SubList works correctly")
    void testSubList() {
        list.add("first");
        list.add("second");
        list.add("third");
        list.add("fourth");

        List<String> subList = list.subList(1, 3);
        
        assertEquals(2, subList.size());
        assertEquals("second", subList.get(0));
        assertEquals("third", subList.get(1));
    }

    @Test
    @DisplayName("Test with null values")
    void testWithNullValues() {
        list.add("first");
        list.add(null);
        list.add("third");

        assertEquals(3, list.size());
        assertNull(list.get(1));
        assertTrue(list.contains(null));
        assertEquals(1, list.indexOf(null));
    }

    @Test
    @DisplayName("Index out of bounds throws exception")
    void testIndexOutOfBounds() {
        list.add("first");
        
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(1, "test"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
    }

    @Test
    @DisplayName("Test with numeric data (Integer)")
    void testWithNumericData() {
        intList.add(10);
        intList.add(20);
        intList.add(30);

        assertEquals(3, intList.size());
        assertEquals((Integer) 10, intList.get(0));
        assertEquals((Integer) 20, intList.get(1));
        assertEquals((Integer) 30, intList.get(2));
    }

    @Test
    @DisplayName("AddFirst method works correctly")
    void testAddFirst() {
        list.add("second");
        list.add("third");
        
        // Test addFirst functionality through add(0, element)
        list.add(0, "first");

        assertEquals(3, list.size());
        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
        assertEquals("third", list.get(2));
    }

    @Test
    @DisplayName("RemoveAll method works correctly")
    void testRemoveAll() {
        list.add("keep1");
        list.add("remove1");
        list.add("keep2");
        list.add("remove2");

        Collection<String> toRemove = Arrays.asList("remove1", "remove2");
        boolean modified = list.removeAll(toRemove);

        assertTrue(modified);
        assertEquals(2, list.size());
        assertTrue(list.contains("keep1"));
        assertTrue(list.contains("keep2"));
        assertFalse(list.contains("remove1"));
        assertFalse(list.contains("remove2"));
    }

    @Test
    @DisplayName("RetainAll method works correctly")
    void testRetainAll() {
        list.add("keep1");
        list.add("remove1");
        list.add("keep2");
        list.add("remove2");

        Collection<String> toKeep = Arrays.asList("keep1", "keep2");
        boolean modified = list.retainAll(toKeep);

        assertTrue(modified);
        assertEquals(2, list.size());
        assertTrue(list.contains("keep1"));
        assertTrue(list.contains("keep2"));
        assertFalse(list.contains("remove1"));
        assertFalse(list.contains("remove2"));
    }
}