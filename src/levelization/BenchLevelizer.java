package levelization;

import java.util.*;
import java.io.*;

public class BenchLevelizer {

    //configs
    final static String UX_BENCH_FILE = "c:/ISCAS85/bench/c432_UX.bench.txt";
    final static String LEVELIZED_BENCH_FILE = UX_BENCH_FILE.replace("UX", "LEVELIZED");
    //variables
    static LinkedList<String> outputList = new LinkedList<>();
    static LinkedList<Gate> unready = new LinkedList<>();
    static LinkedList<Gate> ready = new LinkedList<>();
    static HashMap<String, Gate> gates = new HashMap<>();

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
                    continue;
                }
                Gate g;
                if (aLine.startsWith("INPUT(")) {
                    outputList.add(aLine);
                    g = Gate.getInputGate(aLine);
                } else {
                    g = Gate.getGate(aLine);
                    unready.add(g);
                }
                gates.put(g.name, g);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void start() {
        while (!unready.isEmpty()) {
            Gate g = unready.poll();
            int maxLevel = 0;
            boolean isReady = true;
            //push unready gate back
            for (String k : g.inputs) {
                int n = gates.get(k).level;
                if (n == 0) {
                    unready.add(g);
                    isReady = false;
                    break;
                }
                if (n > maxLevel) {
                    maxLevel = n;
                }
            }
            //set level to this gate and push to done
            if (isReady) {
                g.level = maxLevel + 1;
                ready.add(g);
            }
        }
    }

    private static void writeLevelizedBeanch(String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (String k : outputList) {
                bw.write(k);
                bw.newLine();
            }
            //output gates
            for (Gate g : ready) {
                System.out.println(g.name + " " + g.level);
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }
}

class Gate {

    final String originalString;
    final String name;
    String[] inputs;
    int level;

    private Gate(String s, String n) {
        originalString = s;
        name = n;
    }

    private Gate(String s, String n, int level) {
        this(s, n);
        this.level = level;
    }

    public static Gate getInputGate(String s) {
        return new Gate(s, s.substring(6, s.length() - 1), 1);
    }

    public static Gate getGate(String s) {
        Gate g = new Gate(s, s.substring(0, s.indexOf(" ")));
        s = s.substring(s.indexOf("(") + 1, s.length() - 1);
        g.inputs = s.split(", ");
        return g;
    }

    @Override
    public String toString() {
        return String.format("name : %s , str : %s", name, originalString);
    }
}
