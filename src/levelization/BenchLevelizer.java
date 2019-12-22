package levelization;

import java.util.*;
import java.io.*;

public class BenchLevelizer {

    //configs
    final static String UX_BENCH_FILE = "c:/ISCAS85/bench/c17_UX.bench.txt";
    final static String LEVELIZED_BENCH_FILE = UX_BENCH_FILE.replace("UX", "LEVELIZED");
    //variables
    static LinkedList<String> outputList = new LinkedList<>();
    static LinkedList<String> unlevelizedList = new LinkedList<>();
    static LinkedList<String> levelizedList = new LinkedList<>();

    public static void main(String[] args) {
        readUXBench(UX_BENCH_FILE);
        start();
        writeLevelizedBeanch(LEVELIZED_BENCH_FILE);
    }

    public static void readUXBench(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String aLine;
            //skips comments , inputs and outputs
            while (null != (aLine = br.readLine())) {
                if (aLine.startsWith("#") || aLine.startsWith("OUTPUT(") || aLine.length() == 0) {
                    outputList.add(aLine);
                } else if (aLine.startsWith("INPUT(")) {
                    outputList.add(aLine);
                    unlevelizedList.add(aLine);
                } else {
                    unlevelizedList.add(aLine);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void start() {
      
    }

    private static void writeLevelizedBeanch(String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (String k : outputList) {
                bw.write(k);
                bw.newLine();
            }
            for (String k : levelizedList) {
                bw.write(k);
                bw.newLine();
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

}
