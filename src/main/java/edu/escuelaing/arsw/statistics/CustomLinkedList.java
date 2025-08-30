package edu.escuelaing.arsw.statistics;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Custom implementation of a LinkedList that is compliant with Java's Collections API.
 * This implementation uses a doubly-linked list structure with head and tail pointers.
 * 
 * @param <E> the type of elements held in this collection
 * @author Diego Cardenas
 * @version 1.0
 */
public class CustomLinkedList<E> implements List<E> {
    
    /**
     * Node class representing each element in the linked list
     * @param <E> the type of data stored in the node
     */
    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;
        
        Node(E data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
        
        Node(E data, Node<E> prev, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }
    
    private Node<E> head;
    private Node<E> tail;
    private int size;
    private int modCount = 0;
    
    /**
     * Constructs an empty list.
     */
    public CustomLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    /**
     * Constructs a list containing the elements of the specified collection.
     * @param c the collection whose elements are to be placed into this list
     */
    public CustomLinkedList(Collection<? extends E> c) {
        this();
        addAll(c);
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }
    
    @Override
    public Iterator<E> iterator() {
        return new CustomListIterator(0);
    }
    
    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Node<E> x = head; x != null; x = x.next) {
            result[i++] = x.data;
        }
        return result;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), size);
        }
        int i = 0;
        Object[] result = a;
        for (Node<E> x = head; x != null; x = x.next) {
            result[i++] = x.data;
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }
    
    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }
    
    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> x = head; x != null; x = x.next) {
                if (x.data == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<E> x = head; x != null; x = x.next) {
                if (o.equals(x.data)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }
    
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkPositionIndex(index);
        
        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew == 0) {
            return false;
        }
        
        Node<E> pred, succ;
        if (index == size) {
            succ = null;
            pred = tail;
        } else {
            succ = node(index);
            pred = succ.prev;
        }
        
        for (Object o : a) {
            @SuppressWarnings("unchecked") E e = (E) o;
            Node<E> newNode = new Node<>(e, pred, null);
            if (pred == null) {
                head = newNode;
            } else {
                pred.next = newNode;
            }
            pred = newNode;
        }
        
        if (succ == null) {
            tail = pred;
        } else {
            pred.next = succ;
            succ.prev = pred;
        }
        
        size += numNew;
        modCount++;
        return true;
    }
    
    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<?> it = iterator();
        while (it.hasNext()) {
            if (c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    @Override
    public void clear() {
        for (Node<E> x = head; x != null; ) {
            Node<E> next = x.next;
            x.data = null;
            x.next = null;
            x.prev = null;
            x = next;
        }
        head = tail = null;
        size = 0;
        modCount++;
    }
    
    @Override
    public E get(int index) {
        checkElementIndex(index);
        return node(index).data;
    }
    
    @Override
    public E set(int index, E element) {
        checkElementIndex(index);
        Node<E> x = node(index);
        E oldVal = x.data;
        x.data = element;
        return oldVal;
    }
    
    @Override
    public void add(int index, E element) {
        checkPositionIndex(index);
        
        if (index == size) {
            addLast(element);
        } else {
            addBefore(element, node(index));
        }
    }
    
    @Override
    public E remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }
    
    @Override
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (Node<E> x = head; x != null; x = x.next) {
                if (x.data == null) {
                    return index;
                }
                index++;
            }
        } else {
            for (Node<E> x = head; x != null; x = x.next) {
                if (o.equals(x.data)) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }
    
    @Override
    public int lastIndexOf(Object o) {
        int index = size;
        if (o == null) {
            for (Node<E> x = tail; x != null; x = x.prev) {
                index--;
                if (x.data == null) {
                    return index;
                }
            }
        } else {
            for (Node<E> x = tail; x != null; x = x.prev) {
                index--;
                if (o.equals(x.data)) {
                    return index;
                }
            }
        }
        return -1;
    }
    
    @Override
    public ListIterator<E> listIterator() {
        return new CustomListIterator(0);
    }
    
    @Override
    public ListIterator<E> listIterator(int index) {
        checkPositionIndex(index);
        return new CustomListIterator(index);
    }
    
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        
        List<E> subList = new CustomLinkedList<>();
        Node<E> current = node(fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            subList.add(current.data);
            current = current.next;
        }
        return subList;
    }
    
    /**
     * Adds the specified element as the tail (last element) of this list.
     * @param e the element to add
     */
    public void addLast(E e) {
        final Node<E> l = tail;
        final Node<E> newNode = new Node<>(e, l, null);
        tail = newNode;
        if (l == null) {
            head = newNode;
        } else {
            l.next = newNode;
        }
        size++;
        modCount++;
    }
    
    /**
     * Adds the specified element as the head (first element) of this list.
     * @param e the element to add
     */
    public void addFirst(E e) {
        final Node<E> f = head;
        final Node<E> newNode = new Node<>(e, null, f);
        head = newNode;
        if (f == null) {
            tail = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
        modCount++;
    }
    
    /**
     * Returns the node at the specified element index.
     */
    private Node<E> node(int index) {
        if (index < (size >> 1)) {
            Node<E> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<E> x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }
    
    /**
     * Inserts element e before non-null Node succ.
     */
    private void addBefore(E e, Node<E> succ) {
        final Node<E> pred = succ.prev;
        final Node<E> newNode = new Node<>(e, pred, succ);
        succ.prev = newNode;
        if (pred == null) {
            head = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
        modCount++;
    }
    
    /**
     * Unlinks non-null node x.
     */
    private E unlink(Node<E> x) {
        final E element = x.data;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;
        
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            x.prev = null;
        }
        
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }
        
        x.data = null;
        size--;
        modCount++;
        return element;
    }
    
    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
    
    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
    
    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }
    
    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }
    
    /**
     * Custom ListIterator implementation
     */
    private class CustomListIterator implements ListIterator<E> {
        private Node<E> lastReturned;
        private Node<E> next;
        private int nextIndex;
        private int expectedModCount = modCount;
        
        CustomListIterator(int index) {
            next = (index == size) ? null : node(index);
            nextIndex = index;
        }
        
        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }
        
        @Override
        public E next() {
            checkForComodification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            
            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.data;
        }
        
        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }
        
        @Override
        public E previous() {
            checkForComodification();
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            
            lastReturned = next = (next == null) ? tail : next.prev;
            nextIndex--;
            return lastReturned.data;
        }
        
        @Override
        public int nextIndex() {
            return nextIndex;
        }
        
        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }
        
        @Override
        public void remove() {
            checkForComodification();
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            
            Node<E> lastNext = lastReturned.next;
            unlink(lastReturned);
            if (next == lastReturned) {
                next = lastNext;
            } else {
                nextIndex--;
            }
            lastReturned = null;
            expectedModCount++;
        }
        
        @Override
        public void set(E e) {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            checkForComodification();
            lastReturned.data = e;
        }
        
        @Override
        public void add(E e) {
            checkForComodification();
            lastReturned = null;
            if (next == null) {
                addLast(e);
            } else {
                addBefore(e, next);
            }
            nextIndex++;
            expectedModCount++;
        }
        
        final void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}