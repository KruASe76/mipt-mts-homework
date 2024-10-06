package me.kruase.mipt.customarraylist;


import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;


/**
 * Naive implementation of {@link java.util.ArrayList}
 *
 * @param <E> {@inheritDoc}
 */
public class CustomArrayList<E> implements CustomList<E> {
    /**
     * Default initial capacity
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Current actual list size
     */
    private int size;

    /**
     * Internal array of elements
     */
    private Object[] elements;


    /**
     * Constructs an empty list with specified initial capacity
     *
     * @param initialCapacity initial internal array capacity
     */
    public CustomArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }

        this.elements = new Object[initialCapacity];
    }

    /**
     * Constructs an empty list with default initial capacity
     */
    public CustomArrayList() {
        this(DEFAULT_CAPACITY);
    }


    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * {@inheritDoc}
     *
     * @param element {@inheritDoc}
     */
    @Override
    public void add(@NotNull E element) {
        add(size, element);
    }

    /**
     * {@inheritDoc}
     *
     * @param index   {@inheritDoc}
     * @param element {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void add(int index, @NotNull E element) throws IndexOutOfBoundsException {
        Objects.checkIndex(index, size + 1);

        if (size + 1 > elements.length) {
            grow();
        }

        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }

        elements[index] = element;
        size++;
    }

    /**
     * {@inheritDoc}
     *
     * @param index {@inheritDoc}
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public @NotNull E get(int index) throws IndexOutOfBoundsException {
        Objects.checkIndex(index, size);
        return (E) elements[index];
    }

    /**
     * {@inheritDoc}
     *
     * @param index {@inheritDoc}
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public @NotNull E remove(int index) throws IndexOutOfBoundsException {
        E removedElement = get(index);

        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }

        size--;
        return removedElement;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public E[] toArray() {
        return (E[]) Arrays.copyOfRange(elements, 0, size);
    }

    /**
     * Expands the internal array to twice its initial size
     * (so appending an element has constant amortized complexity)
     */
    private void grow() {
        elements = Arrays.copyOf(elements, elements.length * 2);
    }

    /**
     * Calculates hash based on list elements
     *
     * @return hash based on list elements
     */
    @Override
    public int hashCode() {
        return Objects.hash(Arrays.copyOfRange(elements, 0, size));
    }

    /**
     * Compares the specified object with the list element-wise
     *
     * @param obj and object to compare the list with
     * @return {@code true} if object is an instance of {@link CustomList} interface
     * and their elements are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CustomList<?> otherList)) {
            return false;
        }

        if (size != otherList.size()) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (!Objects.equals(elements[i], otherList.get(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return Arrays.toString(toArray());
    }
}
