public class MyLinkedList {
    public static void main(String[] args) {
        //创建一个链表
        LinkedListDemo list = new LinkedListDemo();
        //添加16个节点
        for (int i = 1; i <17 ; i++) {
            list.addNode(new Node(i));
        }
        list.listNode();
        list.insertNodeAtIndex(8,new Node(666));
        list.listNode();
    }


}
class LinkedListDemo{
    Node head;
    public LinkedListDemo() {

    }
    //添加节点
    public void addNode(Node node){
        Node temp = this.head;
        if (temp!=null){
            while(temp.getNext()!=null){
                temp = temp.getNext();
            }
            temp.setNext(node);
        }else {
            head = node;
        }

    }
    //遍历节点
    public void listNode(){
        Node temp = this.head;
        while(temp.getNext()!=null){
            System.out.print(temp+"->>");
            temp = temp.getNext();
        }
        System.out.println(temp);
    }
    //在指定位置插入节点
    public void insertNodeAtIndex(int index,Node node){
        Node temp = this.head;
        Node next;
        for (int i = 1; i <index; i++) {
            temp = temp.getNext();
        }
        //经过for循环找到要插入的位置
        next = temp.getNext();
        temp.setNext(node);
        node.setNext(next);

    }

}
class Node{
    private int val;
    private Node next;

    public Node(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "val=" + val +
                '}';
    }
}