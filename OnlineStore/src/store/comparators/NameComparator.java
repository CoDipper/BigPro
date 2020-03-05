package store.comparators;

import store.extending.Item;

public class NameComparator extends ItemsComparator{
	@Override
	public int compare(Item o1, Item o2) {
		if (o1.getNameItem().equals(o2.getNameItem())) {
			return 0;
		} else {
			return (o1.getNameItem().compareTo(o2.getNameItem()) * sortOrder);
		}
	}
}
