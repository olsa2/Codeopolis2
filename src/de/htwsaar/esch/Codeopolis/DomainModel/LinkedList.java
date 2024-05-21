package de.htwsaar.esch.Codeopolis.DomainModel;

public class LinkedList <T> {

    private  class Node<T> {
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

    Node<T> head;

    public LinkedList() {
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
        Node<T> result = head;
        if (result!= null) {
            head = result.next;
            return result.element;
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
        Node<T> node = head;
        Node<T> prev = null;
        while (index-- != 0 && node != null) {
            prev = node;
            node = node.next;
        }
        if (prev==null || node==null) {
            return null;
        }
        prev.next = null;
        return node.element;
    }

}
