package dev.codewizz.utils.quadtree;

public interface Func<T> {
    public void call(QuadTree<T> quadTree, Node<T> node);
}