package org.healthylifestyle.common;

public interface ParentDeletionListener<T> {
	void parentWillBeDeleted(T parent);
}
