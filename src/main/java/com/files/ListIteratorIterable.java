package com.files;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListIteratorIterable<T> implements Iterable<T> {
  private final ListIterator<T> listIterator;

  public ListIteratorIterable(ListIterator<T> listIterator) {
    this.listIterator = listIterator;
  }

  @Override
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      private int index = 0;
      private int page = 0;

      @Override
      public boolean hasNext() {
        return page == 0 || listIterator.hasNextPage() || this.index < listIterator.data.size() - 1;
      }

      @Override
      public T next() {
        this.index++;
        if (this.index >= listIterator.data.size()) {
          if (listIterator.hasNextPage() || this.page == 0) {
            listIterator.loadNextPage();
            this.index = 0;
            this.page++;
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
