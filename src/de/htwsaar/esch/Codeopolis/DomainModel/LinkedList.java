package de.htwsaar.esch.Codeopolis.DomainModel;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class LinkedList<T extends Comparable<T>> {

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
		Node<T> current = null, previous = null;
		boolean started = false;
		boolean finished = false;

		public void remove() {
			if (current == null) {
				throw new IllegalStateException("");
			}
			if (current == head) {
				current = head.next;
			} else {
				current = current.next;
				previous.next = current;
			}
			if (current == null) {
				finished = true;
			}
		}

		@Override
		public boolean hasNext() {
			if (finished) {
				return false;
			}

			if (!started) {
				started = true;
				current = head;
			} else {
				if (current != null) {
					previous = current;
					current = current.next;
				}
			}
			if (current == null) {
				finished = true;
				return false;
			}
			return true;
		}

		@Override
		public T next() {
			return current != null ? current.element : null;
		}
	}

	Node<T> head;

	public LinkedList() {
	}

	public java.util.Iterator<T> iterator() {
		return new Iterator();
	}

	private Node<T> getLast() {
		if (head == null) {
			return null;
		}

		Node<T> result = head;
		while (result.next != null) {
			result = result.next;
		}
		return result;
	}

	public void addLast(T element) {
		Node<T> last = getLast();
		Node<T> newLast = new Node<>(element);
		if (last != null) {
			last.next = newLast;
		} else {
			head = newLast;
		}
	}

	public T removeFirst() {
		Node<T> node = head;
		if (node != null) {
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

	public void clear() {
		head = null;
	}

	public T get(int index) {
		Node<T> node = head;
		while (index > 0 && node != null) {
			node = node.next;
			--index;
		}
		return node != null ? node.element : null;
	}

	public T set(int index, T element) {
		Node<T> node = head;
		while (index != 0 && node != null) {
			node = node.next;
			--index;
		}
		if (node != null) {
			T oldValue = node.element;
			node.element = element;
			return oldValue;
		}
		return null;
	}

	public T remove(int index) {
		if (index == 0) {
			return removeFirst();
		}

		Node<T> prev = head;
		index--;

		while (index != 0 && prev != null) {
			prev = prev.next;
			--index;
		}

		if (prev == null || prev.next == null) {
			return null;
		}

		Node<T> removed = prev.next;
		prev.next = removed.next;
		removed.next = null;
		return removed.element;
	}

	public void sort() {
		if (head == null || head.next == null) { // nothing to sort
			return;
		}

		int size = size() - 1;

		while (size-- > 0) {
			boolean swapped = false;
			for (int i = 0; i < size; i++) {
				T e1 = get(i);
				T e2 = get(i + 1);
				if (e1.compareTo(e2) > 0) {
					set(i, e2);
					set(i + 1, e1);
					swapped = true;
				}
			}
			if (!swapped) {
				break;
			}
		}

	}
	
/*---------------- Aufgabe 6 ------------------------------*/
	
	public LinkedList<T> filter(Predicate<T> predicate) {
		LinkedList<T> filtered = new LinkedList<>();
		java.util.Iterator<T> iter = iterator();
		while (iter.hasNext()) {
			T e = iter.next();
			if (predicate.test(e)) {
				filtered.addLast(e);
			}
		}
		return filtered;
	}

	public void forEach(Consumer<T> consumer) {
		java.util.Iterator<T> iter = iterator();
		while (iter.hasNext()) {
			consumer.accept(iter.next());
		}
	}

	public int removeIf(Predicate<T> predicate) {
		int removed = 0;
		java.util.Iterator<T> iter = iterator();
		while (iter.hasNext()) {
			T e = iter.next();
			if (predicate.test(e)) {
				iter.remove();
				++removed;
			}
		}
		return removed;
	}

	public boolean addIf(T e, Predicate<T> predicate) {
		if (predicate.equals(e)) {
			addLast(e);
			return true;
		}
		return false;
	}

	public void sort(Comparator comparator) {
		if (head == null || head.next == null) { // nothing to sort
			return;
		}

		int size = size() - 1;

		while (size-- > 0) {
			boolean swapped = false;
			for (int i = 0; i < size; i++) {
				T e1 = get(i);
				T e2 = get(i + 1);
				if (comparator.compare(e1, e2) > 0) {
					set(i, e2);
					set(i + 1, e1);
					swapped = true;
				}
			}
			if (!swapped) {
				break;
			}
		}

	}

	public double sum(Function<T, Double> f) {
		double result = 0;
		java.util.Iterator<T> iter = iterator();
		while (iter.hasNext()) {
			T e = iter.next();
	 		result += f.apply(e);
		}
		return result;
	}
}
