
package hospital;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;


/**
 *
 * @author Ruth Hofmans
 */
public class reader {
    
    //Lijn 291+292 manueel ingevuld voor test AANPASSEN!!!!
    
    private char department;
    
    private final int DAYS = 28; 
    private final int SHIFTS = 5;           // aan te passen
    private int[] shift = new int[SHIFTS];
    
    private int length;
    private int shifts;
    private int [] startShiftA;
    private int [] startShiftB;
    private int [] startShiftC;
    private int [] startShiftD;
    private int [] reqA = new int [SHIFTS];
    private int [] reqB = new int [SHIFTS];;
    private int [] reqC = new int [SHIFTS];;
    private int [] reqD = new int [SHIFTS];;
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
    
    private int numberOfRostersType1;
    private int numberOfRostersType2;
    private int [][] cyclicRostersType1 = new int [50][50];
    private int [][] cyclicRostersType2 = new int [50][50];
    private int [] reqFTERosterType1;
    private int [] reqFTERosterType2;
    
    private int reqShiftType;
    
    private double wageType1Early9Week;
    private double wageType1Early9Weekend;
    private double wageType1Day9Week;
    private double wageType1Day9Weekend;
    private double wageType1Late9Week;
    private double wageType1Late9Weekend;
    private double wageType1Night9Week;
    private double wageType1Night9Weekend;
    private double wageType1Early12Week;
    private double wageType1Early12Weekend;
    private double wageType1Day12Week;
    private double wageType1Day12Weekend;
    private double wageType1Late12Week;
    private double wageType1Late12Weekend;
    private double wageType1Night12Week;
    private double wageType1Night12Weekend;
    private double wageType2Early9Week;
    private double wageType2Early9Weekend;
    private double wageType2Day9Week;
    private double wageType2Day9Weekend;
    private double wageType2Late9Week;
    private double wageType2Late9Weekend;
    private double wageType2Night9Week;
    private double wageType2Night9Weekend;
    private double wageType2Early12Week;
    private double wageType2Early12Weekend;
    private double wageType2Day12Week;
    private double wageType2Day12Weekend;
    private double wageType2Late12Week;
    private double wageType2Late12Weekend;
    private double wageType2Night12Week;
    private double wageType2Night12Weekend;
    
    private int preference;
    
    private double [][] wageCost = new double [50][50];
    
    private double wageCost2;
       
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
            
            setReqA(new int [] {0,Integer.parseInt(lines[11][0]), Integer.parseInt(lines[12][0]),Integer.parseInt(lines[13][0])});
            setReqB(new int [] {0,Integer.parseInt(lines[11][1]), Integer.parseInt(lines[12][1]),Integer.parseInt(lines[13][1])});
            setReqC(new int [] {0,Integer.parseInt(lines[11][2]), Integer.parseInt(lines[12][2]),Integer.parseInt(lines[13][2])});
            setReqD(new int [] {0,Integer.parseInt(lines[11][3]), Integer.parseInt(lines[12][3]),Integer.parseInt(lines[13][3])});
            
            budgetA = Integer.parseInt(lines[18][0]);
            budgetB = Integer.parseInt(lines[18][1]);
            budgetC = Integer.parseInt(lines[18][2]);
            budgetD = Integer.parseInt(lines[18][3]);
            
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
            //reader = new CSVReader(new FileReader("C:\\Users\\julie.MATTIS\\OneDrive\\Documenten\\AOR\\Anurse.csv"));
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
                       
