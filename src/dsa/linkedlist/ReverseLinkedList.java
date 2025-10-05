package dsa.linkedlist;

public class ReverseLinkedList {

    static class Node{
        int val;
        Node next;
        Node(int val){
            this.val = val;
        }
    }


     static Node reverse(Node head){
        if(head == null || head.next == null) {
             return head;
        }

        Node newHead = reverse(head.next); //every time this node will be the last node
        head.next.next = head;
        head.next = null;
        System.out.println(newHead.val);
        return newHead;
    }

    public static void main(String[] args) {
        Node node = new Node(1);
        node.next = new Node(2);
        node.next.next = new Node(3);
        node.next.next.next = new Node(4);
        Node reverseHead =  reverse(node);
        while(reverseHead != null){
            System.out.println(reverseHead.val);
            reverseHead = reverseHead.next;
        }
    }
}


