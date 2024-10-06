package me.kruase.mipt;


import me.kruase.mipt.customarraylist.CustomArrayList;


public class Main {
    public static void main(String[] args) {
        System.out.println(">>> new CustomArrayList<Integer>()");
        CustomArrayList<Integer> customArrayList = new CustomArrayList<>();

        System.out.println(">>> isEmpty()\n" + customArrayList.isEmpty());
        System.out.println(">>> size()\n" + customArrayList.size());

        System.out.println(">>> add(1)");
        customArrayList.add(1);

        System.out.println(">>> size()\n" + customArrayList.size());
        System.out.println(">>> get(0)\n" + customArrayList.get(0));

        System.out.println(">>> add(2)");
        customArrayList.add(2);
        System.out.println(">>> add(3)");
        customArrayList.add(3);

        System.out.println(">>> size()\n" + customArrayList.size());
        System.out.println(">>> toString()\n" + customArrayList);

        System.out.println(">>> add(4)");
        customArrayList.add(4);
        System.out.println(">>> add(5)");
        customArrayList.add(5);

        System.out.println(">>> remove(1)\n" + customArrayList.remove(1));
        System.out.println(">>> size()\n" + customArrayList.size());
        System.out.println(">>> toString()\n" + customArrayList);

        System.out.println(">>> remove(2)\n" + customArrayList.remove(2));
        System.out.println(">>> size()\n" + customArrayList.size());
        System.out.println(">>> toString()\n" + customArrayList);

        System.out.println();

        System.out.println(">>> new CustomArrayList<String>()");
        CustomArrayList<String> longCustomArrayList = new CustomArrayList<>();

        System.out.println(">>> [filling 1000 elements with \"a\".repeat(i)]");

        int desiredSize = 1000;
        String letter = "a";

        for (int i = 0; i < desiredSize; i++) {
            longCustomArrayList.add(letter.repeat(i));
        }

        System.out.println(">>> get(42)\n" + longCustomArrayList.get(42));
        System.out.println(">>> get(42).length()\n" + longCustomArrayList.get(42).length());

        System.out.println(">>> [removing elements at odd indices (all elements of odd length)]");

        for (int i = 1; i < longCustomArrayList.size(); i++) {
            longCustomArrayList.remove(i);
        }

        System.out.println(">>> get(42)\n" + longCustomArrayList.get(42));
        System.out.println(">>> get(42).length()\n" + longCustomArrayList.get(42).length());
    }
}
