
package hospital;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;


/**
 *
 * @author Ruth Hofmans
 */
public class reader {
    private char department;
    
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
    
    private int nurses;
    private int nurses1;
    private int nurses2;
    private double [] employmentRate;
    private int [] type;
    private String [] ID;
    
    private int minAss;
    private int maxAss;
    private int minConsAss;
    private int maxConsAss;
    private String weekend;
    private int weekendNr;
    private int [] minConsecPerShiftType;
    private int [] maxConsecPerShiftType;
    private int [] minNumberOfAssPerShiftMonth;
    private int [] maxNumberOfAssPerShiftMonth;
       
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
            
                
            setStartShiftA(new int [] {0,Integer.parseInt(lines[4][0]), Integer.parseInt(lines[5][0]),Integer.parseInt(lines[6][0])});
            setStartShiftB(new int [] {0,Integer.parseInt(lines[4][1]), Integer.parseInt(lines[5][1]),Integer.parseInt(lines[6][1])});
            setStartShiftC(new int [] {0,Integer.parseInt(lines[4][2]), Integer.parseInt(lines[5][2]),Integer.parseInt(lines[6][2])});
            setStartShiftD(new int [] {0,Integer.parseInt(lines[4][3]), Integer.parseInt(lines[5][3]),Integer.parseInt(lines[6][3])});
            
            setReqA(new int [] {0,Integer.parseInt(lines[9][0]), Integer.parseInt(lines[10][0]),Integer.parseInt(lines[11][0])});
            setReqB(new int [] {0,Integer.parseInt(lines[9][1]), Integer.parseInt(lines[10][1]),Integer.parseInt(lines[11][1])});
            setReqC(new int [] {0,Integer.parseInt(lines[9][2]), Integer.parseInt(lines[10][2]),Integer.parseInt(lines[11][2])});
            setReqD(new int [] {0,Integer.parseInt(lines[9][3]), Integer.parseInt(lines[10][3]),Integer.parseInt(lines[11][3])});
            
            budgetA = Integer.parseInt(lines[14][0]);
            budgetB = Integer.parseInt(lines[14][1]);
            budgetC = Integer.parseInt(lines[14][2]);
            budgetD = Integer.parseInt(lines[14][3]);
            
            //System.out.println(start[0] + "" + start[1] +""+start[2]);
            
