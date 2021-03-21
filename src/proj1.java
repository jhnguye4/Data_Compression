import java.util.*;

import java.io.*;

public class proj1 {
    static StrLinkedList list = new StrLinkedList();
    static boolean decompress = false;
    static int compressed = 0;
    static int uncompressed = 0;

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter a filename: ");
        String filename = console.next().toLowerCase();

        Scanner input = null;
        PrintStream output = null;
        input = getInputScanner(filename);
        if (input != null) {
            output = getOutputPrintStream(console, filename);
            if (input.hasNextInt() && input.nextInt() == 0) {
                decompress = true;
                input.findInLine(" ").charAt(0);
            }
            if (!decompress) {
                output.print("0 ");
            }
            while (input.hasNextLine()) {
                if (decompress) {
                    String line = input.nextLine();
                    if (line.startsWith("0 Uncompressed")) {
                        continue;
                    } else {
                        output.println(decompression(line));
                    }

                } else {
                    String line = input.nextLine();
                    if (line.startsWith("0 Uncompressed")) {
                        continue;
                    } else {
                        output.println(compression(line));
                    }
                }

            }

        } else {
            System.out.println("Invalid filename.");
        }
        output.println("0 Uncompressed: " + uncompressed + " bytes;  Compressed: " + compressed + " bytes");
    }

    public static Scanner getInputScanner(String filename) {
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return fileScanner;
    }

    public static PrintStream getOutputPrintStream(Scanner console, String filename) {
        PrintStream output = null;
        File file = new File(filename + "_output.txt");
        try {
            if (!file.exists()) {
                output = new PrintStream(file);
            } else {
                System.out.print(filename + " exists - OK to overwrite(y,n)?: ");
                String reply = console.next().toLowerCase();
                if (reply.startsWith("y")) {
                    output = new PrintStream(file);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File unable to be written " + e);
        }
        return output;
    }

    public static String compression(String input) {
        StringTokenizer tokenizer = new StringTokenizer(input, ".,//%&!@#$?-_>< ", true);
        ArrayList<String> line = new ArrayList<String>();
        while (tokenizer.hasMoreElements()) {
            String word = tokenizer.nextElement().toString();
            line.add(word);
            uncompressed += word.length();
        }
        String newString = "";

        for (int i = 0; i < line.size(); i++) {
            if (!Character.isLetter(line.get(i).charAt(0))) {
                newString += line.get(i);
                compressed += line.get(i).length();
                continue;
            }
            if (list.contains(line.get(i))) {
                int number = list.indexOf(line.get(i)) + 1;
                newString += number;
                compressed += String.valueOf(number).length();
                list.remove(line.get(i));
                list.addFirst(line.get(i));
            } else {
                list.addFirst(line.get(i));
                compressed += line.get(i).length();
                newString += line.get(i);
            }
        }

        return newString;
    }

    public static String decompression(String input) {
        StringTokenizer tokenizer = new StringTokenizer(input, ".,//%&!@#$?-_>< ", true);
        ArrayList<String> line = new ArrayList<String>();
        while (tokenizer.hasMoreElements()) {
            String word = tokenizer.nextElement().toString();
            line.add(word);
            compressed += word.length();
        }
        String newString = "";

        for (int i = 0; i < line.size(); i++) {
            if (Character.isDigit(line.get(i).charAt(0))) {
                int index = Integer.parseInt(line.get(i));
                if (index == 0 || index > list.size()) {
                    continue;
                }
                uncompressed += list.get(index - 1).length();
                newString += list.get(index - 1);
                String word = list.remove(index - 1);
                list.addFirst(word);
                continue;
            }
            if (!Character.isLetter(line.get(i).charAt(0))) {
                uncompressed += line.get(i).length();
                newString += line.get(i);
                continue;
            }
            list.addFirst(line.get(i));
            uncompressed += line.get(i).length();
            newString += line.get(i);
        }
        return newString;
    }

    private static class StrLinkedList {
        private ListNode front;
        private ListNode back;
        private int size = 0;

        public StrLinkedList() {
            front = null;
            size = 0;
        }

        public void addFirst(String word) {
            if (front == null) {
                front = new ListNode(word, null);
                size++;
            } else {
                ListNode n = new ListNode(word, front);
                front = n;
                size++;
            }
        }

        public String remove(int index) {
            ListNode current = front;
            ListNode previous = null;
            if (index < 0 || index >= size()) {
                throw new IndexOutOfBoundsException();
            }
            for (int k = 0; k < index; k++) {
                if (current == null) {
                    throw new IndexOutOfBoundsException();
                }
                previous = current;
                current = current.next;
            }
            if (current == null) {
                throw new IndexOutOfBoundsException();
            }
            String value = current.data;
            if (current == front) {
                front = front.next;
            } else {
                previous.next = current.next;
            }
            if (index == size - 1) {
                back = previous;
            }

            size--;
            return value;

        }

        public String remove(String word) {
            ListNode current = front;
            ListNode previous = null;
            String value = current.data;
            if (front.data.equals(word)) {
                value = front.data;
                front = front.next;
                return value;
            }
            while (current.next != null) {
                if (current.data.equals(word)) {
                    previous.next = current.next;
                    return value;
                }
                previous = current;
                current = current.next;

            }

            size--;
            return value;

        }

        public String get(int index) {
            if (index < 0 || index >= size()) {
                throw new IndexOutOfBoundsException();
            }
            ListNode current = front;
            for (int k = 0; k < index; k++) {
                if (current == null) {
                    throw new IndexOutOfBoundsException();
                }
                current = current.next;
            }
            if (current == null) {
                throw new IndexOutOfBoundsException();
            }
            String value = current.data;
            return value;
        }

        public Boolean contains(String word) {
            ListNode current = front;
            while (current != null) {
                if (current.data.equals(word)) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }

        public int indexOf(String word) {
            int count = -1;
            ListNode current = front;
            while (current != null) {
                if (current.data.equals(word)) {
                    count++;
                    return count;
                }
                current = current.next;
                count++;
            }
            return -1;
        }

        public int size() {
            return size;
        }

        public class ListNode {

            private String data;

            private ListNode next;

            public ListNode(String data) {
                this.data = data;
            }

            public ListNode(String data, ListNode next) {
                this(data);
                this.next = next;
            }
        }
    }
}