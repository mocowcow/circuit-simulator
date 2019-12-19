package basicsim;


import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author chaoyu
 */
public class CompareCircuit extends JFrame {
    
    private JFrame frmae;
    private JButton ans, op, calc;
    private JTextArea ansPath, opPath, result;
    private JFileChooser chooser;
    private JScrollPane sp;
    private Container cp;
    private static String baseDir = "d:/ISCAS85/" ;
    
    public static void main(String[] args) {
        new CompareCircuit();
    }
    
    CompareCircuit() {
        this.frmae = new JFrame();
        this.setTitle("Compare Circuit Output with Answer");
        this.cp = this.getContentPane();
        this.setLayout(null);
        this.setBounds(200, 200, 1000, 650);
        
        this.chooser = new JFileChooser(baseDir);
        
        this.ansPath = new JTextArea();
        this.ansPath.setBounds(50, 150, 400, 66);
        setTextArea(ansPath);
        
        this.ans = new JButton();
        this.ans.setBounds(50, 50, 400, 100);
        this.ans.setText("Choose .ans file path");
        setButton(ans, ansPath);
        
        this.opPath = new JTextArea();
        this.opPath.setBounds(50, 350, 400, 66);
        setTextArea(opPath);
        
        this.op = new JButton();
        this.op.setBounds(50, 250, 400, 100);
        this.op.setText("Choose .op file path");
        setButton(op, opPath);
        
        this.calc = new JButton();
        this.cp.add(calc);
        this.calc.setBounds(50, 450, 400, 100);
        this.calc.setText("compare .op with .ans");
        this.calc.setFont(new Font(null, Font.BOLD, 30));
        this.calc.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    result.setText("");
                    BufferedReader br = new BufferedReader(new FileReader(ansPath.getText()));
                    BufferedReader br2 = new BufferedReader(new FileReader(opPath.getText()));
                    String ans = "";
                    String op = "";
                    int cnt = 1;
                    boolean allCorrect = true;
                    while ((ans = br.readLine()) != null) {
                        if ((op = br2.readLine()) == null) {
                            result.setText("not enough lines.");
                            allCorrect = false;
                            break;
                        }
                        if (!ans.equals(op)) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("[Line").append(cnt).append("]").append(", [The answer is] ").append(ans.split(" ")[1]).append(", [Your answer is] ").append(op.split(" ")[1]).append("\n");
                            result.append(sb.toString());
                            if (allCorrect) {
                                allCorrect = false;
                            }
                        } else {
                            if (cnt%1000==0) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("[Line").append(cnt).append("]").append(", Correct!!\n");
                                result.append(sb.toString());
                            }
                        }
                        cnt++;
                    }
                    if ((op = br2.readLine()) != null) {
                        result.setText("too more lines");
                        allCorrect = false;
                    }
                    if (allCorrect) {
                        result.append("All Correct!!");
                    }
                    br.close();
                    br2.close();
                } catch (FileNotFoundException ex) {
                    result.setText("can't find file");
                } catch (IOException ex) {
                    Logger.getLogger(CompareCircuit.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        this.sp = new JScrollPane();
        this.cp.add(sp);
        this.sp.setBounds(500, 50, 450, 500);
        this.result = new JTextArea();
        this.sp.setViewportView(result);
        this.result.setFont(new Font(null, Font.BOLD, 14));
        this.result.setEditable(false);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
    }
    
    private void setTextArea(JTextArea t) {
        this.cp.add(t);
        t.setFont(new Font(null, Font.BOLD, 14));
        t.setLineWrap(true);
        t.setEditable(false);
    }
    
    private void setButton(JButton b, JTextArea t) {
        this.cp.add(b);
        b.setFont(new Font(null, Font.BOLD, 30));
        b.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = chooser.showOpenDialog(CompareCircuit.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    t.setText(chooser.getSelectedFile().getAbsolutePath());
                }
            }
            
        });
    }
    
}
