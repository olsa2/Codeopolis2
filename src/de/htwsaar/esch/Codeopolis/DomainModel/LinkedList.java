package de.htwsaar.esch.Codeopolis.DomainModel;

public class LinkedList <T extends Comparable<T>> {

    private static class Node<T> {
        T element;
        Node<T> next;

        Node(T element, Node<T> next) {
            this.element = element;
            this.next = next;
        }

        Node(T element) {
            this(element, null);
        }
    }

    private class Iterator implements java.util.Iterator<T> {
        Node<T> current;
        boolean started=false;
        boolean finished=false;

        @Override
        public boolean hasNext() {
            if (finished) {
                return false;
            }

            if (!started) {
                started=true;
                current = head;
            }
            else {
                if (current != null) {
                    current = current.next;
                }
            }
            if (current == null) {
                finished=true;
                return false;
            }
            return true;
        }

        @Override
        public T next() {
            return current!=null?current.element:null;
        }
    }

    Node<T> head;

    public LinkedList() {
    }

    public java.util.Iterator<T> iterator() {
        return new Iterator();
    }

    private Node<T> getLast() {
        Node<T> result = head;
        while (result.next != null) {
            result = result.next;
        }
        return result;
    }

    public void addLast(T element) {
        Node<T> last = getLast();
        Node<T> newLast = new Node<>(element);
        if (last!=null) {
            last.next = newLast;
        }
        else {
            head = newLast;
        }
    }

    public T removeFirst() {
        Node<T> node = head;
        if (node!= null) {
            head = node.next;
            return node.element;
        }
        return null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        int count = 0;
        Node<T> node = head;
        while (node != null) {
            ++count;
            node = node.next;
        }
        return count;
    }

    public  void clear() {
        head = null;
    }

    public T get(int index) {
        Node<T> node = head;
        while (index-- != 0 && node != null) {
            node = node.next;
        }
        return node!=null ? node.element: null;
    }

    public T set(int index, T element) {
        Node<T> node = head;
        while (index-- != 0 && node != null) {
            node = node.next;
        }
        if (node!=null) {
            T prev = node.element;
            node.element = element;
            return prev;
        }
        return null;
    }

    public T remove(int index) {
        if (index == 0) {
            return removeFirst();
        }

        Node<T> prev = head;
        index--;

        while (index-- != 0 && prev != null) {
            prev = prev.next;
        }

        if (prev==null) {
            return null;
        }

        if (prev.next == null) {
            return null;
        }

        T element = prev.next.element;
        prev.next = null;

        return element;
    }

    public  void sort() {
        if (head==null) {
            return;
        }

        int size = size() - 1;

        while (size-- > 0) {
            boolean swapped = false;
            for(int i=0; i < size; i++) {
                T e1 = get(i);
                T e2 = get(i+1);
                if (e1.compareTo(e2) > 0) {
                    set(i,e2);
                    set(i+1,e1);
                    swapped = true;
                }
            }
            if(!swapped) {
                break;
            }
        }

    }

}
