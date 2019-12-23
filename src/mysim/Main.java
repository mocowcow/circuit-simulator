package mysim;

import java.io.*;
import java.util.*;
import levelization.*;

public class Main {

    //configs
    final static String BENCH_FILE = "d:/ISCAS85/bench/c432_UX.bench.txt";
    final static String INPUT_FILE = "d:/ISCAS85/ip/c432_1m_ip.txt";
    final static String TEMP_FILE = BENCH_FILE.replace("UX", "LEVELIZED");
    final static String OUTPUT_FILE = INPUT_FILE.replaceAll("ip", "op");
    final static int NUMBER_OF_THREAD = 2;
    //variables
    static int[] fromIndex = new int[NUMBER_OF_THREAD];
    static int[] toIndex = new int[NUMBER_OF_THREAD];
    static CircuitSimulator[] simulators = new CircuitSimulator[NUMBER_OF_THREAD];
    static List<String> ipvs = new ArrayList<>();
    static String[] opvs;
    static int inputSize;

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        buildCircuit();
        simulation();
        System.out.printf("Total time=%.3f sec(s)\n",
                (System.currentTimeMillis() - start) / 1000.0);
    }

    private static void simulation() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE));
        String aLine;
        while ((aLine = br.readLine()) != null) {
            ipvs.add(aLine.trim());
        }
        inputSize = ipvs.size();
        startThreads();
        //gather outputs
        for (int i = 0; i < inputSize; i++) {
            bw.write(ipvs.get(i) + " " + opvs[i]);
            bw.newLine();
        }
        br.close();
        bw.close();
    }

    private static void startThreads() {
        opvs = new String[inputSize];
        System.out.println("input size = " + inputSize);
        calculateBounds();
        //run threads
        MyThread[] threads = new MyThread[NUMBER_OF_THREAD];
        for (int i = 0; i < NUMBER_OF_THREAD; i++) {
            System.out.printf("from = %d , to = %d\n", fromIndex[i], toIndex[i]);
            threads[i] = new MyThread(fromIndex[i], toIndex[i], simulators[i], ipvs, opvs);
            threads[i].start();
        }
        for (int i = 0; i < NUMBER_OF_THREAD; i++) {
            try {
                threads[i].join();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void calculateBounds() {
        int segmentSize = (int) Math.ceil(1.0 * inputSize / NUMBER_OF_THREAD);
        int indexIncrement = segmentSize - 1;
        for (int i = 0, index = 0; i < NUMBER_OF_THREAD; i++, index += segmentSize) {
            fromIndex[i] = index;
            toIndex[i] = index + indexIncrement;
        }
        //make sure the last segment not out of bound
        int indexOfLastSegment = toIndex.length - 1;
        int indexOfLastInput = inputSize - 1;
        if (toIndex[indexOfLastSegment] > indexOfLastInput) {
            toIndex[indexOfLastSegment] = indexOfLastInput;
        }
    }

    private static void buildCircuit() throws Exception {
        for (int i = 0; i < NUMBER_OF_THREAD; i++) {
            simulators[i] = new CircuitSimulator();
        }
        BufferedReader br;
        if (BENCH_FILE.indexOf("UX.bench") != -1) {
            System.out.println("levelizing bench file");
            BenchLevelizer.levelize(BENCH_FILE, TEMP_FILE);
            br = new BufferedReader(new FileReader(TEMP_FILE));
        } else {
            br = new BufferedReader(new FileReader(BENCH_FILE));
        }
        String aLine = "";
        while ((aLine = br.readLine()) != null) {
            if (aLine.startsWith("#") || aLine.trim().length() == 0) {
                continue;
            }
            break;
        }
        //build input
        do {
            if (aLine.startsWith("INPUT")) {
                String[] tt = aLine.split("\\(");
                tt[1] = tt[1].replace(")", "");
                for (int i = 0; i < NUMBER_OF_THREAD; i++) {
                    simulators[i].addInputGate(tt[1]);
                }
            } else {
                break;
            }
        } while ((aLine = br.readLine()) != null);
        //build output
        do {
            if (aLine.startsWith("OUTPUT")) {
                String[] tt = aLine.split("\\(");
                tt[1] = tt[1].replace(")", "");
                for (int i = 0; i < NUMBER_OF_THREAD; i++) {
                    simulators[i].addOutputGate(tt[1]);
                }
            } else {
                //pass an empty line
                aLine = br.readLine();
                break;
            }
        } while ((aLine = br.readLine()) != null);
        //build other gates
        do {
            aLine = aLine.replace(" ", "");
            aLine = aLine.replace("=", ",");
            aLine = aLine.replace("(", ",");
            aLine = aLine.replace(")", "");
            String[] tt = aLine.split(",");
            for (int i = 0; i < NUMBER_OF_THREAD; i++) {
                simulators[i].addConfigString(tt);
            }
        } while ((aLine = br.readLine()) != null);
        br.close();
        for (int i = 0; i < NUMBER_OF_THREAD; i++) {
            simulators[i].buildCircuitByConfig();
        }
    }
}

class MyThread extends Thread {

    int fromIndex, toIndex;
    List<String> ipvs;
    String[] opvs;
    CircuitSimulator sim;

    MyThread() {

    }

    MyThread(int fromIndex, int toIndex, CircuitSimulator sim, List<String> ipvs, String[] opvs) {
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
        this.sim = sim;
        this.ipvs = ipvs;
        this.opvs = opvs;
    }

    @Override
    public void run() {
        for (int i = fromIndex; i <= toIndex; i++) {
            sim.fillInput(ipvs.get(i));
            opvs[i] = sim.getOuputs();
        }
    }
}
