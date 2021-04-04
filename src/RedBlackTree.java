


class Node2 {
    int data;
    Node2 parent;
    Node2 left;
    Node2 right;
    int color; // 1 . Red, 0 . Black
}



public class RedBlackTree {
    private Node2 root;
    private Node2 TNULL;

    private void preOrderHelper(Node2 node) {
        if (node != TNULL) {
            System.out.print(node.data + " ");
            preOrderHelper(node.left);
            preOrderHelper(node.right);
        }
    }

    private void inOrderHelper(Node2 node) {
        if (node != TNULL) {
            inOrderHelper(node.left);
            System.out.print(node.data + " ");
            inOrderHelper(node.right);
        }
    }

    private void postOrderHelper(Node2 node) {
        if (node != TNULL) {
            postOrderHelper(node.left);
            postOrderHelper(node.right);
            System.out.print(node.data + " ");
        }
    }

    private Node2 searchTreeHelper(Node2 node, int key) {
        if (node == TNULL || key == node.data) {
            return node;
        }

        if (key < node.data) {
            return searchTreeHelper(node.left, key);
        }
        return searchTreeHelper(node.right, key);
    }


    private void fixDelete(Node2 x) {
        Node2 s;
        try {
            while (x != root && x.color == 0) {
                if (x == x.parent.left) {
                    s = x.parent.right;
                    if (s.color == 1) {
                        // case 3.1
                        s.color = 0;
                        x.parent.color = 1;
                        leftRotate(x.parent);
                        s = x.parent.right;
                    }

                    if (s.left.color == 0 && s.right.color == 0) {
                        // case 3.2
                        s.color = 1;
                        x = x.parent;
                    } else {
                        if (s.right.color == 0) {
                            // case 3.3
                            s.left.color = 0;
                            s.color = 1;
                            rightRotate(s);
                            s = x.parent.right;
                        }

                        // case 3.4
                        s.color = x.parent.color;
                        x.parent.color = 0;
                        s.right.color = 0;
                        leftRotate(x.parent);
                        x = root;
                    }
                } else {
                    s = x.parent.left;
                    if (s.color == 1) {

                        s.color = 0;
                        x.parent.color = 1;
                        rightRotate(x.parent);
                        s = x.parent.left;
                    }

                    if (s.right.color == 0 && s.right.color == 0) {

                        s.color = 1;
                        x = x.parent;
                    } else {
                        if (s.left.color == 0) {

                            s.right.color = 0;
                            s.color = 1;
                            leftRotate(s);
                            s = x.parent.left;
                        }


                        s.color = x.parent.color;
                        x.parent.color = 0;
                        s.left.color = 0;
                        rightRotate(x.parent);
                        x = root;
                    }
                }
            }
            x.color = 0;
        } catch (NullPointerException e) {

        }
    }


    private void rbTransplant(Node2 u, Node2 v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    private void deleteNodeHelper(Node2 node, int key) {

        Node2 z = TNULL;
        Node2 x, y;
        while (node != TNULL) {
            if (node.data == key) {
                z = node;
            }

            if (node.data <= key) {
                node = node.right;
            } else {
                node = node.left;
            }
        }

        if (z == TNULL) {

            return;
        }

        y = z;
        int yOriginalColor = y.color;
        if (z.left == TNULL) {
            x = z.right;
            rbTransplant(z, z.right);
        } else if (z.right == TNULL) {
            x = z.left;
            rbTransplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                rbTransplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            rbTransplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == 0) {
            fixDelete(x);
        }
    }


    private void fixInsert(Node2 k) {
        Node2 u;
        while (k.parent.color == 1) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left; // uncle
                if (u.color == 1) {
                    // case 3.1
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        // case 3.2.2
                        k = k.parent;
                        rightRotate(k);
                    }
                    // case 3.2.1
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right; // uncle

                if (u.color == 1) {
                    // mirror case 3.1
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        // mirror case 3.2.2
                        k = k.parent;
                        leftRotate(k);
                    }
                    // mirror case 3.2.1
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = 0;
    }

    private void printHelper(Node2 root, String indent, boolean last) {

        if (root != TNULL) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|    ";
            }

            String sColor = root.color == 1 ? "RED" : "BLACK";
            System.out.println(root.data + "(" + sColor + ")");
            printHelper(root.left, indent, false);
            printHelper(root.right, indent, true);
        }
    }

    public RedBlackTree() {
        TNULL = new Node2();
        TNULL.color = 0;
        TNULL.left = null;
        TNULL.right = null;
        root = TNULL;
    }


    public void preorder() {
        preOrderHelper(this.root);
    }

    public void inorder() {
        inOrderHelper(this.root);
    }


    public void postorder() {
        postOrderHelper(this.root);
    }
    public Node2 searchTree(int k) {
        return searchTreeHelper(this.root, k);
    }


    public Node2 minimum(Node2 node) {
        while (node.left != TNULL) {
            node = node.left;
        }
        return node;
    }


    public Node2 maximum(Node2 node) {
        while (node.right != TNULL) {
            node = node.right;
        }
        return node;
    }

    public void leftRotate(Node2 x) {
        Node2 y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
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
    }


    public void rightRotate(Node2 x) {
        Node2 y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
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
    }


    public void insert(int key) {

        Node2 node = new Node2();
        node.parent = null;
        node.data = key;
        node.left = TNULL;
        node.right = TNULL;
        node.color = 1; // new node must be red

        Node2 y = null;
        Node2 x = this.root;

        while (x != TNULL) {
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


        if (node.parent == null) {
            node.color = 0;
            return;
        }


        if (node.parent.parent == null) {
            return;
        }


        fixInsert(node);
    }

    public Node2 getRoot() {
        return this.root;
    }

    public void deleteNode(int data) {
        deleteNodeHelper(this.root, data);
    }


    public void prettyPrint() {
        printHelper(this.root, "", true);
    }


}