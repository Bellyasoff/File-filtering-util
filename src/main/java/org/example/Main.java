package main.java.org.example;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            checkArgs(args);
        } catch (Exception e) {
            System.err.println("Ошибка выполнения программы: " + e.getMessage());
        }
    }

    private static String resultPath = "";
    private static String resultName = "";
    private static boolean isAppend;
    private static boolean isShortStatisticRequired;
    private static boolean isFullStatisticRequired;
    private static int lineCount;
    private static int integerCount;
    private static int doubleCount;
    private static final Statistic statistic = new Statistic();

    public static void checkArgs(String[] args) {

        if (args.length == 0) {
            System.err.println("Не указаны параметры и файлы для чтения.\n" +
                    "Пример запуска: java -jar util.jar -s -a -p sample- in1.txt in2.txt");
            return;
        }

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o": // Путь исходного файла
                    resultPath = args[++i] + "/";
                    break;
                case "-p": // Префикс исходного файла
                    resultName = args[++i];
                    break;
                case "-a": // Добавление в файл
                    isAppend = true;
                    break;
                case "-s": // Необходима краткая статистика
                    isShortStatisticRequired = true;
                    break;
                case "-f": // Необходима полная статистика
                    isFullStatisticRequired = true;
                    break;
                default:
                    if (!args[i].endsWith(".txt")) {
                        System.err.println("Неизвестное расширение файла: " + args[i] + "\n" +
                                "Файл должен иметь расширение .txt");
                        break;
                    }
                    readFile("src/main/java/org/example/" + args[i]);
            }
        }
        printStatistic();
    }

    public static void printStatistic() {
        if (isShortStatisticRequired || isFullStatisticRequired) {
            System.out.println("Краткая статистика: " + "\n");
            System.out.println("Количество отфильтрованных строк: " + lineCount + "\n");
            if (isFullStatisticRequired) {
                System.out.println("Полная статистика: " + "\n");
                System.out.println(statistic.getStatistic() + "\n");
                System.out.println("Среднее значение целых чисел: " + (Statistic.intSum / integerCount));
                System.out.println("Среднее значение вещественных чисел: " + (Statistic.doubleSum / doubleCount));
            }
        }
    }

    private static void readFile(String file) {
        BufferedWriter stringWriter = null;
        BufferedWriter integersWriter = null;
        BufferedWriter doubleWriter = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            String line;
            while ((line = reader.readLine()) != null) {
                // Является ли строка текстом
                if (line.matches("\\D+")) {
                    if (stringWriter == null) {
                        stringWriter = new BufferedWriter(
                                new FileWriter(resultPath + resultName + "string.txt", isAppend));
                    }
                    lineCount++;
                    statistic.getLongestAndShortestLines(line);

                    stringWriter.write(line + "\n");
                    stringWriter.flush();
                }
                // Является ли строка целым числом
                 else if (line.matches("-?\\d+")) {
                    if (integersWriter == null) {
                        integersWriter = new BufferedWriter(
                                new FileWriter(resultPath + resultName + "integers.txt", isAppend));
                    }
                    lineCount++;
                    integerCount++;
                    statistic.getIntStat(Long.parseLong(line));

                    integersWriter.write(line + "\n");
                    integersWriter.flush();
                }
                // Является ли строка вещественным числом
                else if (line.matches("-?\\d+\\.?\\d+E?-?\\d+")) {
                    if (doubleWriter == null) {
                        doubleWriter = new BufferedWriter(
                                new FileWriter(resultPath + resultName + "floats.txt", isAppend));
                    }
                    lineCount++;
                    doubleCount++;
                    statistic.getDoubleStat(Double.parseDouble(line));

                    doubleWriter.write(line + "\n");
                    doubleWriter.flush();
                }
                else {
                    System.err.println("Неизвестный формат данных: " + line);
                }
            }

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + file + e.getMessage());
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (stringWriter != null) {
                    stringWriter.close();
                }
                if (integersWriter != null) {
                    integersWriter.close();
                }
                if (doubleWriter != null) {
                    doubleWriter.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}