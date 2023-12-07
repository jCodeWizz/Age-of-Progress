package dev.codewizz.modding;

import java.lang.reflect.Method;

import dev.codewizz.modding.annotations.Priorities;

public class EventMethod implements Comparable<EventMethod>{

	private Object object;
	private Method method;
	private Priorities priority;
	
	public EventMethod(Object object, Method method, Priorities priority) {
		this.object = object;
		this.method = method;
		this.priority = priority;
	}

	public Object getObject() {
		return object;
	}

	public Method getMethod() {
		return method;
	}

	public Priorities getPriority() {
		return priority;
	}

	@Override
	public int compareTo(EventMethod o) {
		if(this.priority.ordinal() == o.priority.ordinal()) return 0;
		else if(this.priority.ordinal() > o.priority.ordinal()) return 1;
		else return -1;
	}
}
