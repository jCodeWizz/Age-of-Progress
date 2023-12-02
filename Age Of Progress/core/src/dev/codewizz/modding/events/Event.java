package dev.codewizz.modding.events;

import java.lang.reflect.Method;

import dev.codewizz.modding.Registers;
import dev.codewizz.utils.Logger;
import dev.codewizz.utils.Pair;

public abstract class Event {

	/**
	 * The main method for dispatching events for mods. Only dispatch events to methods that accept this specific 
	 * type of event by checking if their .class is the same. Then us the object stored in the same pair to execute the method on/with whatever.
	 * @param event The event that is going to be dispatched.
	 * @return a boolean that is true if the event WASN'T cancelled. (false if it was cancelled)
	 */
	public static boolean dispatch(Event event) {
		for (Pair<Object, Method> m : Registers.events.values()) {
			if (m.getTypeB().getGenericParameterTypes()[0].getTypeName().equals(event.getClass().getTypeName())) {
				try {
					m.getTypeB().invoke(m.getTypeA(), event);
				} catch (Exception exception) {
					Logger.error("Exception during event dispatch: ");
					exception.printStackTrace();
				}
			}
		}
		
		return !event.isCancelled();
	}

	protected boolean cancelled = false;

	/**
	 * check if event is cancelled;
	 * @pure
	 * @return true if event is cancelled, false otherwise.
	 */
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * cancel this event. Not all events are actually able to be cancelled. 
	 */
	public void cancel() {
		this.cancelled = true;
	}
}
