package dev.codewizz.utils;

public class Tuple<T, Q, K> {
	public T typeA;
    public Q typeB;
    public K typeC;

    public Tuple(T typeA, Q typeB, K typeC) {
        this.typeA = typeA;
        this.typeB = typeB;
        this.typeC = typeC;
    }

    public T getTypeA() {
        return typeA;
    }

    public Q getTypeB() {
        return typeB;
    }
    
    public K getTypeC() {
    	return typeC;
    }

    public boolean has() {
        return typeA != null && typeB != null && typeC != null;
    }

    @Override
    public String toString() {
        return "Tuple{" + "typeA=" + typeA + ", typeB=" + typeB + ", typeC=" + typeC + '}';
    }
}
