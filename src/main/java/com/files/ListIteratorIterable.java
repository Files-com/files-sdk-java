package com.files;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListIteratorIterable<T> implements Iterable<T> {
  private final ListIterator<T> listIterator;

  public ListIteratorIterable(ListIterator<T> listIterator) {
    this.listIterator = listIterator;
    listIterator.loadNextPage();
  }

  @Override
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      private int index = -1;

      @Override
      public boolean hasNext() {
        return listIterator.hasNextPage() || this.index < listIterator.data.size() - 1;
      }

      @Override
      public T next() {
        this.index++;
        if (this.index >= listIterator.data.size()) {
          if (listIterator.hasNextPage()) {
            listIterator.loadNextPage();
            this.index = 0;
          } else {
            throw new NoSuchElementException("There are no more items in the list.");
          }
        }

        return listIterator.data.get(index);
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
}
