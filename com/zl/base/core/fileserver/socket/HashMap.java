package com.zl.base.core.fileserver.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

public class HashMap extends AbstractMap implements Map, Cloneable,
		Serializable {
	static final int DEFAULT_INITIAL_CAPACITY = 16;
	static final int MAXIMUM_CAPACITY = 1073741824;
	static final float DEFAULT_LOAD_FACTOR = 0.75F;
	transient Entry[] table;
	transient int size;
	int threshold;
	final float loadFactor;
	volatile transient int modCount;
	static final Object NULL_KEY = new Object();
	private transient Set entrySet = null;
	private static final long serialVersionUID = 362498820763181265L;

	public HashMap(int paramInt, float paramFloat) {
		if (paramInt < 0)
			throw new IllegalArgumentException("Illegal initial capacity: "
					+ paramInt);
		if (paramInt > 1073741824)
			paramInt = 1073741824;
		if ((paramFloat <= 0.0F) || (Float.isNaN(paramFloat)))
			throw new IllegalArgumentException("Illegal load factor: "
					+ paramFloat);
		int i = 1;
		while (i < paramInt)
			i <<= 1;
		this.loadFactor = paramFloat;
		this.threshold = (int) (i * paramFloat);
		this.table = new Entry[i];
		init();
	}

	public HashMap(int paramInt) {
		this(paramInt, 0.75F);
	}

	public HashMap() {
		this.loadFactor = 0.75F;
		this.threshold = 16;
		this.table = new Entry[16];
		init();
	}

	public HashMap(Map paramMap) {
		this(Math.max((int) (paramMap.size() / 0.75F) + 1, 16), 0.75F);
		putAllForCreate(paramMap);
	}

	void init() {
	}

	static Object maskNull(Object paramObject) {
		return paramObject == null ? NULL_KEY : paramObject;
	}

	static Object unmaskNull(Object paramObject) {
		return paramObject == NULL_KEY ? null : paramObject;
	}

	static int hash(Object paramObject) {
		int i = paramObject.hashCode();
		i += (i << 9 ^ 0xFFFFFFFF);
		i ^= i >>> 14;
		i += (i << 4);
		i ^= i >>> 10;
		return i;
	}

	static boolean eq(Object paramObject1, Object paramObject2) {
		return (paramObject1 == paramObject2)
				|| (paramObject1.equals(paramObject2));
	}

	static int indexFor(int paramInt1, int paramInt2) {
		return paramInt1 & paramInt2 - 1;
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public Object get(Object obj) {
		Object obj1 = maskNull(obj);
		int i = hash(obj1);
		int j = indexFor(i, table.length);
		Entry entry = table[j];
		do {
			if (entry == null)
				return entry;
			if (entry.hash == i && eq(obj1, entry.key))
				return entry.value;
			entry = entry.next;
		} while (true);
	}

	// public Object get(Object paramObject)
	// {
	// Object localObject = maskNull(paramObject);
	// int i = hash(localObject);
	// int j = indexFor(i, this.table.length);
	// for (Entry localEntry = this.table[j]; ; localEntry = localEntry.next)
	// {
	// if (localEntry == null)
	// return localEntry;
	// if ((localEntry.hash == i) && (eq(localObject, localEntry.key)))
	// return localEntry.value;
	// }
	// }
	public boolean containsKey(Object obj) {
		Object obj1 = maskNull(obj);
		int i = hash(obj1);
		int j = indexFor(i, table.length);
		for (Entry entry = table[j]; entry != null; entry = entry.next)
			if (entry.hash == i && eq(obj1, entry.key))
				return true;

		return false;
	}

	// public boolean containsKey(Object paramObject)
	// {
	// Object localObject = maskNull(paramObject);
	// int i = hash(localObject);
	// int j = indexFor(i, this.table.length);
	// for (Entry localEntry = this.table[j]; localEntry != null; localEntry =
	// localEntry.next)
	// if ((localEntry.hash == i) && (eq(localObject, localEntry.key)))
	// return true;
	// return false;
	// }

	Entry getEntry(Object obj) {
		Object obj1 = maskNull(obj);
		int i = hash(obj1);
		int j = indexFor(i, table.length);
		Entry entry;
		for (entry = table[j]; entry != null
				&& (entry.hash != i || !eq(obj1, entry.key)); entry = entry.next)
			;
		return entry;
	}

	// Entry getEntry(Object paramObject)
	// {
	// Object localObject = maskNull(paramObject);
	// int i = hash(localObject);
	// int j = indexFor(i, this.table.length);
	// for (Entry localEntry = this.table[j]; (localEntry != null) &&
	// ((localEntry.hash != i) || (!eq(localObject, localEntry.key)));
	// localEntry = localEntry.next);
	// return localEntry;
	// }
	public Object put(Object obj, Object obj1) {
		Object obj2 = maskNull(obj);
		int i = hash(obj2);
		int j = indexFor(i, table.length);
		for (Entry entry = table[j]; entry != null; entry = entry.next)
			if (entry.hash == i && eq(obj2, entry.key)) {
				Object obj3 = entry.value;
				entry.value = obj1;
				entry.recordAccess(this);
				return obj3;
			}

		modCount++;
		addEntry(i, obj2, obj1, j);
		return null;
	}

	// public Object put(Object paramObject1, Object paramObject2)
	// {
	// Object localObject1 = maskNull(paramObject1);
	// int i = hash(localObject1);
	// int j = indexFor(i, this.table.length);
	// for (Entry localEntry = this.table[j]; localEntry != null; localEntry =
	// localEntry.next)
	// if ((localEntry.hash == i) && (eq(localObject1, localEntry.key)))
	// {
	// Object localObject2 = localEntry.value;
	// localEntry.value = paramObject2;
	// localEntry.recordAccess(this);
	// return localObject2;
	// }
	// this.modCount += 1;
	// addEntry(i, localObject1, paramObject2, j);
	// return null;
	// }
	private void putForCreate(Object obj, Object obj1) {
		Object obj2 = maskNull(obj);
		int i = hash(obj2);
		int j = indexFor(i, table.length);
		for (Entry entry = table[j]; entry != null; entry = entry.next)
			if (entry.hash == i && eq(obj2, entry.key)) {
				entry.value = obj1;
				return;
			}

		createEntry(i, obj2, obj1, j);
	}

	// private void putForCreate(Object paramObject1, Object paramObject2)
	// {
	// Object localObject = maskNull(paramObject1);
	// int i = hash(localObject);
	// int j = indexFor(i, this.table.length);
	// for (Entry localEntry = this.table[j]; localEntry != null; localEntry =
	// localEntry.next)
	// if ((localEntry.hash == i) && (eq(localObject, localEntry.key)))
	// {
	// localEntry.value = paramObject2;
	// return;
	// }
	// createEntry(i, localObject, paramObject2, j);
	// }

	void putAllForCreate(Map paramMap) {
		Iterator localIterator = paramMap.entrySet().iterator();
		while (localIterator.hasNext()) {
			Map.Entry localEntry = (Map.Entry) localIterator.next();
			putForCreate(localEntry.getKey(), localEntry.getValue());
		}
	}

	void resize(int paramInt) {
		Entry[] arrayOfEntry1 = this.table;
		int i = arrayOfEntry1.length;
		if ((this.size < this.threshold) || (i > paramInt))
			return;
		Entry[] arrayOfEntry2 = new Entry[paramInt];
		transfer(arrayOfEntry2);
		this.table = arrayOfEntry2;
		this.threshold = (int) (paramInt * this.loadFactor);
	}

	void transfer(Entry aentry[]) {
		Entry aentry1[] = table;
		int i = aentry.length;
		for (int j = 0; j < aentry1.length; j++) {
			Entry entry = aentry1[j];
			if (entry == null)
				continue;
			aentry1[j] = null;
			do {
				Entry entry1 = entry.next;
				int k = indexFor(entry.hash, i);
				entry.next = aentry[k];
				aentry[k] = entry;
				entry = entry1;
			} while (entry != null);
		}

	}

	// void transfer(Entry[] paramArrayOfEntry)
	// {
	// Entry[] arrayOfEntry = this.table;
	// int i = paramArrayOfEntry.length;
	// for (int j = 0; j < arrayOfEntry.length; j++)
	// {
	// Object localObject = arrayOfEntry[j];
	// if (localObject != null)
	// {
	// arrayOfEntry[j] = null;
	// do
	// {
	// Entry localEntry = ((Entry)localObject).next;
	// int k = indexFor(((Entry)localObject).hash, i);
	// ((Entry)localObject).next = paramArrayOfEntry[k];
	// paramArrayOfEntry[k] = localObject;
	// localObject = localEntry;
	// }
	// while (localObject != null);
	// }
	// }
	// }

	public void putAll(Map paramMap) {
		int i = paramMap.size();
		if (i == 0)
			return;
		if (i >= this.threshold) {
			i = (int) (i / this.loadFactor + 1.0F);
			if (i > 1073741824)
				i = 1073741824;
			int j = this.table.length;
			while (j < i)
				j <<= 1;
			resize(j);
		}
		Iterator localIterator = paramMap.entrySet().iterator();
		while (localIterator.hasNext()) {
			Map.Entry localEntry = (Map.Entry) localIterator.next();
			put(localEntry.getKey(), localEntry.getValue());
		}
	}

	public Object remove(Object paramObject) {
		Entry localEntry = removeEntryForKey(paramObject);
		return localEntry == null ? localEntry : localEntry.value;
	}

	Entry removeEntryForKey(Object obj) {
		Object obj1 = maskNull(obj);
		int i = hash(obj1);
		int j = indexFor(i, table.length);
		Entry entry = table[j];
		Entry entry1;
		Entry entry2;
		for (entry1 = entry; entry1 != null; entry1 = entry2) {
			entry2 = entry1.next;
			if (entry1.hash == i && eq(obj1, entry1.key)) {
				modCount++;
				size--;
				if (entry == entry1)
					table[j] = entry2;
				else
					entry.next = entry2;
				entry1.recordRemoval(this);
				return entry1;
			}
			entry = entry1;
		}

		return entry1;
	}

	// Entry removeEntryForKey(Object paramObject)
	// {
	// Object localObject1 = maskNull(paramObject);
	// int i = hash(localObject1);
	// int j = indexFor(i, this.table.length);
	// Object localObject2 = this.table[j];
	// Entry localEntry;
	// for (Object localObject3 = localObject2; localObject3 != null;
	// localObject3 = localEntry)
	// {
	// localEntry = localObject3.next;
	// if ((localObject3.hash == i) && (eq(localObject1, localObject3.key)))
	// {
	// this.modCount += 1;
	// this.size -= 1;
	// if (localObject2 == localObject3)
	// this.table[j] = localEntry;
	// else
	// ((Entry)localObject2).next = localEntry;
	// localObject3.recordRemoval(this);
	// return localObject3;
	// }
	// localObject2 = localObject3;
	// }
	// return localObject3;
	// }
	Entry removeMapping(Object obj) {
		if (!(obj instanceof java.util.Map.Entry))
			return null;
		java.util.Map.Entry entry = (java.util.Map.Entry) obj;
		Object obj1 = maskNull(entry.getKey());
		int i = hash(obj1);
		int j = indexFor(i, table.length);
		Entry entry1 = table[j];
		Entry entry2;
		Entry entry3;
		for (entry2 = entry1; entry2 != null; entry2 = entry3) {
			entry3 = entry2.next;
			if (entry2.hash == i && entry2.equals(entry)) {
				modCount++;
				size--;
				if (entry1 == entry2)
					table[j] = entry3;
				else
					entry1.next = entry3;
				entry2.recordRemoval(this);
				return entry2;
			}
			entry1 = entry2;
		}

		return entry2;
	}

	// Entry removeMapping(Object paramObject)
	// {
	// if (!(paramObject instanceof Map.Entry))
	// return null;
	// Map.Entry localEntry = (Map.Entry)paramObject;
	// Object localObject1 = maskNull(localEntry.getKey());
	// int i = hash(localObject1);
	// int j = indexFor(i, this.table.length);
	// Object localObject2 = this.table[j];
	// Entry localEntry1;
	// for (Object localObject3 = localObject2; localObject3 != null;
	// localObject3 = localEntry1)
	// {
	// localEntry1 = localObject3.next;
	// if ((localObject3.hash == i) && (localObject3.equals(localEntry)))
	// {
	// this.modCount += 1;
	// this.size -= 1;
	// if (localObject2 == localObject3)
	// this.table[j] = localEntry1;
	// else
	// ((Entry)localObject2).next = localEntry1;
	// localObject3.recordRemoval(this);
	// return localObject3;
	// }
	// localObject2 = localObject3;
	// }
	// return localObject3;
	// }

	public void clear() {
		this.modCount += 1;
		Entry[] arrayOfEntry = this.table;
		for (int i = 0; i < arrayOfEntry.length; i++)
			arrayOfEntry[i] = null;
		this.size = 0;
	}

	public boolean containsValue(Object paramObject) {
		if (paramObject == null)
			return containsNullValue();
		Entry[] arrayOfEntry = this.table;
		for (int i = 0; i < arrayOfEntry.length; i++)
			for (Entry localEntry = arrayOfEntry[i]; localEntry != null; localEntry = localEntry.next)
				if (paramObject.equals(localEntry.value))
					return true;
		return false;
	}

	private boolean containsNullValue() {
		Entry[] arrayOfEntry = this.table;
		for (int i = 0; i < arrayOfEntry.length; i++)
			for (Entry localEntry = arrayOfEntry[i]; localEntry != null; localEntry = localEntry.next)
				if (localEntry.value == null)
					return true;
		return false;
	}

	public Object clone() {
		HashMap localHashMap = null;
		try {
			localHashMap = (HashMap) super.clone();
		} catch (CloneNotSupportedException localCloneNotSupportedException) {
		}
		localHashMap.table = new Entry[this.table.length];
		localHashMap.entrySet = null;
		localHashMap.modCount = 0;
		localHashMap.size = 0;
		localHashMap.init();
		localHashMap.putAllForCreate(this);
		return localHashMap;
	}

	void addEntry(int i, Object obj, Object obj1, int j) {
		table[j] = new Entry(i, obj, obj1, table[j]);
		if (size++ >= threshold)
			resize(2 * table.length);
	}

	// void addEntry(int paramInt1, Object paramObject1, Object paramObject2,
	// int paramInt2)
	// {
	// this.table[paramInt2] = new Entry(paramInt1, paramObject1, paramObject2,
	// this.table[paramInt2]);
	// if (this.size++ >= this.threshold)
	// resize(2 * this.table.length);
	// }
	void createEntry(int i, Object obj, Object obj1, int j) {
		table[j] = new Entry(i, obj, obj1, table[j]);
		size++;
	}

	// void createEntry(int paramInt1, Object paramObject1, Object paramObject2,
	// int paramInt2)
	// {
	// this.table[paramInt2] = new Entry(paramInt1, paramObject1, paramObject2,
	// this.table[paramInt2]);
	// this.size += 1;
	// }

	Iterator newKeyIterator() {
		return new KeyIterator();
	}

	Iterator newValueIterator() {
		return new ValueIterator();
	}

	Iterator newEntryIterator() {
		return new EntryIterator();
	}

	// Iterator newKeyIterator()
	// {
	// return new KeyIterator(null);
	// }
	//
	// Iterator newValueIterator()
	// {
	// return new ValueIterator(null);
	// }
	//
	// Iterator newEntryIterator()
	// {
	// return new EntryIterator(null);
	// }
	public Set entrySet() {
		Set set = entrySet;
		return set == null ? (entrySet = new EntrySet()) : set;
	}

	// public Set entrySet()
	// {
	// Set localSet = this.entrySet;
	// return this.entrySet = new EntrySet(null);
	// }

	private void writeObject(ObjectOutputStream paramObjectOutputStream)
			throws IOException {
		paramObjectOutputStream.defaultWriteObject();
		paramObjectOutputStream.writeInt(this.table.length);
		paramObjectOutputStream.writeInt(this.size);
		Iterator localIterator = entrySet().iterator();
		while (localIterator.hasNext()) {
			Map.Entry localEntry = (Map.Entry) localIterator.next();
			paramObjectOutputStream.writeObject(localEntry.getKey());
			paramObjectOutputStream.writeObject(localEntry.getValue());
		}
	}

	private void readObject(ObjectInputStream paramObjectInputStream)
			throws IOException, ClassNotFoundException {
		paramObjectInputStream.defaultReadObject();
		int i = paramObjectInputStream.readInt();
		this.table = new Entry[i];
		init();
		int j = paramObjectInputStream.readInt();
		for (int k = 0; k < j; k++) {
			Object localObject1 = paramObjectInputStream.readObject();
			Object localObject2 = paramObjectInputStream.readObject();
			putForCreate(localObject1, localObject2);
		}
	}

	int capacity() {
		return this.table.length;
	}

	float loadFactor() {
		return this.loadFactor;
	}

	private class EntrySet extends AbstractSet {

		public Iterator iterator() {
			return newEntryIterator();
		}

		public boolean contains(Object obj) {
			if (!(obj instanceof java.util.Map.Entry)) {
				return false;
			} else {
				java.util.Map.Entry entry = (java.util.Map.Entry) obj;
				HashMap.Entry entry1 = getEntry(entry.getKey());
				return entry1 != null && entry1.equals(entry);
			}
		}

		public boolean remove(Object obj) {
			return removeMapping(obj) != null;
		}

		public int size() {
			return HashMap.this.size;
		}

		public void clear() {
			HashMap.this.clear();
		}

		private EntrySet() {
		}

	}

	// private class EntrySet extends AbstractSet
	// {
	// private EntrySet()
	// {
	// }
	//
	// public Iterator iterator()
	// {
	// return HashMap.this.newEntryIterator();
	// }
	//
	// public boolean contains(Object paramObject)
	// {
	// if (!(paramObject instanceof Map.Entry))
	// return false;
	// Map.Entry localEntry = (Map.Entry)paramObject;
	// HashMap.Entry localEntry1 = HashMap.this.getEntry(localEntry.getKey());
	// return (localEntry1 != null) && (localEntry1.equals(localEntry));
	// }
	//
	// public boolean remove(Object paramObject)
	// {
	// return HashMap.this.removeMapping(paramObject) != null;
	// }
	//
	// public int size()
	// {
	// return HashMap.this.size;
	// }
	//
	// public void clear()
	// {
	// HashMap.this.clear();
	// }
	//
	// EntrySet(HashMap.1 arg2)
	// {
	// this();
	// }
	// }

	private class Values extends AbstractCollection {
		private Values() {
		}

		public Iterator iterator() {
			return HashMap.this.newValueIterator();
		}

		public int size() {
			return HashMap.this.size;
		}

		public boolean contains(Object paramObject) {
			return HashMap.this.containsValue(paramObject);
		}

		public void clear() {
			HashMap.this.clear();
		}
	}

	private class KeySet extends AbstractSet {
		private KeySet() {
		}

		public Iterator iterator() {
			return HashMap.this.newKeyIterator();
		}

		public int size() {
			return HashMap.this.size;
		}

		public boolean contains(Object paramObject) {
			return HashMap.this.containsKey(paramObject);
		}

		public boolean remove(Object paramObject) {
			return HashMap.this.removeEntryForKey(paramObject) != null;
		}

		public void clear() {
			HashMap.this.clear();
		}
	}

	private class EntryIterator extends HashIterator {

		public Object next() {
			return nextEntry();
		}

		private EntryIterator() {
		}

	}

	// private class EntryIterator extends HashMap.HashIterator
	// {
	// private EntryIterator()
	// {
	// super();
	// }
	//
	// public Object next()
	// {
	// return nextEntry();
	// }
	//
	// EntryIterator(HashMap.1 arg2)
	// {
	// this();
	// }
	// }
	private class KeyIterator extends HashIterator {

		public Object next() {
			return nextEntry().getKey();
		}

		private KeyIterator() {
		}

	}

	// private class KeyIterator extends HashMap.HashIterator
	// {
	// private KeyIterator()
	// {
	// super();
	// }
	//
	// public Object next()
	// {
	// return nextEntry().getKey();
	// }
	//
	// KeyIterator(HashMap.1 arg2)
	// {
	// this();
	// }
	// }
	private class ValueIterator extends HashIterator {

		public Object next() {
			return nextEntry().value;
		}

		private ValueIterator() {
		}

	}

	// private class ValueIterator extends HashMap.HashIterator
	// {
	// private ValueIterator()
	// {
	// super();
	// }
	//
	// public Object next()
	// {
	// return nextEntry().value;
	// }
	//
	// ValueIterator(HashMap.1 arg2)
	// {
	// this();
	// }
	// }

	private abstract class HashIterator implements Iterator {
		HashMap.Entry next;
		int expectedModCount = HashMap.this.modCount;
		int index;
		HashMap.Entry current;

		HashIterator() {
			HashMap.Entry[] arrayOfEntry = HashMap.this.table;
			int i = arrayOfEntry.length;
			HashMap.Entry localEntry = null;
			while ((HashMap.this.size != 0) && (i > 0)
					&& ((localEntry = arrayOfEntry[(--i)]) == null))
				;
			this.next = localEntry;
			this.index = i;
		}

		public boolean hasNext() {
			return this.next != null;
		}

		HashMap.Entry nextEntry() {
			if (HashMap.this.modCount != this.expectedModCount)
				throw new ConcurrentModificationException();
			HashMap.Entry localEntry1 = this.next;
			if (localEntry1 == null)
				throw new NoSuchElementException();
			HashMap.Entry localEntry2 = localEntry1.next;
			HashMap.Entry[] arrayOfEntry = HashMap.this.table;
			int i = this.index;
			while ((localEntry2 == null) && (i > 0))
				localEntry2 = arrayOfEntry[(--i)];
			this.index = i;
			this.next = localEntry2;
			return this.current = localEntry1;
		}

		public void remove() {
			if (this.current == null)
				throw new IllegalStateException();
			if (HashMap.this.modCount != this.expectedModCount)
				throw new ConcurrentModificationException();
			Object localObject = this.current.key;
			this.current = null;
			HashMap.this.removeEntryForKey(localObject);
			this.expectedModCount = HashMap.this.modCount;
		}
	}

	static class Entry implements Map.Entry {
		final Object key;
		Object value;
		final int hash;
		Entry next;

		Entry(int paramInt, Object paramObject1, Object paramObject2,
				Entry paramEntry) {
			this.value = paramObject2;
			this.next = paramEntry;
			this.key = paramObject1;
			this.hash = paramInt;
		}

		public Object getKey() {
			return HashMap.unmaskNull(this.key);
		}

		public Object getValue() {
			return this.value;
		}

		public Object setValue(Object paramObject) {
			Object localObject = this.value;
			this.value = paramObject;
			return localObject;
		}

		public boolean equals(Object paramObject) {
			if (!(paramObject instanceof Map.Entry))
				return false;
			Map.Entry localEntry = (Map.Entry) paramObject;
			Object localObject1 = getKey();
			Object localObject2 = localEntry.getKey();
			if ((localObject1 == localObject2)
					|| ((localObject1 != null) && (localObject1
							.equals(localObject2)))) {
				Object localObject3 = getValue();
				Object localObject4 = localEntry.getValue();
				if ((localObject3 == localObject4)
						|| ((localObject3 != null) && (localObject3
								.equals(localObject4))))
					return true;
			}
			return false;
		}

		public int hashCode() {
			return (this.key == HashMap.NULL_KEY ? 0 : this.key.hashCode())
					^ (this.value == null ? 0 : this.value.hashCode());
		}

		public String toString() {
			return getKey() + "=" + getValue();
		}

		void recordAccess(HashMap paramHashMap) {
		}

		void recordRemoval(HashMap paramHashMap) {
		}
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.fileserver.socket.HashMap JD-Core Version: 0.6.1
 */