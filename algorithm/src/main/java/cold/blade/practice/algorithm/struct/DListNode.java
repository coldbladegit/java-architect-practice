package cold.blade.practice.algorithm.struct;

/**
 * @Description
 *   双向链表
 * @author cold_blade
 * @date 2022/4/19
 * @version 1.0
 */
public class DListNode<T> {

    private DListNode<T> prevNode;
    private DListNode<T> nextNode;
    private T val;

    public DListNode() {
    }

    public DListNode(T val) {
        this.val = val;
    }

    public DListNode<T> getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(DListNode<T> prevNode) {
        this.prevNode = prevNode;
    }

    public DListNode<T> getNextNode() {
        return nextNode;
    }

    public void setNextNode(DListNode<T> nextNode) {
        this.nextNode = nextNode;
    }

    public T getVal() {
        return val;
    }
}
