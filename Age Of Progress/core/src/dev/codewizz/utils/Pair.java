package dev.codewizz.utils;

public class Pair<T, Q> {
	public T typeA;
    public Q typeB;

    public Pair(T typeA, Q typeB) {
        this.typeA = typeA;
        this.typeB = typeB;
    }

    public T getTypeA() {
        return typeA;
    }

    public Q getTypeB() {
        return typeB;
    }

    public boolean has() {
        return typeA != null && typeB != null;
    }

    @Override
    public String toString() {
        return "<" + typeA + "," + typeB + ">";
    }
}
