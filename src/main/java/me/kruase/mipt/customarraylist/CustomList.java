package me.kruase.mipt.customarraylist;


import org.jetbrains.annotations.NotNull;


/**
 * Interface for a naive implementation of {@link java.util.ArrayList}.
 *
 * @param <E> type of elements in this list
 */
public interface CustomList<E> {
    /**
     * Returns the number of elements in this list.
     *
     * @return number of elements in this list
     */
    int size();

    /**
     * Checks if the list is empty
     *
     * @return {@code true} if array contains no elements
     */
    boolean isEmpty();

    /**
     * Appends the specified element to the end of this list
     *
     * @param element element to be added
     */
    void add(@NotNull E element);

    /**
     * Inserts the specified at the specified position in this list.
     * Shifts the element currently at that position (if any) and any
     * subsequent elements to the right (adds one to their indices).
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size() + 1})
     */
    void add(int index, @NotNull E element) throws IndexOutOfBoundsException;

    /**
     * Removes and returns the element at the specified position in this list.
     *
     * @param index index of the element to be removed
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index  0 || index >= size()})
     */
    @NotNull E remove(int index) throws IndexOutOfBoundsException;

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index  0 || index >= size()})
     */
    @NotNull E get(int index) throws IndexOutOfBoundsException;

    /**
     * Returns elements of this list as an array
     *
     * @return elements of this list as an array
     */
    @NotNull E[] toArray();
}
