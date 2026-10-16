package com.files;

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The remaining items of a {@link ListIterator}, requested one page at a time as iteration reaches
 * them. See {@link ListIterator#listAutoPaging()}.
 *
 * @param <T> the type of the listed items
 */
public class ListIteratorIterable<T> implements Iterable<T> {
  private final ListIterator<T> listIterator;

  public ListIteratorIterable(ListIterator<T> listIterator) {
    this.listIterator = listIterator;
  }

  @Override
  public Iterator<T> iterator() {
    return new RemainingItems();
  }

  private class RemainingItems implements Iterator<T> {
    private Iterator<T> pageItems = Collections.emptyIterator();
    private int expectedModCount = listIterator.modCount;

    @Override
    public boolean hasNext() {
      if (listIterator.modCount != expectedModCount) {
        throw new ConcurrentModificationException("The listing was reset or advanced outside this iterator");
      }
      // Pages can be empty or short without being the last one, so keep loading until an item
      // appears or the listing has no more pages.
      while (!pageItems.hasNext() && listIterator.hasMorePages()) {
        pageItems = listIterator.loadNextPage().iterator();
        expectedModCount = listIterator.modCount;
      }
      return pageItems.hasNext();
    }

    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException("There are no more items in the list.");
      }
      return pageItems.next();
    }
  }
}
