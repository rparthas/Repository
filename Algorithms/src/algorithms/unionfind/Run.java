package algorithms.unionfind;

import java.util.Scanner;

public class Run {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Finder find = null;
            // find = new QuickUnion(10);
            // find = new UnionFind(10);
            find = new QuickFind(10);

            while (true) {
                System.out.println("Enter first number");
                int aNumber = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter second number");
                int bNumber = Integer.parseInt(scanner.nextLine());
                if (!find.isConnected(aNumber, bNumber)) {
                    find.union(aNumber, bNumber);
                }
                System.out.println("Do you want to continue(Y/N)");
                if ("N".equals(scanner.nextLine())) {
                    break;
                }
                find.print();
            }
            find.print();
        } catch (Exception e) {
            System.out.println("Exception in union find" + e);
        }
    }
}
