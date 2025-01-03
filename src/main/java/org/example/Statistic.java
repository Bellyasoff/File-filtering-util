package main.java.org.example;

public class Statistic {

    private int shortestLineLength = Integer.MAX_VALUE;
    private int longestLineLength = 0;
    private long minIntNumber = Integer.MAX_VALUE;
    private long maxIntNumber = 0;
    public static long intSum = 0;
    private double minDoubleNumber = Double.MAX_VALUE;
    private double maxDoubleNumber = 0;
    public static double doubleSum = 0;

    public void getLongestAndShortestLines(String line) {
        if (line.length() > longestLineLength) {
            longestLineLength = line.length();
        }
        if (line.length() < shortestLineLength) {
            shortestLineLength = line.length();
        }
    }

    public String getStatistic() {
        return "Размер самой длинной строки: " + longestLineLength + "\n" +
                "Размер самой короткой строки: " + shortestLineLength + "\n\n" +
                "Максимальное целочисленное число: " + maxIntNumber + "\n" +
                "Минимальное целочисленное число: " + minIntNumber + "\n\n" +
                "Максимальное вещественное число: " + maxDoubleNumber + "\n" +
                "Минимальное вещественное число: " + minDoubleNumber + "\n\n" +
                "Сумма целочисленных чисел: " + intSum + "\n" +
                "Сумма вещественных чисел: " + doubleSum;
    }

    public void getIntStat(long number) {
        if (number > maxIntNumber) {
            maxIntNumber = number;
        }
        if (number < minIntNumber) {
            minIntNumber = number;
        }
        intSum += number;
    }

    public void getDoubleStat(double number) {
        if (number < minDoubleNumber)  {
            minDoubleNumber = number;
        }
        if (number > maxDoubleNumber) {
            maxDoubleNumber = number;
        }
        doubleSum += number;
    }

}
