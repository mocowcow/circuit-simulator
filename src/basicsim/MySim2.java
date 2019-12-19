package basicsim;

import java.util.*;
import java.io.*;

public class MySim2 {

    static HashMap<String, Integer> gateValue = new HashMap<String, Integer>();
    static ArrayList<String> inputList = new ArrayList<String>();
    static ArrayList<String> outputList = new ArrayList<String>();
    static ArrayList<String[]> gateList = new ArrayList<String[]>();

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        String benchFile = "d:/ISCAS85/bench/c7552.bench.txt";
        parseBenchFile(benchFile); // build data structure 
        String ipFile = "d:/ISCAS85/ip/c7552_10k_ip.txt";
        String opFile = "d:/ISCAS85/op/c7552_10k_op.txt";
       // simulation(ipFile, opFile); // generate the result
        System.out.printf("Total time=%.3f sec(s)\n",
                (System.currentTimeMillis() - start) / 1000.0);
    }

    // 模擬器函數
    public static void simulation(String ipFile, String opFile) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(ipFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(opFile));
        String ipvs = "", opvs = "";
        // 讀取輸入訊號檔案(xxx_ip.txt)，並進行邏輯閘運算，產生輸出。
        while ((ipvs = br.readLine()) != null) {
            ipvs = ipvs.trim(); // 如"01010011"的輸入
            fillInput(ipvs);  // 將ipvs分解為0, 1, …，填入電路的輸入腳
            for (int i = 0; i < gateList.size(); i++) { // evaluate gates one by one
                doSim(gateList.get(i)); // 依照每個邏輯閘特性，進行運算
            }
            opvs = gatherOutput(ipvs, bw); // 蒐集輸出，並寫至檔案
        }
        br.close();
        bw.close();
    }

    public static void fillInput(String ipvs) {
        if (ipvs.length() != inputList.size()) {
            throw new java.lang.RuntimeException("Input Size mismatch:" + ipvs.length() + "," + inputList.size());
        }
        for (int i = 0; i < ipvs.length(); i++) {
            if (ipvs.charAt(i) == '0') {
                gateValue.put(inputList.get(i), 0);
            } else {
                gateValue.put(inputList.get(i), 1);
            }
        }
    }

    // 各種邏輯閘的運算，使用if-else，效率??????
    public static void doSim(String[] gateInfo) {
        String gName = gateInfo[0];
        String gateType = gateInfo[1];
        int v = 0;
        switch (gateType.hashCode()) {
            case 96727:
                v = AND(gateInfo);
                break;
            case 3555:
                v = OR(gateInfo);
                break;
            case 3373737:
                v = NAND(gateInfo);
                break;
            case 109265:
                v = NOR(gateInfo);
                break;
            case 118875:
                v = XOR(gateInfo);
                break;
            case 3684185:
                v = XNOR(gateInfo);
                break;
            case 97907:
                v = BUF(gateInfo);
                break;
            case 109267:
                v = NOT(gateInfo);
                break;
            default:
                throw new java.lang.RuntimeException("Unknown Gate:" + gName + "," + gateType);
        }
        gateValue.put(gName, v);
    }

    public static String gatherOutput(String ipvs, BufferedWriter bw) throws Exception {
        String opvs = "";
        for (int i = 0; i < outputList.size(); i++) {
            opvs += gateValue.get(outputList.get(i));
        }
        bw.write(ipvs + " " + opvs);
        bw.newLine();
        return opvs;
    }

// ------ Gate Value Evaluation Fuctions ----------
    public static int AND(String[] gateInfo) {
        int v = 0;
        for (int i = 2; i < gateInfo.length; i++) {
            if ((v = gateValue.get(gateInfo[i])) == 0) {
                return 0;
            }
        }
        return 1;
    }

    public static int OR(String[] gateInfo) {
        int v = 0;
        for (int i = 2; i < gateInfo.length; i++) {
            if ((v = gateValue.get(gateInfo[i])) == 1) {
                return 1;
            }
        }
        return 0;
    }

    public static int XOR(String[] gateInfo) {
        int v1 = gateValue.get(gateInfo[2]), v2 = gateValue.get(gateInfo[3]);
        return (v1 == v2) ? 0 : 1;
    }

    public static int NAND(String[] gateInfo) {
        return (AND(gateInfo) == 0) ? 1 : 0;
    }

    public static int NOR(String[] gateInfo) {
        return (OR(gateInfo) == 0) ? 1 : 0;
    }

    public static int XNOR(String[] gateInfo) {
        return (XOR(gateInfo) == 0) ? 1 : 0;
    }

    public static int BUF(String[] gateInfo) {
        return gateValue.get(gateInfo[2]);
    }

    public static int NOT(String[] gateInfo) {
        return (BUF(gateInfo) == 0) ? 1 : 0;
    }

// ------ Parsing Circuit File and Build Data Structure ----------
// ****** 暴力法讀取並切割電路檔，產生資料結構，不妥，需要修改 ******
    public static void parseBenchFile(String benchFile) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(benchFile));
        String aLine = "";
        String gName = "", gType = "";

        while ((aLine = br.readLine()) != null) {
            if (aLine.startsWith("#") || aLine.trim().length() == 0) {
                continue;
            }
            if (aLine.startsWith("INPUT")) {
                String[] tt = aLine.split("\\(");
                gName = tt[1].replace(")", "");
                gateValue.put(gName, null);
                inputList.add(gName);
            } else if (aLine.startsWith("OUTPUT")) {
                String[] tt = aLine.split("\\(");
                gName = tt[1].replace(")", "");
                gateValue.put(gName, null);
                outputList.add(gName);
            } else {
                aLine = aLine.replace(" ", "");
                aLine = aLine.replace("=", ",");
                aLine = aLine.replace("(", ",");
                aLine = aLine.replace(")", "");
                String[] tt = aLine.split(",");
                gateValue.put(tt[0], null);
                gateList.add(tt);
            }
        }
        br.close();
    }
}
