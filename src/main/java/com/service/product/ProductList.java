package com.service.product;

import com.model.product.AbstractProduct;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ProductList<E extends AbstractProduct> implements Iterable<E> {
    private final Set<Integer> versions;
    private Node<E> first;
    private Node<E> last;
    private int size;

    public ProductList() {
        size = 0;
        versions = new HashSet<>();
    }

    public void addToHead(E product, int version) {
        final Node<E> firstNode = first;
        final Node<E> newNode = new Node<>(product, version, LocalDateTime.now(), first, null);
        first = newNode;
        if (firstNode == null) {
            last = newNode;
        } else {
            firstNode.previous = newNode;
        }
        size++;
        versions.add(newNode.version);
    }

    public E findByVersion(int version) {
        if (validateVersion(version)) {
            for (Node<E> eNode = first; eNode != null; eNode = eNode.next) {
                if (eNode.version == version) {
                    return eNode.product;
                }
            }
        }
        throw new NoSuchElementException("There are no products in this version");
    }

    public boolean deleteByVersion(int version) {
        if (validateVersion(version)) {
            for (Node<E> eNode = first; eNode != null; eNode = eNode.next) {
                if (eNode.version == version) {
                    unlink(eNode);
                    return true;
                }
            }
        }
        return false;
    }

    public LocalDateTime getFirstVersionDate() {
        if (last == null) {
            throw new NoSuchElementException("There are no first version date");
        }
        return last.date;
    }

    public LocalDateTime getLastVersionDate() {
        if (first == null) {
            throw new NoSuchElementException("There are no last version date");
        }
        return first.date;
    }

    public boolean setByVersion(int version, E product) {
        if (validateVersion(version)) {
            for (Node<E> eNode = first; eNode != null; eNode = eNode.next) {
                if (eNode.version == version) {
                    eNode.product = product;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean validateVersion(int version) {
        return versions.contains(version);
    }

    public int getVersionCount() {
        return versions.size();
    }

    private E unlink(Node<E> eNode) {
        final E product = eNode.product;
        final Node<E> next = eNode.next;
        final Node<E> previous = eNode.previous;
        if (previous == null) {
            first = next;
        } else {
            previous.next = next;
            eNode.previous = null;
        }
        if (next == null) {
            last = previous;
        } else {
            next.previous = previous;
            eNode.next = null;
        }
        eNode.product = null;
        versions.remove(eNode.version);
        size--;
        return product;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }


    @Override
    public void forEach(Consumer<? super E> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<E> spliterator() {
        return Iterable.super.spliterator();
    }

    private class LinkedListIterator implements Iterator<E> {
        private Node<E> next;
        private int index;

        public LinkedListIterator() {
            index = 0;
            next = first;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public E next() {
            if (hasNext()) {
                Node<E> eNode = next;
                next = next.next;
                index++;
                return eNode.product;
            }
            throw new NoSuchElementException("");
        }
    }

    private static class Node<T> {
        private final int version;
        private final LocalDateTime date;
        private T product;
        private Node<T> next;
        private Node<T> previous;

        public Node(T product, int version, LocalDateTime date, Node<T> next, Node<T> previous) {
            this.product = product;
            this.version = version;
            this.date = date;
            this.next = next;
            this.previous = previous;
        }
    }
}
