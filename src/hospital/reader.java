
package hospital;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.opencsv.CSVReader;
import java.util.ArrayList;


/**
 *
 * @author Ruth Hofmans
 */
public class reader {
    
    private int length;
    private int shifts;
    private int [] startShiftA;
    private int [] startShiftB;
    private int [] startShiftC;
    private int [] startShiftD;
    private int [] reqA;
    private int [] reqB;
    private int [] reqC;
    private int [] reqD;
    private int budgetA;
    private int budgetB;
    private int budgetC;
    private int budgetD;
    
    public reader(){
        
    }
    
    public void readCaseC() {

        CSVReader reader;
        try{
            //reader = new CSVReader(new FileReader("C:\\Users\\julie.MATTIS\\OneDrive\\Documenten\\AOR\\C-input.csv"));
            reader = new CSVReader(new FileReader("C:\\Users\\Ruth Hofmans\\Desktop\\input example\\C input.csv"));
            String[][] lines= new String[20][20];
            String [] nextLine;
        int lineNumber = 0;
               
        while ((nextLine = reader.readNext()) != null) {
            
            String[] cells = nextLine[0].split(";");
            lines[lineNumber] = cells;
            
             lineNumber ++; 
            
           }
        
            length = Integer.parseInt(lines[1][1]);
            //System.out.print("length: "+length + "\n");
            shifts = Integer.parseInt(lines[1][0]);
            //System.out.print("shifts: "+shifts + "\n");
            
                    
            setStartShiftA(new int [] {Integer.parseInt(lines[4][0]), Integer.parseInt(lines[5][0]),Integer.parseInt(lines[6][0])});
            setStartShiftB(new int [] {Integer.parseInt(lines[4][1]), Integer.parseInt(lines[5][1]),Integer.parseInt(lines[6][1])});
            setStartShiftC(new int [] {Integer.parseInt(lines[4][2]), Integer.parseInt(lines[5][2]),Integer.parseInt(lines[6][2])});
            setStartShiftD(new int [] {Integer.parseInt(lines[4][3]), Integer.parseInt(lines[5][3]),Integer.parseInt(lines[6][3])});
            
            setReqA(new int [] {Integer.parseInt(lines[9][0]), Integer.parseInt(lines[10][0]),Integer.parseInt(lines[11][0])});
            setReqB(new int [] {Integer.parseInt(lines[9][1]), Integer.parseInt(lines[10][1]),Integer.parseInt(lines[11][1])});
            setReqC(new int [] {Integer.parseInt(lines[9][2]), Integer.parseInt(lines[10][2]),Integer.parseInt(lines[11][2])});
            setReqD(new int [] {Integer.parseInt(lines[9][3]), Integer.parseInt(lines[10][3]),Integer.parseInt(lines[11][3])});
            
            budgetA = Integer.parseInt(lines[14][0]);
            budgetB = Integer.parseInt(lines[14][1]);
            budgetC = Integer.parseInt(lines[14][2]);
            budgetD = Integer.parseInt(lines[14][3]);
            
            //System.out.print(getStartShiftA() + " "+ getStartShiftB() +" "+ getStartShiftC() +" "+ getStartShiftD() + "\n");
            //System.out.print(getReqA() +" "+ getReqB() +" "+ getReqC()+" "+ getReqD()+"\n");
            //System.out.print(budgetA +" "+budgetB+" "+budgetC+" "+budgetD+"\n");
            
               
     }
         catch (IOException e) {
			e.printStackTrace();
        }
     
}  

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return the shifts
     */
    public int getShifts() {
        return shifts;
    }

    /**
     * @param shifts the shifts to set
     */
    public void setShifts(int shifts) {
        this.shifts = shifts;
    }

    /**
     * @return the startShiftA
     */
    public int[] getStartShiftA() {
        return startShiftA;
    }

    /**
     * @param startShiftA the startShiftA to set
     */
    public void setStartShiftA(int[] startShiftA) {
        this.startShiftA = startShiftA;
    }

    /**
     * @return the startShiftB
     */
    public int[] getStartShiftB() {
        return startShiftB;
    }

    /**
     * @param startShiftB the startShiftB to set
     */
    public void setStartShiftB(int[] startShiftB) {
        this.startShiftB = startShiftB;
    }

    /**
     * @return the startShiftC
     */
    public int[] getStartShiftC() {
        return startShiftC;
    }

    /**
     * @param startShiftC the startShiftC to set
     */
    public void setStartShiftC(int[] startShiftC) {
        this.startShiftC = startShiftC;
    }

    /**
     * @return the startShiftD
     */
    public int[] getStartShiftD() {
        return startShiftD;
    }

    /**
     * @param startShiftD the startShiftD to set
     */
    public void setStartShiftD(int[] startShiftD) {
        this.startShiftD = startShiftD;
    }

    /**
     * @return the reqA
     */
    public int[] getReqA() {
        return reqA;
    }

    /**
     * @param reqA the reqA to set
     */
    public void setReqA(int[] reqA) {
        this.reqA = reqA;
    }

    /**
     * @return the reqB
     */
    public int[] getReqB() {
        return reqB;
    }

    /**
     * @param reqB the reqB to set
     */
    public void setReqB(int[] reqB) {
        this.reqB = reqB;
    }

    /**
     * @return the reqC
     */
    public int[] getReqC() {
        return reqC;
    }

    /**
     * @param reqC the reqC to set
     */
    public void setReqC(int[] reqC) {
        this.reqC = reqC;
    }

    /**
     * @return the reqD
     */
    public int[] getReqD() {
        return reqD;
    }

    /**
     * @param reqD the reqD to set
     */
    public void setReqD(int[] reqD) {
        this.reqD = reqD;
    }

    /**
     * @return the budgetA
     */
    public int getBudgetA() {
        return budgetA;
    }

    /**
     * @param budgetA the budgetA to set
     */
    public void setBudgetA(int budgetA) {
        this.budgetA = budgetA;
    }

    /**
     * @return the budgetB
     */
    public int getBudgetB() {
        return budgetB;
    }

    /**
     * @param budgetB the budgetB to set
     */
    public void setBudgetB(int budgetB) {
        this.budgetB = budgetB;
    }

    /**
     * @return the budgetC
     */
    public int getBudgetC() {
        return budgetC;
    }

    /**
     * @param budgetC the budgetC to set
     */
    public void setBudgetC(int budgetC) {
        this.budgetC = budgetC;
    }

    /**
     * @return the budgetD
     */
    public int getBudgetD() {
        return budgetD;
    }

    /**
     * @param budgetD the budgetD to set
     */
    public void setBudgetD(int budgetD) {
        this.budgetD = budgetD;
    }
    
    
    
}


    