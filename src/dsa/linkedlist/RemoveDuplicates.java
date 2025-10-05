package dsa.linkedlist;

public class RemoveDuplicates {
    static class Node{
        int val;
        Node next;
        Node(int val){
            this.val = val;
        }
    }


    static Node removeDuplicates(Node head){
        if(head == null || head.next == null) return  head;

        head.next = removeDuplicates(head.next);

        if(head.val == head.next.next.val){
            return head.next;
        }
        else {
            return head;
        }
    }

    public static void main(String[] args) {
        Node node = new Node(1);
        node.next = new Node(2);
        node.next.next = new Node(2);
        node.next.next.next = new  Node(4);
        node.next.next.next.next = new  Node(4);
        // Duplicates are 2 and 4
        // I can remove iteratively or recursivly

        Node head = node;


        while(node != null && node.next != null){

            Node temp = node.next;

            while(temp != null && temp.val == node.val){
                temp = temp.next;
            }

            node.next = temp;
            node = node.next;
        }

        // now print the linked list

        while(head != null){
            System.out.println(head.val);
            head = head.next;
        }


    }
}
