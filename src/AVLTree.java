
class Node {
    int data;
    Node parent;
    Node left;
    Node right;
    int bf;

    public Node(int data) {
        this.data = data;
        this.parent = null;
        this.left = null;
        this.right = null;
        this.bf = 0;
    }
}

public class AVLTree {
    private Node root;

    public AVLTree() {
        root = null;
    }

    private void printHelper(Node currPtr, String indent, boolean last) {

        if (currPtr != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|    ";
            }

            System.out.println(currPtr.data + "(BF = " + currPtr.bf + ")");

            printHelper(currPtr.left, indent, false);
            printHelper(currPtr.right, indent, true);
        }
    }

    private Node searchTreeHelper(Node node, int key) {
        if (node == null || key == node.data) {
            return node;
        }

        if (key < node.data) {
            return searchTreeHelper(node.left, key);
        }
        return searchTreeHelper(node.right, key);
    }

    private Node deleteNodeHelper(Node node, int key) {

        if (node == null) return node;
        else if (key < node.data) node.left = deleteNodeHelper(node.left, key);
        else if (key > node.data) node.right = deleteNodeHelper(node.right, key);
        else {

            if (node.left == null && node.right == null) {
                node = null;
            }


            else if (node.left == null) {
                Node temp = node;
                node = node.right;
            }

            else if (node.right == null) {
                Node temp = node;
                node = node.left;
            }


            else {
                Node temp = minimum(node.right);
                node.data = temp.data;
                node.right = deleteNodeHelper(node.right, temp.data);
            }

        }

        return node;
    }


    private void updateBalance(Node node) {
        if (node.bf < -1 || node.bf > 1) {
            rebalance(node);
            return;
        }

        if (node.parent != null) {
            if (node == node.parent.left) {
                node.parent.bf -= 1;
            }

            if (node == node.parent.right) {
                node.parent.bf += 1;
            }

            if (node.parent.bf != 0) {
                updateBalance(node.parent);
            }
        }
    }


    void rebalance(Node node) {
        if (node.bf > 0) {
            if (node.right.bf < 0) {
                rightRotate(node.right);
                leftRotate(node);
            } else {
                leftRotate(node);
            }
        } else if (node.bf < 0) {
            if (node.left.bf > 0) {
                leftRotate(node.left);
                rightRotate(node);
            } else {
                rightRotate(node);
            }
        }
    }


    public Node searchTree(int k) {
        return searchTreeHelper(this.root, k);
    }

    public Node minimum(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public Node maximum(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;

        x.bf = x.bf - 1 - Math.max(0, y.bf);
        y.bf = y.bf - 1 + Math.min(0, x.bf);
    }

    void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != null) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;

        x.bf = x.bf + 1 - Math.min(0, y.bf);
        y.bf = y.bf + 1 + Math.max(0, x.bf);
    }


    public void insert(int key) {

        Node node = new Node(key);
        Node y = null;
        Node x = this.root;

        while (x != null) {
            y = x;
            if (node.data < x.data) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.data < y.data) {
            y.left = node;
        } else {
            y.right = node;
        }

        updateBalance(node);
    }

    Node deleteNode(int data) {
        return deleteNodeHelper(this.root, data);
    }
    
    public void print() {
        printHelper(this.root, "", true);
    }

}
