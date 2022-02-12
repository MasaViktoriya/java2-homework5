package com.geekbrains;

public class Main {
    public static void main(String[] args) {
        commonArray();
        concurrentArray();
    }

    public static void commonArray (){
        final int size = 1000000;
        float[] array = new float[size];
        for (int j = 0; j < size; j++) {
            array[j] = 1f;
        }
        long a = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            array[i] = (float)(array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Время работы обычного метода:");
        System.out.println(System.currentTimeMillis() - a);
    }

    public static void concurrentArray (){
        final int size = 1000000;
        final int half = size / 2;
        float[] array = new float[size];
        float[] a1 = new float[half];
        float[] a2 = new float[half];
        for (int j = 0; j < size; j++) {
            array[j] = 1f;
        }
        long a = System.currentTimeMillis();
        System.arraycopy(array, 0, a1, 0, half);
        System.arraycopy(array, half, a2, 0, half);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < half; i++) {
                    a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < half; i++) {
                    a2[i] = (float)(a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        thread1.start();
        thread2.start();
        System.arraycopy(a1, 0, array, 0, half);
        System.arraycopy(a2, 0, array, half, half);
        System.out.println("Время работы метода в два потока:");
        System.out.println(System.currentTimeMillis() - a);
    }
}
