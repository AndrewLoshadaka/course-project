package data_structures;

import data.Doctor;
import nodes.TreeNode;

import java.util.*;

public class AVLTree {
    public TreeNode root;
    private HashSet<Doctor> set = new HashSet<>();

    //вставка
    public void insert(Doctor doctor){
        set.add(doctor);
        root = insert(root, doctor);
    }

    //удаление
    public void delete(String name){
        root = delete(root, name);
    }

    // доп метод вставки
    private TreeNode insert(TreeNode node, Doctor doctor){
        if(node == null) return new TreeNode(doctor);
        String nodeName = node.getDate().getName().toLowerCase(Locale.ROOT);
        String docName = doctor.getName().toLowerCase(Locale.ROOT);

        if(nodeName.compareTo(docName) > 0)
            node.setLeft(insert(node.getLeft(), doctor));
        else if(nodeName.compareTo(docName) < 0)
            node.setRight(insert(node.getRight(), doctor));
        else throw new RuntimeException("duplicate Key!");
        return reBalance(node);
    }

    //проверка дубликатов
    public boolean checkDuplicateKey(String line){
        for (Doctor x : set){
            if(x.getName().equals(line))
                return true;
        }
        return false;
    }

    //доп метод для удаления
    private TreeNode delete(TreeNode node, String docName){
        if(node == null) return null;
        docName = docName.toLowerCase(Locale.ROOT);
        String nodeName = node.getDate().getName().toLowerCase(Locale.ROOT);
        if(nodeName.compareTo(docName) > 0)
            node.setLeft(delete(node.getLeft(), docName));
        else if(nodeName.compareTo(docName) < 0 )
            node.setRight(delete(node.getRight(), docName));
        else {
            if(node.getLeft() == null || node.getRight() == null)
                node = (node.getLeft() == null) ? node.getRight() : node.getLeft();
            else{
                TreeNode leftChild = mostLeftChild(node.getRight());
                node.setDate(leftChild.getDate());
                node.setRight(delete(node.getRight(), node.getDate().getName()));
            }
        }
        if (node != null){
            node = reBalance(node);
        }
        return node;
    }

    //ребалансировка
    private TreeNode reBalance(TreeNode node){
        updateHeight(node);
        int currentBalance = getBalance(node);
        if(currentBalance > 1){
            if (height(node.getRight().getRight()) <= height(node.getRight().getLeft())) {
                node.setRight(rotateRight(node.getRight()));
            }
            node = rotateLeft(node);
        } else if(currentBalance < -1){
            if (height(node.getLeft().getLeft()) <= height(node.getLeft().getRight())) {
                node.setLeft(rotateLeft(node.getLeft()));
            }
            node = rotateRight(node);
        }
        return node;
    }


    private TreeNode mostLeftChild(TreeNode node){
        TreeNode currentNode = node;
        while (currentNode.getLeft() != null)
            currentNode = currentNode.getLeft();
        return currentNode;
    }

    private void updateHeight(TreeNode node){
        node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));
    }


    // TODO: ПОВОРОТЫ
    private TreeNode rotateLeft(TreeNode node){
        TreeNode tempRight = node.getRight();
        TreeNode tempLeft = tempRight.getLeft();
        tempRight.setLeft(node);
        node.setRight(tempLeft);
        updateHeight(node);
        updateHeight(tempRight);
        return tempRight;
    }

    private TreeNode rotateRight(TreeNode node){
        TreeNode tempLeft = node.getLeft();
        TreeNode tempRight = tempLeft.getRight();
        tempLeft.setRight(node);
        node.setLeft(tempRight);
        updateHeight(node);
        updateHeight(tempLeft);
        return tempLeft;
    }

    private int height(TreeNode node){
        return node == null ? -1 : node.getHeight();
    }

    public int getBalance(TreeNode node){
        return (node == null) ? 0 : height(node.getRight()) - height(node.getLeft());
    }

    //печать дерева
    private void print(TreeNode node, int level) {
        if (node != null) {
            print(node.getRight(),level+1);
            for (int i=0;i<level;i++) {
                System.out.print("\t");
            }
            System.out.println(node.getDate().getName());
            print(node.getLeft(),level+1);
        }
    }

    public void print() {
        print(root,0);
    }

    //симмтеричный обход
    public ArrayList<Doctor> symmetricBypass(TreeNode node) {
        ArrayList<Doctor> list = new ArrayList<>();
        if (node != null) {
            symmetricBypass(node.getLeft());
            list.add(node.getDate());
            symmetricBypass(node.getRight());
        }
        return list;
    }


    public void printListOfDoctor(List<Doctor> list){
        for(int i = 0; i < list.size(); i++) {
            System.out.print((i + 1) + ") ");
            printInformAboutDoctor(list.get(i));
        }
    }

    //печать сведениц о докторе
    public void printInformAboutDoctor(Doctor doctor){
        System.out.println("Имя:" + " " + doctor.getName() + "; Должность: " + doctor.getPost() + "; Кабинет: " + doctor.getNumberOfRoom()
                + "; График приема: " + doctor.getSchedule());
    }


    public Doctor findByNumber(String number, String name){
        return contains(name).get(Integer.parseInt(number) - 1);
    }
    //поиск доктора по имени
    public Doctor find(String name){
        TreeNode findNode = root;
        String keyToLowerCase = name.toLowerCase(Locale.ROOT);
        while (findNode != null) {
            String currentName = findNode.getDate().getName().toLowerCase(Locale.ROOT);
            if (findNode.getDate().getName().equals(name))
                break;
            findNode = currentName.compareTo(keyToLowerCase) < 0 ? findNode.getRight() : findNode.getLeft();
        }
        if(findNode == null) return null;
        return findNode.getDate();
    }

    //очистка дерева
    public void clearTree(){
        root = null;
    }

    //просмотр дерева
    public void showTree(){
        symmetricBypass(root);
    }

    public ArrayList<Doctor> contains(String line){
        line = line.toLowerCase();
        ArrayList<Doctor> listDoctor = new ArrayList<>();
        for(Doctor x : set){
            if(contains(x.getPost().toLowerCase(), line))
                listDoctor.add(x);
        }
        return listDoctor;
    }

    //поиск по части текста
    private boolean contains(String str, String str1){
        char[] line1 = str.toCharArray();
        char[] line2 = str1.toCharArray();
        int firstEntry = -1;
        int j;
        for(int i = 0; i < line1.length - line2.length; i++){
            j = 0;
            while (j < line2.length && (line2[j] == line1[i + j]))
                j++;
            if (j == line2.length) firstEntry = i;
        }
        return firstEntry != -1;
    }
}