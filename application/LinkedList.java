package application;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements Iterable<T> {
private Node<T> first,last;
private int count=0;
public LinkedList() {
	
}
public LinkedList(LinkedList<T> list) {
	for (T element : list) {
        addLast(element);
    }
}
public T getFirst() {
	return first.getElement();
}
public T getLast() {
	return last.getElement();
}
public int getCount() {
	return count;
}
public void setCount(int count) {
	this.count = count;
}
public int size() {
	return count;
}
public void addFirst(T element) {
	if(first == null)
		first = last = new Node<>(element);
	else {
		Node<T> temp = new Node<>(element);
		temp.setNext(first);
		first = temp;
	}
	count ++;
}
public void set(int index, T element) {
    if (index < 0 || index >= count) {
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + count);
    }
    Node<T> current = first;
    for (int i = 0; i < index; i++) {
        current = current.getNext();
    }
    current.setElement(element);
}

public void addLast(T element) {
	Node<T> current = new Node<>(element);
	if(last ==null)
		last =first=current;
	else {
		last.setNext(current);
		last = last.getNext();
	}
	count ++;
}
public void add(T element,int index) {
	if(count ==0)
		first = last =new Node<>(element);
		if(index <= 0 || count < index)
			addLast(element);
		else {
			Node<T> current = first;
			for(int i=0;i<index-1;i++) {
				current = current.getNext();
			}
			Node<T> temp = new Node<>(element);
			temp.setNext(current.getNext());
			current.setNext(temp);
		}
		count ++;
	}
public boolean removeFirst() {
	if(count == 0)
		return false;
	if(count ==1)
		first=last=null;
	else {
		first=first.getNext();
		
	}
	count --;
	return true;
}
public boolean removeLast() {
	if(count == 0)
		return false;
	if(count ==1)
		first=last=null;
	else {
		Node<T> current =first;
		for(int i=0;i<count -1 ;i++) {
			current = current.getNext();
		last = current;
		//	current.next=null;
		last = current;
		}
	}
	count --;
	return true;
}
public boolean remove(int index) {
	Node<T> prev =first;
	if(count == 1)
		return removeFirst();
	if(index == count)
		return removeLast();
	if(index <=0 || index>count)
		return false;
	else {
		Node<T> current = first.getNext();
		for(int i=0;i<index;i++) {
			prev = current;
			current = current.getNext();
		}
		prev.setNext(current.getNext());
		//current.next=null;
		
	}
	count --;
	return true;
	
}
public boolean remove (Object element) {
	if(count ==0) 
		return false;
		if(element.equals(first.getElement()))
			return removeFirst();
		if(element.equals(last.getElement()))
			return removeLast();
		else {
			Node<T> current =first.getNext();
			for(int i=1;i<count-1;i++) {
			//	System.out.println(i+" "+current.getElement());
				if(current.getElement().equals(element))
					return remove(i);
				current = current.getNext();
			}
			count --;
			return false;
		}
}
public boolean contains (Object element) {
	Node<T> current = first;
	while (current != null) {
		if(((Node<T>)current).getElement().equals(element)) {
			return true;
		}
		current = ((Node<T>)current).getNext();
		
	}
	return false;
}
public void clear() {
	first = last = null;
	count = 0;
}
public T get(int index) {
	if(index < 0 || index >= count) {
		throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + count);
	}
	Node<T> current = first;
	for(int i = 0;i < index;i++) {
		current = ((Node<T>)current).getNext();
	}
	return ((Node<T>)current).getElement();
}
@Override
public Iterator<T> iterator() {
    return new LinkedListIterator();
}

private class LinkedListIterator implements Iterator<T> {
    private Node<T> current = first;

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        T element = current.getElement();
        current = current.getNext();
        return element;
    }
}
}
