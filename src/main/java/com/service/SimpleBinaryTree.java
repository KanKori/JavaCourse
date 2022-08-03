package com.service;

import com.model.Phone;
import com.model.Product;
import com.model.ProductComparator;
import com.repository.LaptopRepository;
import com.repository.PhoneRepository;
import lombok.Getter;

import java.io.PrintStream;

public class SimpleBinaryTree<E extends Product> {

    private Node<E> root;
    private final ProductComparator<E> productComparator = new ProductComparator<>();

    public SimpleBinaryTree() {
    }

    public void createAndOutputTree(ProductService<E> productService) {
        productService.createAndSaveProducts(14);
        SimpleBinaryTree<E> productTree = new SimpleBinaryTree<>();
        for (E product : productService.getAll()) {
            productTree.add(product);
        }

        productTree.print(System.out);
        System.out.println();
        System.out.println("---".repeat(10));
        System.out.println("Left branch sum: " + productTree.sumLeftBranch());
        System.out.println("Right branch sum: " + productTree.sumRightBranch());
        System.out.println("---".repeat(10));
    }

    private Node<E> addRecursive(Node<E> current, E value) {
        if (current == null) {
            return new Node<>(value);
        }

        if (productComparator.compare(current.item, value) < 0) {
            current.left = addRecursive(current.left, value);
        } else if (productComparator.compare(current.item, value) > 0) {
            current.right = addRecursive(current.right, value);
        } else {
            return current;
        }
        return current;
    }

    public void add(E item) {
        root = addRecursive(root, item);
    }

    public double sumLeftBranch() {
        if (root.left == null) {
            return 0;
        }
        return sum(root.left);
    }

    public double sumRightBranch() {
        if (root.right == null) {
            return 0;
        }
        return sum(root.right);
    }

    private double sum(Node<E> root) {
        if (root == null)
            return 0;
        return (root.item.getPrice() + sum(root.left) +
                sum(root.right));
    }

    public void traverseNodes(StringBuilder sb, String padding, String pointer, Node<E> node,
                              boolean hasRightSibling) {
        if (node != null) {
            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.item.getTitle());

            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSibling) {
                paddingBuilder.append("|  ");
            } else {
                paddingBuilder.append(" ");
            }

            String paddingForBoth = paddingBuilder.toString();
            String pointerRight = "'--";
            String pointerLeft = "|--";

            traverseNodes(sb, paddingForBoth, pointerLeft, node.getLeft(), node.getRight() != null);
            traverseNodes(sb, paddingForBoth, pointerRight, node.getRight(), false);
        }
    }

    public String traversePreOrder(Node<E> root) {

        if (root == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(root.item.getTitle());

        String pointerRight = "'--";
        String pointerLeft = "|--";

        traverseNodes(sb, "", pointerLeft, root.left, root.right != null);
        traverseNodes(sb, "", pointerRight, root.right, false);

        return sb.toString();
    }

    public void print(PrintStream os) {
        os.print(traversePreOrder(root));
    }

    @Getter
    private static class Node<T extends Product> {
        T item;
        Node<T> left;
        Node<T> right;

        Node(T item) {
            this.item = item;
            right = null;
            left = null;
        }
    }
}
