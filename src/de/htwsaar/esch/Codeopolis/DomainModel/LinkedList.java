package de.htwsaar.esch.Codeopolis.DomainModel;

public class LinkedList <E> {

    protected static class Node<E> {
        E element;
        Node<E> next;

        Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

        Node(E element) {
            this.element = element;
            this.next = null;
        }
    }

    Node<E> head;

    public LinkedList() {
        this.head = null;
    }

    private Node<E> getLast() {
        Node<E> result = head;
        while (result.next != null) {
            result = result.next;
        }
        return result;
    }

    public void addLast(E element) {
        Node<E> last = getLast();
        Node<E> newLast = new Node<>(element);
        if (last!=null) {
            last.next = newLast;
        }
        else {
            head = newLast;
        }
    }

    public E removeFirst() {
        Node<E> result = head;
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
        Node<E> node = head;
        while (node != null) {
            ++count;
            node = node.next;
        }
        return count;
    }

    public  void clear() {
        head = null;
    }
}
