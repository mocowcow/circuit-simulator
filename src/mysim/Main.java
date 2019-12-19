package mysim;

import java.io.*;

public class Main {

    static CircuitSimulator simulator = new CircuitSimulator();
    static String benchFile = "d:/ISCAS85/bench/c432.bench.txt";
    static String ipFile = "d:/ISCAS85/ip/c432_1m_ip.txt";
    static String opFile = ipFile.replaceAll("ip", "op");

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        buildCircuit();
        simulation();
        System.out.printf("Total time=%.3f sec(s)\n",
                (System.currentTimeMillis() - start) / 1000.0);
    }

    public static void simulation() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(ipFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(opFile));
        String ipvs = "", opvs = "";
        // 讀取輸入訊號檔案(xxx_ip.txt)，並進行邏輯閘運算，產生輸出。
        while ((ipvs = br.readLine()) != null) {
            ipvs = ipvs.trim(); // 如"01010011"的輸入
            simulator.fillInput(ipvs);
            opvs = simulator.getOuputs();
            bw.write(ipvs + " " + opvs);
            bw.newLine();
        }
        br.close();
        bw.close();
    }

    public static void buildCircuit() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(benchFile));
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
                simulator.addInputGate(tt[1]);
            } else {
                break;
            }
        } while ((aLine = br.readLine()) != null);
        //build output
        do {
            if (aLine.startsWith("OUTPUT")) {
                String[] tt = aLine.split("\\(");
                tt[1] = tt[1].replace(")", "");
                simulator.addOutputGate(tt[1]);
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
            simulator.addConfigString(tt);
        } while ((aLine = br.readLine()) != null);
        br.close();
        simulator.buildCircuitByConfig();
    }
}
