package store.comparators;

import store.extending.Item;

public class PriceComparator extends ItemsComparator{

	@Override
	public int compare(Item o1, Item o2) {
		if (o1.getPrice() < o2.getPrice()) {
			return -1 * sortOrder;
		} else if (o1.getPrice() > o2.getPrice()) {
			return 1 * sortOrder;
		}
		return 0;
	}

}