            type = new int [(nurses)];
            for (int i=0; i<nurses; i++)
            {
                type[i]=Integer.parseInt(lines[(6+i)][16]);
                //System.out.println("nurse "+ (i+1) + " type: "+type[i]);
            }
            
                         
     }
         catch (IOException e) {
			e.printStackTrace();
        }
     
} 
    public void readRosterRules(char department) {
        this.department = department;
        CSVReader reader;
        try{
            //reader = new CSVReader(new FileReader("C:\\Users\\julie.MATTIS\\OneDrive\\Documenten\\AOR\\EconstrInputA.csv"));
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
            //System.out.println(minAss +" "+ maxAss+" "+minConsAss+" "+maxConsAss+" "+weekend+" "+getWeekendNr());
            
            int s = getShifts();
            //System.out.println(s); //s=0 hoe haal je aantal shiften op??
            s = 4;
            minConsecPerShiftType = new int [s];
            for (int i=1; i<s;i++)
            {
               minConsecPerShiftType[i]=Integer.parseInt(lines[10+i][0]);
               //System.out.println("shift= "+(i)+"minConsecPerShift: "+minConsecPerShiftType[i]);
            }
            maxConsecPerShiftType = new int [s];
            for (int i=1; i<s;i++)
            {
               maxConsecPerShiftType[i]=Integer.parseInt(lines[10+i][1]);
               //System.out.println("shift= "+(i)+"maxConsecPerShift: "+maxConsecPerShiftType[i]);
            }
            minNumberOfAssPerShiftMonth = new int [s];
            for (int i=1; i<s;i++)
            {
               minNumberOfAssPerShiftMonth[i]=Integer.parseInt(lines[18+i][0]);
               //System.out.println("shift= "+(i)+"minAssPerShift: "+minNumberOfAssPerShiftMonth[i]);
            }
            maxNumberOfAssPerShiftMonth = new int [s];
            for (int i=1; i<s;i++)
            {
               maxNumberOfAssPerShiftMonth[i]=Integer.parseInt(lines[18+i][1]);
               //System.out.println("shift= "+(i)+"minAssPerShift: "+maxNumberOfAssPerShiftMonth[i]);
            }
                                    
        }
         catch (IOException e) {
			e.printStackTrace();
        }
     
}
    
    public void readRosters(char department){
        
        this.department = department;
        CSVReader reader;
        
        try{
            //reader = new CSVReader(new FileReader("C:\\Users\\julie.MATTIS\\OneDrive\\Documenten\\AOR\\D-input.csv"));
            reader = new CSVReader(new FileReader("C:\\Users\\Ruth Hofmans\\Desktop\\input example\\"+department+"roster.csv"));
            String[][] lines= new String[1000][1000];
            String [] nextLine;
        int lineNumber = 0;
               
        while ((nextLine = reader.readNext()) != null) {
            
            String[] cells = nextLine[0].split(";");
            lines[lineNumber] = cells;
            
             lineNumber ++; 
            
           }
        
        readNurses(department);
        //numberOfRostersType1 = nurses1;
        //numberOfRostersType2 = nurses2;
        numberOfRostersType1 = 5;
        numberOfRostersType2 = 10; // zelf bepaald voor test
                
        for (int s=0;s<numberOfRostersType1;s++)
		{
			//reqFTERosterType1[s]= Integer.parseInt(lines[s+4]);
			for (int d=0;d<DAYS;d++){
			
                                //System.out.println(Integer.parseInt(lines[s+3][d]));
				cyclicRostersType1[s][d]=Integer.parseInt(lines[s+3][d]); 
				//System.out.println("Type 1, Cyclic roster : " + (s+1)+  " for department " +department+ " on day " + (d+1) 
				//		+ " is " + getCyclicRostersType1()[s][d] + " and there are " + "reqFTERosterType1[r]" + " FTE required.") ;
			}
		}
        for (int s=numberOfRostersType1;s<numberOfRostersType1+numberOfRostersType2;s++)
		{
			//reqFTERosterType1[s]= Integer.parseInt(lines[s+4]);
			for (int d=0;d<DAYS;d++)
			{
				cyclicRostersType1[s][d]=Integer.parseInt(lines[s+3][d]); 
				//System.out.println("Type 2, Cyclic roster : " + (s+1)+  " for department " +department+ " on day " + (d+1) 
				//		+ " is " + getCyclicRostersType1()[s][d] + " and there are " + "reqFTERosterType1[r]" + " FTE required.") ;
			}
		}
    }
        catch (IOException e) {
			e.printStackTrace();
        }
        
    }
    
    public void readWages(){
        CSVReader reader;
        
        try{
            reader = new CSVReader(new FileReader("C:\\Users\\julie.MATTIS\\OneDrive\\Documenten\\AOR\\Wages.csv"));
            //reader = new CSVReader(new FileReader("C:\\Users\\Ruth Hofmans\\Desktop\\input example\\Wages.csv"));
            String[][] lines= new String[1000][1000];
            String [] nextLine;
        int lineNumber = 0;
               
        while ((nextLine = reader.readNext()) != null) {
            
            String[] cells = nextLine[0].split(";");
            lines[lineNumber] = cells;
            
             lineNumber ++; 
            
           }
        
            setWageType1Early9Week(Double.parseDouble(lines[1][2]));
            setWageType1Early9Weekend(Double.parseDouble(lines[1][3]));
            setWageType1Day9Week(Double.parseDouble(lines[2][2]));
            setWageType1Day9Weekend(Double.parseDouble(lines[2][3]));
            setWageType1Late9Week(Double.parseDouble(lines[3][2]));
            setWageType1Late9Weekend(Double.parseDouble(lines[3][3]));
            setWageType1Night9Week(Double.parseDouble(lines[4][2]));
            setWageType1Night9Weekend(Double.parseDouble(lines[4][3]));
            setWageType1Early12Week(Double.parseDouble(lines[5][2]));
            setWageType1Early12Weekend(Double.parseDouble(lines[5][3]));
            setWageType1Day12Week(Double.parseDouble(lines[6][2]));
            setWageType1Day12Weekend(Double.parseDouble(lines[6][3]));
            setWageType1Late12Week(Double.parseDouble(lines[7][2]));
            setWageType1Late12Weekend(Double.parseDouble(lines[7][3]));
            setWageType1Night12Week(Double.parseDouble(lines[8][2]));
            setWageType1Night12Weekend(Double.parseDouble(lines[8][3]));
            setWageType2Early9Week(Double.parseDouble(lines[9][2]));
            setWageType2Early9Weekend(Double.parseDouble(lines[9][3]));
            setWageType2Day9Week(Double.parseDouble(lines[10][2]));
            setWageType2Day9Weekend(Double.parseDouble(lines[10][3]));
            setWageType2Late9Week(Double.parseDouble(lines[11][2]));
            setWageType2Late9Weekend(Double.parseDouble(lines[11][3]));
            setWageType2Night9Week(Double.parseDouble(lines[12][2]));
            setWageType2Night9Weekend(Double.parseDouble(lines[12][3]));
            setWageType2Early12Week(Double.parseDouble(lines[13][2]));
            setWageType2Early12Weekend(Double.parseDouble(lines[13][3]));
            setWageType2Day12Week(Double.parseDouble(lines[14][2]));
            setWageType2Day12Weekend(Double.parseDouble(lines[14][3]));
            setWageType2Late12Week(Double.parseDouble(lines[15][2]));
            setWageType2Late12Weekend(Double.parseDouble(lines[15][3]));
            setWageType2Night12Week(Double.parseDouble(lines[16][2]));
            setWageType2Night12Weekend(Double.parseDouble(lines[16][3]));
        
        }
        catch (IOException e) {
			e.printStackTrace();
        }
    }
    
    public double readWages2(int r, int k){
        CSVReader reader;
        
        try{
            //reader = new CSVReader(new FileReader("C:\\Users\\julie.MATTIS\\OneDrive\\Documenten\\AOR\\Wages9hr.csv"));
            reader = new CSVReader(new FileReader("C:\\Users\\Ruth Hofmans\\Desktop\\input example\\Wages.csv"));
            String[][] lines= new String[1000][1000];
            String [] nextLine;
        int lineNumber = 0;
               
        while ((nextLine = reader.readNext()) != null) {
            
            String[] cells = nextLine[0].split(";");
            lines[lineNumber] = cells;
            
             lineNumber ++; 
            
           }
        
        wageCost2 = Double.parseDouble(lines[r][k]);
        
        /*for (int i = 0; i < 9; i++){
            for (int j = 2; j < 4; j++){
                wageCost[i][j] = Double.parseDouble(lines[r][k]);
                for()
            }
        }*/
        }
        catch (IOException e) {
			e.printStackTrace();
        }
        
        return wageCost2;
        
    }
    
    public int readPreference(int n, int d, int s, char department){
        this.department = department;
        CSVReader reader;
        
        try{
            //reader = new CSVReader(new FileReader("C:\\Users\\julie.MATTIS\\OneDrive\\Documenten\\AOR\\Wages.csv"));
            reader = new CSVReader(new FileReader("C:\\Users\\Ruth Hofmans\\Desktop\\input example\\"+department+"pref.csv"));
            String[][] lines= new String[1000][1000];
            String [] nextLine;
        int lineNumber = 0;
               
        while ((nextLine = reader.readNext()) != null) {
            
            String[] cells = nextLine[0].split(";");
            lines[lineNumber] = cells;
            
             lineNumber ++; 
            
           }
            int line;
            int column;
                    
            if (s==0) // als free shift, dan moeten we kijken naar de laatste column en niet de eerste
            {
            line = n+6;
            column = (1+(5*(d)))+(s+4);
            }
            else
            {
             line = n+6;
             column = (1+(5*(d)))+(s-1);
            }
            setPreference(Integer.parseInt(lines[line][column]));
            System.out.println("pref: "+getPreference());
            return preference;
        }
        
        catch (IOException e) {
			e.printStackTrace();
        }
        return -1;
    }
    public int readRequirements(int t, int s, char department){
        this.department = department;
        CSVReader reader;
        
        try{
            //reader = new CSVReader(new FileReader("C:\\Users\\julie.MATTIS\\OneDrive\\Documenten\\AOR\\Wages.csv"));
            reader = new CSVReader(new FileReader("C:\\Users\\Ruth Hofmans\\Desktop\\input example\\"+department+"req.csv"));
            String[][] lines= new String[1000][1000];
            String [] nextLine;
        int lineNumber = 0;
               
        while ((nextLine = reader.readNext()) != null) {
            
            String[] cells = nextLine[0].split(";");
            lines[lineNumber] = cells;
            
             lineNumber ++; 
            
           }
            int line;
            int column;
            
            column = t;
            line = s+3;
            //System.out.println(column+" "+line);
            //System.out.println(lines[line][column]);
            
            setReqShiftType(Integer.parseInt(lines[line][column]));
            //System.out.println(getReqShiftType());
            return reqShiftType;
        }
        
        catch (IOException e) {
			e.printStackTrace();
        }
        return -1;
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

    /**
     * @return the numberOfRostersType1
     */
    public int getNumberOfRostersType1() {
        return numberOfRostersType1;
    }

    /**
     * @param numberOfRostersType1 the numberOfRostersType1 to set
     */
    public void setNumberOfRostersType1(int numberOfRostersType1) {
        this.numberOfRostersType1 = numberOfRostersType1;
    }

    /**
     * @return the numberOfRostersType2
     */
    public int getNumberOfRostersType2() {
        return numberOfRostersType2;
    }

    /**
     * @param numberOfRostersType2 the numberOfRostersType2 to set
     */
    public void setNumberOfRostersType2(int numberOfRostersType2) {
        this.numberOfRostersType2 = numberOfRostersType2;
    }

    /**
     * @return the cyclicRostersType1
     */
    public int[][] getCyclicRostersType1() {
        return cyclicRostersType1;
    }

    /**
     * @param cyclicRostersType1 the cyclicRostersType1 to set
     */
    public void setCyclicRostersType1(int[][] cyclicRostersType1) {
        this.cyclicRostersType1 = cyclicRostersType1;
    }

    /**
     * @return the cyclicRostersType2
     */
    public int[][] getCyclicRostersType2() {
        return cyclicRostersType2;
    }

    /**
     * @param cyclicRostersType2 the cyclicRostersType2 to set
     */
    public void setCyclicRostersType2(int[][] cyclicRostersType2) {
        this.cyclicRostersType2 = cyclicRostersType2;
    }

    /**
     * @return the reqFTERosterType1
     */
    public int[] getReqFTERosterType1() {
        return reqFTERosterType1;
    }

    /**
     * @param reqFTERosterType1 the reqFTERosterType1 to set
     */
    public void setReqFTERosterType1(int[] reqFTERosterType1) {
        this.reqFTERosterType1 = reqFTERosterType1;
    }

    /**
     * @return the reqFTERosterType2
     */
    public int[] getReqFTERosterType2() {
        return reqFTERosterType2;
    }

    /**
     * @param reqFTERosterType2 the reqFTERosterType2 to set
     */
    public void setReqFTERosterType2(int[] reqFTERosterType2) {
        this.reqFTERosterType2 = reqFTERosterType2;
    }

    /**
     * @return the wageType1Early9Week
     */
    public double getWageType1Early9Week() {
        return wageType1Early9Week;
    }

    /**
     * @param wageType1Early9Week the wageType1Early9Week to set
     */
    public void setWageType1Early9Week(double wageType1Early9Week) {
        this.wageType1Early9Week = wageType1Early9Week;
    }

    /**
     * @return the wageType1Early9Weekend
     */
    public double getWageType1Early9Weekend() {
        return wageType1Early9Weekend;
    }

    /**
     * @param wageType1Early9Weekend the wageType1Early9Weekend to set
     */
    public void setWageType1Early9Weekend(double wageType1Early9Weekend) {
        this.wageType1Early9Weekend = wageType1Early9Weekend;
    }

    /**
     * @return the wageType1Day9Week
     */
    public double getWageType1Day9Week() {
        return wageType1Day9Week;
    }

    /**
     * @param wageType1Day9Week the wageType1Day9Week to set
     */
    public void setWageType1Day9Week(double wageType1Day9Week) {
        this.wageType1Day9Week = wageType1Day9Week;
    }

    /**
     * @return the wageType1Day9Weekend
     */
    public double getWageType1Day9Weekend() {
        return wageType1Day9Weekend;
    }

    /**
     * @param wageType1Day9Weekend the wageType1Day9Weekend to set
     */
    public void setWageType1Day9Weekend(double wageType1Day9Weekend) {
        this.wageType1Day9Weekend = wageType1Day9Weekend;
    }

    /**
     * @return the wageType1Late9Week
     */
    public double getWageType1Late9Week() {
        return wageType1Late9Week;
    }

    /**
     * @param wageType1Late9Week the wageType1Late9Week to set
     */
    public void setWageType1Late9Week(double wageType1Late9Week) {
        this.wageType1Late9Week = wageType1Late9Week;
    }

    /**
     * @return the wageType1Late9Weekend
     */
    public double getWageType1Late9Weekend() {
        return wageType1Late9Weekend;
    }

    /**
     * @param wageType1Late9Weekend the wageType1Late9Weekend to set
     */
    public void setWageType1Late9Weekend(double wageType1Late9Weekend) {
        this.wageType1Late9Weekend = wageType1Late9Weekend;
    }

    /**
     * @return the wageType1Night9Week
     */
    public double getWageType1Night9Week() {
        return wageType1Night9Week;
    }

    /**
     * @param wageType1Night9Week the wageType1Night9Week to set
     */
    public void setWageType1Night9Week(double wageType1Night9Week) {
        this.wageType1Night9Week = wageType1Night9Week;
    }

    /**
     * @return the wageType1NightWeekend
     */
    public double getWageType1Night9Weekend() {
        return wageType1Night9Weekend;
    }

    /**
     * @param wageType1NightWeekend the wageType1NightWeekend to set
     */
    public void setWageType1Night9Weekend(double wageType1Night9Weekend) {
        this.wageType1Night9Weekend = wageType1Night9Weekend;
    }

    /**
     * @return the wageType1Early12Week
     */
    public double getWageType1Early12Week() {
        return wageType1Early12Week;
    }

    /**
     * @param wageType1Early12Week the wageType1Early12Week to set
     */
    public void setWageType1Early12Week(double wageType1Early12Week) {
        this.wageType1Early12Week = wageType1Early12Week;
    }

    /**
     * @return the wageType1Early12Weekend
     */
    public double getWageType1Early12Weekend() {
        return wageType1Early12Weekend;
    }

    /**
     * @param wageType1Early12Weekend the wageType1Early12Weekend to set
     */
    public void setWageType1Early12Weekend(double wageType1Early12Weekend) {
        this.wageType1Early12Weekend = wageType1Early12Weekend;
    }

    /**
     * @return the wageType1Day12Week
     */
    public double getWageType1Day12Week() {
        return wageType1Day12Week;
    }

    /**
     * @param wageType1Day12Week the wageType1Day12Week to set
     */
    public void setWageType1Day12Week(double wageType1Day12Week) {
        this.wageType1Day12Week = wageType1Day12Week;
    }

    /**
     * @return the wageType1Day12Weekend
     */
    public double getWageType1Day12Weekend() {
        return wageType1Day12Weekend;
    }

    /**
     * @param wageType1Day12Weekend the wageType1Day12Weekend to set
     */
    public void setWageType1Day12Weekend(double wageType1Day12Weekend) {
        this.wageType1Day12Weekend = wageType1Day12Weekend;
    }

    /**
     * @return the wageType1Late12Week
     */
    public double getWageType1Late12Week() {
        return wageType1Late12Week;
    }

    /**
     * @param wageType1Late12Week the wageType1Late12Week to set
     */
    public void setWageType1Late12Week(double wageType1Late12Week) {
        this.wageType1Late12Week = wageType1Late12Week;
    }

    /**
     * @return the wageType1Late12Weekend
     */
    public double getWageType1Late12Weekend() {
        return wageType1Late12Weekend;
    }

    /**
     * @param wageType1Late12Weekend the wageType1Late12Weekend to set
     */
    public void setWageType1Late12Weekend(double wageType1Late12Weekend) {
        this.wageType1Late12Weekend = wageType1Late12Weekend;
    }

    /**
     * @return the wageType1Night12Week
     */
    public double getWageType1Night12Week() {
        return wageType1Night12Week;
    }

    /**
     * @param wageType1Night12Week the wageType1Night12Week to set
     */
    public void setWageType1Night12Week(double wageType1Night12Week) {
        this.wageType1Night12Week = wageType1Night12Week;
    }

    /**
     * @return the wageType1Night12Weekend
     */
    public double getWageType1Night12Weekend() {
        return wageType1Night12Weekend;
    }

    /**
     * @param wageType1Night12Weekend the wageType1Night12Weekend to set
     */
    public void setWageType1Night12Weekend(double wageType1Night12Weekend) {
        this.wageType1Night12Weekend = wageType1Night12Weekend;
    }

    /**
     * @return the wageType2Early9Week
     */
    public double getWageType2Early9Week() {
        return wageType2Early9Week;
    }

    /**
     * @param wageType2Early9Week the wageType2Early9Week to set
     */
    public void setWageType2Early9Week(double wageType2Early9Week) {
        this.wageType2Early9Week = wageType2Early9Week;
    }

    /**
     * @return the wageType2Early9Weekend
     */
    public double getWageType2Early9Weekend() {
        return wageType2Early9Weekend;
    }

    /**
     * @param wageType2Early9Weekend the wageType2Early9Weekend to set
     */
    public void setWageType2Early9Weekend(double wageType2Early9Weekend) {
        this.wageType2Early9Weekend = wageType2Early9Weekend;
    }

    /**
     * @return the wageType2Day9Week
     */
    public double getWageType2Day9Week() {
        return wageType2Day9Week;
    }

    /**
     * @param wageType2Day9Week the wageType2Day9Week to set
     */
    public void setWageType2Day9Week(double wageType2Day9Week) {
        this.wageType2Day9Week = wageType2Day9Week;
    }

    /**
     * @return the wageType2Day9Weekend
     */
    public double getWageType2Day9Weekend() {
        return wageType2Day9Weekend;
    }

    /**
     * @param wageType2Day9Weekend the wageType2Day9Weekend to set
     */
    public void setWageType2Day9Weekend(double wageType2Day9Weekend) {
        this.wageType2Day9Weekend = wageType2Day9Weekend;
    }

    /**
     * @return the wageType2Late9Week
     */
    public double getWageType2Late9Week() {
        return wageType2Late9Week;
    }

    /**
     * @param wageType2Late9Week the wageType2Late9Week to set
     */
    public void setWageType2Late9Week(double wageType2Late9Week) {
        this.wageType2Late9Week = wageType2Late9Week;
    }

    /**
     * @return the wageType2Late9Weekend
     */
    public double getWageType2Late9Weekend() {
        return wageType2Late9Weekend;
    }

    /**
     * @param wageType2Late9Weekend the wageType2Late9Weekend to set
     */
    public void setWageType2Late9Weekend(double wageType2Late9Weekend) {
        this.wageType2Late9Weekend = wageType2Late9Weekend;
    }

    /**
     * @return the wageType2Night9Week
     */
    public double getWageType2Night9Week() {
        return wageType2Night9Week;
    }

    /**
     * @param wageType2Night9Week the wageType2Night9Week to set
     */
    public void setWageType2Night9Week(double wageType2Night9Week) {
        this.wageType2Night9Week = wageType2Night9Week;
    }

    /**
     * @return the wageType2NightWeekend
     */
    public double getWageType2Night9Weekend() {
        return wageType2Night9Weekend;
    }

    /**
     * @param wageType2NightWeekend the wageType2NightWeekend to set
     */
    public void setWageType2Night9Weekend(double wageType2Night9Weekend) {
        this.wageType2Night9Weekend = wageType2Night9Weekend;
    }

    /**
     * @return the wageType2Early12Week
     */
    public double getWageType2Early12Week() {
        return wageType2Early12Week;
    }

    /**
     * @param wageType2Early12Week the wageType2Early12Week to set
     */
    public void setWageType2Early12Week(double wageType2Early12Week) {
        this.wageType2Early12Week = wageType2Early12Week;
    }

    /**
     * @return the wageType2Early12Weekend
     */
    public double getWageType2Early12Weekend() {
        return wageType2Early12Weekend;
    }

    /**
     * @param wageType2Early12Weekend the wageType2Early12Weekend to set
     */
    public void setWageType2Early12Weekend(double wageType2Early12Weekend) {
        this.wageType2Early12Weekend = wageType2Early12Weekend;
    }

    /**
     * @return the wageType2Day12Week
     */
    public double getWageType2Day12Week() {
        return wageType2Day12Week;
    }

    /**
     * @param wageType2Day12Week the wageType2Day12Week to set
     */
    public void setWageType2Day12Week(double wageType2Day12Week) {
        this.wageType2Day12Week = wageType2Day12Week;
    }

    /**
     * @return the wageType2Day12Weekend
     */
    public double getWageType2Day12Weekend() {
        return wageType2Day12Weekend;
    }

    /**
     * @param wageType2Day12Weekend the wageType2Day12Weekend to set
     */
    public void setWageType2Day12Weekend(double wageType2Day12Weekend) {
        this.wageType2Day12Weekend = wageType2Day12Weekend;
    }

    /**
     * @return the wageType2Late12Week
     */
    public double getWageType2Late12Week() {
        return wageType2Late12Week;
    }

    /**
     * @param wageType2Late12Week the wageType2Late12Week to set
     */
    public void setWageType2Late12Week(double wageType2Late12Week) {
        this.wageType2Late12Week = wageType2Late12Week;
    }

    /**
     * @return the wageType2Late12Weekend
     */
    public double getWageType2Late12Weekend() {
        return wageType2Late12Weekend;
    }

    /**
     * @param wageType2Late12Weekend the wageType2Late12Weekend to set
     */
    public void setWageType2Late12Weekend(double wageType2Late12Weekend) {
        this.wageType2Late12Weekend = wageType2Late12Weekend;
    }

    /**
     * @return the wageType2Night12Week
     */
    public double getWageType2Night12Week() {
        return wageType2Night12Week;
    }

    /**
     * @param wageType2Night12Week the wageType2Night12Week to set
     */
    public void setWageType2Night12Week(double wageType2Night12Week) {
        this.wageType2Night12Week = wageType2Night12Week;
    }

    /**
     * @return the wageType2Night12Weekend
     */
    public double getWageType2Night12Weekend() {
        return wageType2Night12Weekend;
    }

    /**
     * @param wageType2Night12Weekend the wageType2Night12Weekend to set
     */
    public void setWageType2Night12Weekend(double wageType2Night12Weekend) {
        this.wageType2Night12Weekend = wageType2Night12Weekend;
    }

    /**
     * @return the preference
     */
    public int getPreference() {
        return preference;
    }

    /**
     * @param preference the preference to set
     */
    public void setPreference(int preference) {
        this.preference = preference;
    }
     
   public int getReqShiftType()
   {
       return reqShiftType;
   }
   public void setReqShiftType(int reqShiftType)
   {
       this.reqShiftType = reqShiftType;
   }
    
    
}


    