            //System.out.print(getStartShiftA() + " "+ getStartShiftB() +" "+ getStartShiftC() +" "+ getStartShiftD() + "\n");
            //System.out.print(getReqA() +" "+ getReqB() +" "+ getReqC()+" "+ getReqD()+"\n");
            //System.out.print(budgetA +" "+budgetB+" "+budgetC+" "+budgetD+"\n");
            
               
     }
         catch (IOException e) {
			e.printStackTrace();
        }
     
}  

    public void readNurses(char department) {
        this.department = department;
                
        CSVReader reader;
        try{
            //reader = new CSVReader(new FileReader("C:\\Users\\julie.MATTIS\\OneDrive\\Documenten\\AOR\\C-input.csv"));
            reader = new CSVReader(new FileReader("C:\\Users\\Ruth Hofmans\\Desktop\\input example\\"+department+"nurse.csv"));
            String[][] lines= new String[50][50];
            String [] nextLine;
        int lineNumber = 0;
               
        while ((nextLine = reader.readNext()) != null) {
            
            String[] cells = nextLine[0].split(";");
            lines[lineNumber] = cells;
            
             lineNumber ++; 
            
           }
        
            nurses = Integer.parseInt(lines[0][1]);
            nurses1 = Integer.parseInt(lines[1][1]);
            nurses2 = Integer.parseInt(lines[2][1]);
            //System.out.println("totalNurses: "+nurses+" totalType1: "+nurses1+" totalType2: "+nurses2);
            ID = new String [(nurses)];
            for (int i=0; i<nurses; i++)
            {
                ID[i]=lines[(6+i)][0];
                //System.out.println("nurse "+(i+1)+" ID: "+ID[i] );
            }
            employmentRate = new double [(nurses)];
            for (int i=0; i<nurses; i++)
            {
                employmentRate[i]=Double.parseDouble(lines[(6+i)][15]);
                //System.out.println("nurse "+(i+1)+" employmentrate: "+employmentRate[i]);
            }
           //Employmentrate 1 komt eruit maar een ander kommagetal komt eruit als 0,0
            
            //Type werkt nog niet. Out of bound exception voor rij 17 
            /*
            type = new int [(nurses)];
            for (int i=0; i<nurses; i++)
            {
                type[i]=Integer.parseInt(lines[(6+i)][17]);
                System.out.println("nurse "+ (i+1) + " type: "+type[i]);
            }
            */
                         
     }
         catch (IOException e) {
			e.printStackTrace();
        }
     
} 
    public void readRosterRules(char department) {
        this.department = department;
        CSVReader reader;
        try{
            //reader = new CSVReader(new FileReader("C:\\Users\\julie.MATTIS\\OneDrive\\Documenten\\AOR\\C-input.csv"));
            reader = new CSVReader(new FileReader("C:\\Users\\Ruth Hofmans\\Desktop\\input example\\EconstrInput"+department+".csv"));
            String[][] lines= new String[50][50];
            String [] nextLine;
        int lineNumber = 0;
               
        while ((nextLine = reader.readNext()) != null) {
            
            String[] cells = nextLine[0].split(";");
            lines[lineNumber] = cells;
            
             lineNumber ++; 
            
           }
            setMinAss(Integer.parseInt(lines[3][0]));
            setMaxAss(Integer.parseInt(lines[3][1]));
            setMinConsAss(Integer.parseInt(lines[7][0]));
            setMaxConsAss(Integer.parseInt(lines[7][1]));
            setWeekend(lines[26][0]);
            setWeekendNr(Integer.parseInt(lines[27][0]));
            System.out.println(minAss +" "+ maxAss+" "+minConsAss+" "+maxConsAss+" "+weekend+" "+getWeekendNr());
            
            int s = getShifts();
            System.out.println(s); //s=0 hoe haal je aantal shiften op??
            s = 4;
            minConsecPerShiftType = new int [s];
            for (int i=1; i<s;i++)
            {
               minConsecPerShiftType[i]=Integer.parseInt(lines[10+i][0]);
               System.out.println("shift= "+(i)+"minConsecPerShift: "+minConsecPerShiftType[i]);
            }
            maxConsecPerShiftType = new int [s];
            for (int i=1; i<s;i++)
            {
               maxConsecPerShiftType[i]=Integer.parseInt(lines[10+i][1]);
               System.out.println("shift= "+(i)+"maxConsecPerShift: "+maxConsecPerShiftType[i]);
            }
            minNumberOfAssPerShiftMonth = new int [s];
            for (int i=1; i<s;i++)
            {
               minNumberOfAssPerShiftMonth[i]=Integer.parseInt(lines[18+i][0]);
               System.out.println("shift= "+(i)+"minAssPerShift: "+minNumberOfAssPerShiftMonth[i]);
            }
            maxNumberOfAssPerShiftMonth = new int [s];
            for (int i=1; i<s;i++)
            {
               maxNumberOfAssPerShiftMonth[i]=Integer.parseInt(lines[18+i][1]);
               System.out.println("shift= "+(i)+"minAssPerShift: "+maxNumberOfAssPerShiftMonth[i]);
            }
                                    
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
              
    public int getNurses()
    {
        return nurses;
    }
    public void setNurses(int nurses)
    {
        this.nurses = nurses;
    }
    public int getNurses1()
    {
        return nurses1;
    }
    public void setNurses1(int nurses1)
    {
        this.nurses1 = nurses1;
    }
    public int getNurses2()
    {
        return nurses2;
    }
    public void setNurses2(int nurses2)
    {
        this.nurses2 = nurses2;
    }
        
    public double[]getEmploymentRate()
    {
        return employmentRate;
    }
    public void setEmploymentRate(double []employmentRate)
    {
        this.employmentRate = employmentRate;
    }
    public int [] getType()
    {
        return type;
    }
    public void setType(int[]type)
    {
        this.type = type;
    }
    public String [] getID()
    {
        return ID;
    }
    public void setID(String []ID)
    {
        this.ID = ID;
    }

    /**
     * @param minAss the minAss to set
     */
    public int getMinAss()
    {
        return minAss;
    }
    public void setMinAss(int minAss) {
        this.minAss = minAss;
    }

    /**
     * @return the maxAss
     */
    public int getMaxAss() {
        return maxAss;
    }

    /**
     * @param maxAss the maxAss to set
     */
    public void setMaxAss(int maxAss) {
        this.maxAss = maxAss;
    }

    /**
     * @return the minConsAss
     */
    public int getMinConsAss() {
        return minConsAss;
    }

    /**
     * @param minConsAss the minConsAss to set
     */
    public void setMinConsAss(int minConsAss) {
        this.minConsAss = minConsAss;
    }

    /**
     * @return the maxConsAss
     */
    public int getMaxConsAss() {
        return maxConsAss;
    }

    /**
     * @param maxConsAss the maxConsAss to set
     */
    public void setMaxConsAss(int maxConsAss) {
        this.maxConsAss = maxConsAss;
    }

    /**
     * @return the weekend
     */
    public String getWeekend() {
        return weekend;
    }

    /**
     * @param weekend the weekend to set
     */
    public void setWeekend(String weekend) {
        this.weekend = weekend;
    }

    /**
     * @return the minConsecPerShiftType
     */
    public int[] getMinConsecPerShiftType() {
        return minConsecPerShiftType;
    }

    /**
     * @param minConsecPerShiftType the minConsecPerShiftType to set
     */
    public void setMinConsecPerShiftType(int[] minConsecPerShiftType) {
        this.minConsecPerShiftType = minConsecPerShiftType;
    }

    /**
     * @return the maxConsecPerShiftType
     */
    public int[] getMaxConsecPerShiftType() {
        return maxConsecPerShiftType;
    }

    /**
     * @param maxConsecPerShiftType the maxConsecPerShiftType to set
     */
    public void setMaxConsecPerShiftType(int[] maxConsecPerShiftType) {
        this.maxConsecPerShiftType = maxConsecPerShiftType;
    }

    /**
     * @return the minNumberOfAssPerShiftType
     */
    public int[] getMinNumberOfAssPerShiftMonth() {
        return minNumberOfAssPerShiftMonth;
    }

    /**
     * @param minNumberOfAssPerShiftType the minNumberOfAssPerShiftType to set
     */
    public void setMinNumberOfAssPerShiftMonth(int[] minNumberOfAssPerShiftMonth) {
        this.minNumberOfAssPerShiftMonth = minNumberOfAssPerShiftMonth;
    }

    /**
     * @return the maxNumberOfAssPerShiftType
     */
    public int[] getMaxNumberOfAssPerShiftMonth() {
        return maxNumberOfAssPerShiftMonth;
    }

    /**
     * @param maxNumberOfAssPerShiftType the maxNumberOfAssPerShiftType to set
     */
    public void setMaxNumberOfAssPerShiftMonth(int[] maxNumberOfAssPerShiftMonth) {
        this.maxNumberOfAssPerShiftMonth = maxNumberOfAssPerShiftMonth;
    }

    /**
     * @return the weekendNr
     */
    public int getWeekendNr() {
        return weekendNr;
    }

    /**
     * @param weekendNr the weekendNr to set
     */
    public void setWeekendNr(int weekendNr) {
        this.weekendNr = weekendNr;
    }
            
    
    
}


    