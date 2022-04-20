package cold.blade.practice.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import cold.blade.practice.algorithm.struct.DListNode;

/**
 * @Description LRU算法实现
 *                  1、HashMap + 双链表
 *                  2、单链表
 *
 * @author cold_blade
 * @date 2022/4/19
 * @version 1.0
 */
public class LRUPractice {

    private static final int CAPACITY = 3;

    private Map<Integer, DListNode<Integer>> map = new HashMap<>(CAPACITY * 2);
    private DListNode<Integer> head = new DListNode<>();
    private DListNode<Integer> tail = new DListNode<>();
    private int curSize = 0;

    public LRUPractice() {
        head.setNextNode(tail);
        tail.setPrevNode(head);
    }

    public Optional<Integer> get(Integer val) {
        if (Objects.isNull(val)) {
            return Optional.empty();
        }
        return Optional.ofNullable(map.get(val))
            .map(node -> {
                // 访问并将其移动到尾部
                move2Tail(node);
                return node.getVal();
            });
    }

    public void save(Integer val) {
        DListNode<Integer> node = new DListNode<>(val);
        map.put(val, node);
        if (curSize < CAPACITY) {
            append(node);
            curSize++;
        } else {
            pop();
            append(node);
        }
    }

    public Optional<Integer> firstValue() {
        return Optional.ofNullable(head.getNextNode().getVal());
    }

    public Optional<Integer> lastValue() {
        return Optional.ofNullable(tail.getPrevNode().getVal());
    }

    public int size() {
        return map.size();
    }

    private void pop() {
        DListNode node = head.getNextNode();
        if (node == tail) {
            return;
        }
        remove(node);
        map.remove(node.getVal(), node);
    }

    private void move2Tail(DListNode node) {
        remove(node);
        append(node);
    }

    private void append(DListNode node) {
        DListNode prevNode = tail.getPrevNode();
        node.setNextNode(tail);
        node.setPrevNode(prevNode);
        prevNode.setNextNode(node);
        tail.setPrevNode(node);
    }

    private void remove(DListNode node) {
        if (node == head || node == tail) {
            return;
        }
        DListNode prevNode = node.getPrevNode();
        prevNode.setNextNode(node.getNextNode());
        node.getNextNode().setPrevNode(prevNode);
        node.setPrevNode(null);
        node.setNextNode(null);
    }
}
