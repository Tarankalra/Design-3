import java.util.HashMap;
import java.util.Map;

// TC - get and put O(1)
// SC - O(capacity)
// leetcode passed - Yes
// link - https://leetcode.com/problems/lru-cache/


// Approach - we are using hashmap and a doubly linked list to have the get and put operation in O(1)
// Hashmap - key - Node's reference pair
// Doubly linked list - we are having the head and tail pseudo nodes where the most recently used is after head
// and least recently used is before tail node
public class LRUCache {
    int capacity;
    Node head;
    Node tail;
    Map <Integer, Node> map;

    class Node {
        int key;
        int value;

        Node next;
        Node prev;
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    // initialization of our cache with given capacity
    // creating pseudo nodes and initially they are pointing to each other
    public LRUCache(int capacity) {
        map = new HashMap <>();
        this.capacity = capacity;
        head = new Node(0,0);
        tail = new Node(0,0);
        head.next = tail;
        tail.prev = head;
    }

    // we the key already exist in cache, we are using hashmap to fetch the nodes address in O(1) time
    // otherwise we will have to iterate over the entire LL to fetch the node with given key
    // if the key already exist, we need to put it into most recently used node area - which is just after head
    public int get(int key) {
        if(map.containsKey(key)) {
            Node node = map.get(key);
            delete(node); // delete it where it is currently present
            insertAfterHead(node);
            return node.value;
        }
        return -1;
    }

    private void delete(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.next=null;
        node.prev=null;
    }

    private void insertAfterHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next = node;
        node.next.prev = node;
    }

    // here we have 2 cases,
    // if the key already exist in the map, we need to delete it wherever it is currently and remove from map
    // removal from map is imp because we have created new Node, and new address needs to be updated in map as well
    // if key doesn't exists, check for the map's size, if its equal to capacity, we need to remove lru node from map as well as LL
    // and add the new node after head and put in map.
    public void put(int key, int value) {
        Node node = new Node(key, value);
        if(map.containsKey(key)) {
            delete(map.get(key));
            map.remove(key);
        } else{
            if(map.size() >= capacity) {
                Node lruNode = tail.prev;
                delete(lruNode);
                map.remove(lruNode.key);
            }
        }
        insertAfterHead(node);
        map.put(key, node);
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

