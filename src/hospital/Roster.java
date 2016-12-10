
package hospital;

import com.opencsv.CSVWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import java.util.Random;
import javax.swing.JOptionPane;

import hospital.reader;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Roster {
    
        
        private final int NURSES = 100;
        private final int SHIFTS = 5;           // Weten we nog niet
        private final int TYPES = 2;
	private final int DAYS = 28; 
	private final int ROSTERS = 100;        // Individueel rooster voor elke nurse
	private final int MAXCONSDAYSOFWORK=5; 
        
        
        private char department; // a,b,c,d
        
        private int kappa=0;   //Wat is dit? Wordt gebruikt in evaluateSolution
	
	int k, l, m, p, q, hh, kk, h1, h2;
	
	private int numberOfNurses;
	private int numberOfNursesType1;
	private int numberOfNursesType2;
	private int numberOfShifts;
	private int numberOfRostersType1;
	private int numberOfRostersType2;
        private int lengthOfShift;
	
	//private int violations; 
	
	//readShiftSystem variables
	private int[] startShift = new int[SHIFTS];
	private int[] endShift = new int[SHIFTS];
	private int[] shift = new int[SHIFTS];
	private int[] hrs = new int[SHIFTS]; // hours of a shift
	private int[][] req = new int[DAYS][SHIFTS]; //to avoid double shifts on same day
	
	//readPersonnelCharacteristics variables
	private double[] nurseEmploymentRate = new double[NURSES]; 
	private int [] nurseType = new int[NURSES];
        private String [] nurseID = new String [NURSES];

	//readCyclicRoster variables
	private int[][] cyclicRostersType1 ;
	private int[][] cyclicRostersType2;
	private int [] reqFTERosterType1;
	private int [] reqFTERosterType2;
	
	private int [] minAss = new int[NURSES];								//min number of ass per nurse over complete period (month)
	private int [] maxAss = new int[NURSES];								//max number of ass per nurse over complete period (month)
	private int [][] minConsecPerShiftType = new int[NURSES][SHIFTS];                                       //min number of consecutive work days per shift type
	private int [][] maxConsecPerShiftType = new int[NURSES][SHIFTS];                                       //max number of consecutive work days per shift type
	private int [][] minNumberOfAssPerShiftMonth = new int[NURSES][SHIFTS];                                 //min number of assignments per shift type over the complete scheduling period
	private int [][] maxNumberOfAssPerShiftMonth = new int[NURSES][SHIFTS];                                 //max number of assignments per shift type over the complete scheduling period
	private int [] minConsecWork = new int[NURSES];                                                         //min number of consecutive workdays per nurse
	private int [] maxConsecWork = new int[NURSES];                                                         //max number of consecutive workdays per nurse
	private int [][] extremeMaxConsec = new int[NURSES][SHIFTS];			
	private int [][] extremeMinConsec = new int[NURSES][SHIFTS];
        private int [] identicalWeekend = new int [NURSES];
        
//inteate 
	
	ArrayList<Integer> type1NurseAssignedToType2RosterFinal;
	ArrayList<Integer> assignedNursesFinal;
	HashSet<Integer> sharedRosterFinal;
	private int [][] nurseScheduleFinal=new int [NURSES][DAYS]; //NumberOfNurses veranderd in Nurses
	int [] nurseDoesFinal;
	//private String PTHTDetails;
	private int [] nursesR2Final;
	
	//procedureBA
	ArrayList<Integer> type1NurseAssignedToType2Roster;
	ArrayList<Integer> assignedNurses;
	HashSet<Integer> sharedRoster;
	private int [][] nurseSchedule=new int [NURSES][DAYS]; //NumberOfNurses veranderd in Nurses
	int [] nursePT1; 
	int [] nursePT2;
	int [] nurseDoes;
	private String PTHTDetails;
	private int [] nursesR2;
	
	//procedureBAMH
	HashSet<Integer> sharedRosterBin= new HashSet<Integer>();
	ArrayList<Integer> assignedNursesBin= new ArrayList<Integer>();
	ArrayList<Integer> type1NurseAssignedToType2RosterBin= new ArrayList<Integer>();
	private int [] nurseDoesBin;
	private int [][] nurseScheduleBin=new int [NURSES][DAYS]; //numberOfNurses veranderd in NURSES
	private int [] nursesR2Bin;
	private String PTHTDetailsBin;
	
	private String textBin;//controle
	private String textFin;//controle
	
	
	//procedurePTMH
	private int[][] BinPTRoster= new int [100][DAYS];//Days =stores randomly generated score for pt nurses 
	private int[][] randBin;//random number per nurse per day
	int[] PTHTnurses; // PT/HT nurses of a specific schedule
	ArrayList<Integer> voldoendeDagenAf= new ArrayList<Integer>();
	ArrayList<Integer> voldoendeDagenGewerkt= new ArrayList<Integer>();
	int [] prefArrayRandom;//stores the preferences of nurses of a specific roster(via random) for a specifi day
	
	//procedurePT
	private int [][][] resid = new int [TYPES][DAYS][SHIFTS];
	//printOutput
	private String textMonthlyRoster;//will show the monthly roster in txt.format
	private String texttest="";
	
	//evaluateColumnSimplified variables
	private int countAss; 						//number of assignments in month
	private int countConsecWork; 				//number of consecutive workingdays
	private int countConsec; 					//?
	private int [] countShift = new int[SHIFTS]; //number of shifts of particular type
	private int [][][] scheduled = new int [TYPES][DAYS][SHIFTS];
        private int [] violations = new int[DAYS * SHIFTS];
	private String textConstraints; //will summarize the constraints
	
	//evaluateSolution variables
	private int a;//number of nurses scheduled on a particular day and shift
	//reArrange variables
	private boolean [][] alreadySwitched=new boolean[NURSES][DAYS]; 
	//cost
	private double costType1;
	private double costType2;
	private double costTotal;
	
		
	
        
        // Concstructor maken, departement meegeven
        public Roster(){
            
        }
        
        public Roster(char department){
            this.department = department;
        }
        
        public int shiftDecoding (int shiftCode)
	{
		int userShiftID=10;
		for (int s =0; s<numberOfShifts; s++)
		{
			if (shift[s]==shiftCode)
			userShiftID=s;
		}
		// userShiftID error?? (bij hun was da ne gui, heb dat weggedaan
                //System.out.println("userShiftID: " + userShiftID + " and passed through JAVAs: " + shiftCode);
		return userShiftID;
	}
	
        public void readRequirements (char department,int i ,int shift) //om de requirements in te lezen
        {
            reader r = new reader();
            r.readCaseC();
            
            this.department = department;
            if(department == 'A'){
               
                   int [] reqA = r.getReqA();
                   req [0][shift] = reqA[i];
               
            }
            if(department == 'B'){
               
                   int [] reqB = r.getReqB();
                   req [0][shift] = reqB[i];
               
            }
            if(department == 'C'){
               
                   int [] reqC = r.getReqC();
                   req [0][shift] = reqC[i];
               
            }
            if(department == 'D'){
               
                   int [] reqD = r.getReqD();
                   req [0][shift] = reqD[i];
               
            }
        }
	public void readShiftSystem()
	{
		//numberOfShifts= db.getNumberOfShifts(department)-1;// -1 opdat de free shift niet meegerekend zou worden
            reader r = new reader();
            r.readCaseC();
            
            this.numberOfShifts = r.getShifts();
            this.lengthOfShift = r.getLength();
            
            if(department == 'A'){
                startShift = r.getStartShiftA();
            }
            else if(department == 'B'){
                startShift = r.getStartShiftB();
            }
            else if(department == 'C'){
                startShift = r.getStartShiftC();
            }
            else if(department == 'B'){
                startShift = r.getStartShiftD();
            }
            
            
            
            
            //System.out.println("req: "+req[0][0]);
            
            //System.out.println(startShift[2]);
		// Early shift		Code 0
		// Day shift		Code 1
		// Late shift		Code 2
		// Night shift		Code 3
		// Day off			Code 4
		////////////USER
		// Day off 			Code 0
		// StartHour to EndHour Code 1 etc
		
	
	int d=0; // dag 1
        //System.out.println(numberOfShifts);
        //System.out.println(lengthOfShift);
	for (int s = 1; s<=numberOfShifts ;s++)
	{
            
            // de requierements zijn ingevuld dus als de 2e cel van req =! 0 gaat hij overspringen en krijg je het foute getal.
            // maar wat betekenen die requieremnts? Wat doen die daar??
		if ((startShift[s] >= 3) && (startShift[s] < 9) && (req[d][0] == 0))			// If the shifts start at 3 am or 6 am we define an early shift (and there is no other shift defined as an early shift)
		{	
			shift[s] = 0;
                        readRequirements(department,s,shift[s]);
			//System.out.println("usershift: " + s + " that starts at: " + startShift[s]+ " is now java shift: " + shift[s]);
		}	
		else if ((startShift[s] >= 3) && (startShift[s] < 9) && (req[d][0] != 0))
		{	
			shift[s] = 1;
                        readRequirements(department,s,shift[s]);
			//System.out.println("usershift: " + s + " that starts at: " + startShift[s]+ " is now java shift: " + shift[s]);
		}				
		if ((startShift[s] >= 9) && (startShift[s] < 12) && (req[d][1] == 0))			// If the shifts start at 9 am we define a day shift (and there is no other shift defined as a day shift)
		{	
			shift[s] = 1;
                        readRequirements(department,s,shift[s]);
			//System.out.println("usershift: " + s + " that starts at: " + startShift[s]+ " is now java shift: " + shift[s]);
		}			
		else if ((startShift[s] >= 9) && (startShift[s] < 12) && (req[d][1] != 0))
		{	
			shift[s] = 2;
                        readRequirements(department,s,shift[s]);
			//System.out.println("usershift: " + s + " that starts at: " + startShift[s]+ " is now java shift: " + shift[s]);
		}			
		if ((startShift[s] >= 12) && (startShift[s] < 21) && (req[d][2] == 0))		// If the shifts start at 12 am, 3 pm or 6 pm we define a late shift (and there is no other shift defined as a late shift)
		{	
			shift[s] = 2;
                        readRequirements(department,s,shift[s]);
			//System.out.println("usershift: " + s + " that starts at: " + startShift[s]+ " is now java shift: " + shift[s]);
		}
		else if ((startShift[s] >= 12) && (startShift[s] < 21) && (req[d][2] != 0))
		{			
			shift[s] = 3;
                        readRequirements(department,s,shift[s]);
			//System.out.println("usershift: " + s + " that starts at: " + startShift[s]+ " is now java shift: " + shift[s]);
		}
		if ((startShift[s] >= 21) || (startShift[s] < 3) && (req[d][3] == 0))			// If the shifts start at 9 pm or 12 pm we define a night shift (and there is no other shift defined as a night shift)
		{	
			shift[s] = 3;
                        readRequirements(department,s,shift[s]);
			//System.out.println("usershift: " + s + " that starts at: " + startShift[s]+ " is now java shift: " + shift[s]);
		}
		else if ((startShift[s] >= 21) || (startShift[s] < 3) && (req[d][3] != 0))
			System.out.println("Read problem shifts input");
	}
	shift[0] = 4;
	//System.out.println("free day: usershift: is now java shift: " + shift[0]);
	// According to the input data, the day off (code 0) is associated with shift (numberOfShifts-1) (the free shift). 

	hrs[shift[0]] = 0; // The free shift contains no duty time
        for (int s = 1; s <= numberOfShifts; s++)	// Determine the length of the shifts
	{	if (startShift[s] + lengthOfShift < 24)
			{
			hrs[shift[s]] = lengthOfShift;
                        endShift[s] = startShift[s] + lengthOfShift;
			//System.out.println("length shift " + s + " : "+ hrs[shift[s]]);
			}
		else
			{
			hrs[shift[s]] = lengthOfShift;
                        endShift[s] = hrs[shift[s]] + startShift[s]-24;
			//System.out.println("length shift " + s + ": " + hrs[shift[s]]);
			}
	}

	

	for (d = 0; d <DAYS; d++)	// Copy staffing requirements to the other days								// Copy staffing requirements to the other days
	{	for (int s = 1; s <= numberOfShifts; s++)							
		{
			req[d][shift[s]] = req[0][shift[s]];
			//System.out.println("s = "+s+"shift[s]= "+ shift[s]);
			//System.out.println("staffing req for day " + (d+1) + " and usershift " + s + ": " + req[d][shift[s]] + " number of nurses");
		}
	}
	
	numberOfShifts++; // Increase the number of shifts by one as a day off is also considered as a shift, i.e. the free shift
	System.out.println("_________________________end readshiftSystem_____________________");
	
	}
        
        public void readPersonnelCharacteristics()
	{
            reader r = new reader();
            r.readNurses(department);
            
            numberOfNurses = r.getNurses();
            numberOfNursesType1 = r.getNurses1();
            numberOfNursesType2 = r.getNurses2();
		//System.out.println("Number of Nurses: " + numberOfNurses);
                //System.out.println("Number of Nurses type 1: " + numberOfNursesType1);
                //System.out.println("Number of Nurses type 2: " + numberOfNursesType2);

		for (int n=0;n<numberOfNurses;n++)
		{			
                   double [] rate = r.getEmploymentRate();
                   int [] type = r.getType();
                   String [] ID = r.getID();
                   nurseEmploymentRate[n] = (rate [n]/100);
                   nurseType[n]=type[n];
                   nurseID[n]=ID[n];
                   
		//System.out.println("nurseID: " + nurseID[n] +" nurseEmploymentRate: " + (rate[n]) + "% APPLIED that becomes: " + nurseEmploymentRate[n] + " and of type " + nurseType[n]  ); 
		}
		
            System.out.println("_________________________end readPersonnelCharacteristics_____________________");
	
        }
public void readMonthlyRosterRules()
	{
            reader r = new reader();
            r.readRosterRules(department);
            
		for (int n=0; n < numberOfNurses; n++)
		{
			// Read the min/max number of 
			//assignments over the complete scheduling period
			minAss[n] = r.getMinAss();
			maxAss[n] = r.getMaxAss();
			//individual Employment rates depending of FT or PT nurse
			minAss[n]=(int)Math.round(nurseEmploymentRate[n]*minAss[n]);
			maxAss[n]=(int)Math.round(nurseEmploymentRate[n]*maxAss[n]);
			//Read the min/max number of consecutive work days
			minConsecWork[n] = r.getMinConsAss();
			maxConsecWork[n] = r.getMaxConsAss();
                        identicalWeekend[n]=r.getWeekendNr();
                        //System.out.println("MONTHlyRosterRules nurse: " + nurseID[n] 
			//		+ " min and max ass: " + minAss[n] + "/" + maxAss[n] 
			//		+ " min and max consecWork: "  + minConsecWork[n] + "/" + maxConsecWork[n]
                        //                + " identical weekend? "+identicalWeekend[n]);
			
                        //System.out.println("numberOfShifts: "+numberOfShifts);
			// Read in first the constraints with respect to the working shifts
			for(int s=1;s<(numberOfShifts);s++) //om de free shift te includen, s van nul laten beginnen en de 0 als shiftID toevoegen in dB
			{
                            int [] minCshift = r.getMinConsecPerShiftType();
                            int [] maxCshift = r.getMaxConsecPerShiftType();
                            int [] minAshift = r.getMinNumberOfAssPerShiftMonth();
                            int [] maxAshift = r.getMaxNumberOfAssPerShiftMonth();
                            minConsecPerShiftType[n][shift[s]]=minCshift[s];
                            maxConsecPerShiftType[n][shift[s]]=maxCshift[s];
                            extremeMaxConsec[n][shift[s]] = 10;// hoe bepalen we dat 
                            extremeMinConsec[n][shift[s]] = 10; // hoe bepalen we dat --> mario = 1
                            minNumberOfAssPerShiftMonth[n][shift[s]] = minAshift[s];
                            maxNumberOfAssPerShiftMonth[n][shift[s]] = maxAshift[s];
                            //System.out.println("MonthlyRosterRules shift: " + (s) + " min and max consec days per shift type: "
				//		+ minConsecPerShiftType[n][shift[s]] + "/" +maxConsecPerShiftType[n][shift[s]] 
				//		+" min and max number of ass per shift in a month: " + minNumberOfAssPerShiftMonth[n][shift[s]] 
				//		+"/" +maxNumberOfAssPerShiftMonth[n][shift[s]] );
			}
			
		
		}
		System.out.println("_________________________end readMonthlyRoster_____________________");
		
        }


public void readCyclicRoster()
	{
            reader r = new reader();
            r.readRosters(department);
            
            numberOfRostersType1 = r.getNumberOfRostersType1();
            numberOfRostersType2 = r.getNumberOfRostersType2();
            
            cyclicRostersType1 = new int [numberOfRostersType1][DAYS];
            cyclicRostersType2 = new int [numberOfRostersType1+numberOfRostersType2][DAYS];
            reqFTERosterType1 = new int[numberOfRostersType1];
            reqFTERosterType2 = new int [numberOfRostersType1+numberOfRostersType2];

                    
		for (int s=0;s<numberOfRostersType1;s++)
		{
			
			for (int d=0;d<DAYS;d++)
			{
                                int [][] cyclR1 = r.getCyclicRostersType1();
				cyclicRostersType1[s][d]=shift[cyclR1[s][d]]; 
				//System.out.println("Type 1, Cyclic roster : " + (s+1)+  " for department " +department+ " on day " + (d+1) 
				//		+ " is " + cyclicRostersType1[s][d] + " and there are " + "reqFTERosterType1[r]" + " FTE required.") ;
			}
		}
		
		for (int s=(numberOfRostersType1);s<(numberOfRostersType1+numberOfRostersType2);s++) 
		{
			
			for (int d=0;d<DAYS;d++)
			{
                                int [][] cyclR2 = r.getCyclicRostersType2();
				cyclicRostersType2[s][d]=shift[cyclR2[s][d]]; 
				//System.out.println("Type 2, Cyclic roster : " + (s+1)+  " for department " +department+ " on day " + (d+1) 
				//		+ " is " + cyclicRostersType2[s][d]+ " and there are " + "reqFTERosterType2[r]" + " FTE required.") ;
			}
		}
		System.out.println("_________________________end readCyclicRoster_____________________");
	
	}



public void readInput(){
    readShiftSystem();
    readPersonnelCharacteristics();
    readMonthlyRosterRules();
    readCyclicRoster();
}

public void iterate(){
    
    reader r = new reader();
    
		int count1=0;
		int count2=0;
		int violationss=0; //Ruth heeft hier een int van gemaatk want bestond niet??
		nurseDoesFinal= new int[numberOfNurses];
		int minimum=9999999;
		
		while (count1!=8){ //stond op 28
			System.out.println("\n\n\n\n\t(\\(\\ \n\t( -.-) \n\to__('')('')\n\tFirst loop trial " + (count1+1));
			procedureBA();
			count2=0;
			System.out.println(PTHTDetails);
			
			while (count2!=5){//stond op 7 
				//evaluateSolution();
				horizontalSwapping();
				System.out.println("AFTER HORIZONTAM");
				//evaluateSolution();
				verticalSwapping();
				System.out.println("AFTER VERTICLA");
				//evaluateSolution();
				rearrangeSurplusRandom();
				System.out.println("AFTER REARRANGE");
				//evaluateSolution();
				avoidIslands();
				System.out.println("AFTER ISLANDS");
				count2++;
			}
			violationss=0;
			for (int n=0; n<numberOfNurses;n++){
 				for (int d=0;d<DAYS;d++){
 					violationss+= r.readPreference(n, d, shiftDecoding(nurseSchedule[n][d]),department);
 				}
 			}
 			System.out.println("Total Violations: " + violationss);
 			
 			evaluateSolution();
	 		if (violationss<minimum && violations[1]==0 && kappa!=0){
	 			minimum=violationss;
	 			sharedRosterFinal=new HashSet<Integer>(sharedRoster);
				assignedNursesFinal=new ArrayList<Integer>(assignedNurses);
				type1NurseAssignedToType2RosterFinal=new ArrayList<Integer>(type1NurseAssignedToType2Roster);
				nursesR2Final=new int [nursesR2.length];
				nursesR2Final=nursesR2;
				for (int n=0;n<numberOfNurses;n++){
					if (assignedNursesFinal.contains(n)){
							nurseDoesFinal[n]=nurseDoes[n];
							for (int d=0;d<DAYS;d++){
								nurseScheduleFinal[n][d]=nurseSchedule[n][d];
								}
							}							
					}
					System.out.println("!!!!!!!!!!!!!!!!!!!!MIN SO FAR "  + minimum + "!!!!!!!!!!!!!!!!!!!!!!\n");
	 			}
		count1++;
		}
                        System.out.println("xxxx xxxx xxxx finalSolution xxxx xxxx xxxx");
                //NOG METHODES!!!        
		//printOutputFinal();
		//evaluateSolutionFinal();
                //excelResult(); 
                        System.out.println("EXCEL ---> GA GAAN ZIEN IN UW WORKSPACE VOOR DE OUTPUT!!!");
	}

public void procedureBA()
	{
		nurseDoes= new int[numberOfNurses];
		
		int count=0;
		int mini=99999999;
		while(count!=19){
			System.out.println("Second loop trial " + (count+1));
			int tempProcedureBAMH=procedureBAMH();
			if(tempProcedureBAMH < mini){
				mini=tempProcedureBAMH;
				sharedRoster=new HashSet<Integer>(sharedRosterBin);
				assignedNurses=new ArrayList<Integer>(assignedNursesBin);
				type1NurseAssignedToType2Roster=new ArrayList<Integer>(type1NurseAssignedToType2RosterBin);
				nursesR2=new int [nursesR2Bin.length];
				nursesR2=nursesR2Bin;
				PTHTDetails="BA==TRIAL " + count + "\n" + PTHTDetailsBin;
				for (int n=0;n<numberOfNurses;n++){
					if (assignedNurses.contains(n)){
							nurseDoes[n]=nurseDoesBin[n];
							for (int d=0;d<DAYS;d++){
								nurseSchedule[n][d]=nurseScheduleBin[n][d];
								}
							}
						}
				System.out.println("!!!!!!!!!!!!!!!!!!!!MIN SO FAR "  + mini + "!!!!!!!!!!!!!!!!!!!!!!\n");
				}
			count++;
			}
		//volgende 6 lijntjes zijn pure controle system.outs
		System.out.println(PTHTDetails);
		String gg="nursesR2: ";
		for( int i =0; i<nursesR2.length;i++){
		gg+=	nursesR2[i] + ", ";
		}
		System.out.println("type1NurseAssignedToType2Roster (includes 0!!): "+ type1NurseAssignedToType2Roster + "\n" +  gg);
	}
	public int procedureBAMH(){
		nurseDoesBin= new int[numberOfNurses];
		int n1;
		int n2;
		int penalty=999999;
		int count=0;
		PTHTDetailsBin="";

		boolean vlag=false;
		assignedNursesBin.clear();
		type1NurseAssignedToType2RosterBin.clear();
		sharedRosterBin.clear();
		
		for (int m=0; m<1;m++){//this loops's only purpose is to allow for a break when certain conditions aren't met
		for (int r = 0; r<numberOfRostersType1; r++){
			System.out.println("ROSTER " + (r+1));
			int low=0;//incl.
			int high=numberOfNursesType1;//Excl.
			n1=(new Random().nextInt(high-low)+low);
                        System.out.println("n1: "+n1);
			while(assignedNursesBin.contains(n1)){
				n1=new Random().nextInt(high-low)+low;
				System.out.println("New n1 " + n1);
			}
			PTHTDetailsBin+=("\nn1: " +  n1 + " has ER: " + nurseEmploymentRate[n1] *100 +"%" );
			assignedNursesBin.add(n1);
			nurseDoesBin[n1]=r;
			for(int d=0; d<DAYS;d++){
                            //System.out.println("cyclicRosterType1= " +cyclicRostersType1[r][d]);
				nurseScheduleBin[n1][d]=cyclicRostersType1[r][d];
			}
			if(nurseEmploymentRate[n1]!=1.0){
				sharedRosterBin.add(r);
				n2=(new Random().nextInt(high-low)+low);
				count=0;
				while (!vlag && count!=999999){
					if(!assignedNursesBin.contains(n2) && nurseEmploymentRate[n2]!=1.0)
						{vlag=true;}
					else
						{n2=new Random().nextInt(high-low)+low;}
					count++;
				}
				count=0;
				while(assignedNursesBin.contains(n2) && !vlag && count!=9999){
					n2=new Random().nextInt(high-low)+low;
					count++;
				}
				if (count!=9999){
					PTHTDetailsBin+=("\nn2: " +  n2 + " has ER: " + nurseEmploymentRate[n2] *100+"%" );
				assignedNursesBin.add(n2);
                                System.out.println("assignedNurse 2: "+n2);
				nurseDoesBin[n2]=r;
				for(int d=0; d<DAYS;d++){
					nurseScheduleBin[n2][d]=cyclicRostersType1[r][d];
				}
				vlag=false;
				}
				else
					System.out.println("No nurses left for roster " + (r+1));
			}
			PTHTDetailsBin+=("assigned overview: " + assignedNursesBin.toString());
		}//end loop type 1 roster
		for (int n=0;n<numberOfNursesType1;n++){//nurses of type 1 whom there is no schedule for left
			if (!assignedNursesBin.contains(n)){
				type1NurseAssignedToType2RosterBin.add(n);
				PTHTDetailsBin+=("\nNurse " + n + " of type " + nurseType[n] + " will be assigned to a roster for type 2 nurses");
			}
		}//end loop nurses (type1R vs type2R)
		int [] T1NAssignedToT2R = new int [type1NurseAssignedToType2RosterBin.size()];
		int j=0;
		for (int n=0;n<numberOfNursesType1;n++){//nurses of type 1 whom there is no schedule for left
				if (!assignedNursesBin.contains(n)){
					T1NAssignedToT2R[j]=n;
					j++;
				}
			}
		nursesR2Bin= new int[numberOfNursesType2 + type1NurseAssignedToType2RosterBin.size()];
		for(int i=0; i<type1NurseAssignedToType2RosterBin.size();i++){
			nursesR2Bin[i]=T1NAssignedToT2R[i];
		}
		int t=0;
		for (int i=type1NurseAssignedToType2RosterBin.size(); i<numberOfNursesType2 + type1NurseAssignedToType2RosterBin.size();i++){
			nursesR2Bin[i]=numberOfNursesType1+t;
			t++;
		}
		System.out.println("///////////////////////TYPE 2 ("+nursesR2Bin.length+")/////////////////////////////////////////");
		for (int r=numberOfRostersType1;r<numberOfRostersType1+numberOfRostersType2;r++){
                    System.out.println("reqFTERosterType2: "+reqFTERosterType2[r]);
			for(int f =0; f<reqFTERosterType2[r]; f++){
				System.out.println("ROSTER " + (r+1));
				int low=0;//incl.
				int high=nursesR2Bin.length;//Excl.
				n1=(new Random().nextInt(high-low)+low);
				while(assignedNursesBin.contains(nursesR2Bin[n1])&& count!=99999){
					n1=new Random().nextInt(high-low)+low;
					count++;
				}
				if (count!=99999){count=0;
				System.out.println("n1: " +  nursesR2Bin[n1] + " has ER: " + nurseEmploymentRate[nursesR2Bin[n1]] *100+"%" );
				assignedNursesBin.add(nursesR2Bin[n1]);
				nurseDoesBin[nursesR2Bin[n1]]=r;
				System.out.println("Nurse: " + (nursesR2Bin[n1]+1) +" does roster " + (r+1));
				for(int d=0; d<DAYS;d++){
					nurseScheduleBin[nursesR2Bin[n1]][d]=cyclicRostersType2[r][d];
				}
				if(nurseEmploymentRate[nursesR2Bin[n1]]!=1.0){
					sharedRosterBin.add(r);
					n2=(new Random().nextInt(high-low)+low);
					count=0;
					while (!vlag && count!=999999){
						if(!assignedNursesBin.contains(nursesR2Bin[n2]) && nurseEmploymentRate[nursesR2Bin[n2]]!=1.0)
							{vlag=true;}
						else
							{n2=new Random().nextInt(high-low)+low;}
						count++;	
					}
					count=0;
					while(assignedNursesBin.contains(nursesR2Bin[n2]) && !vlag && count!=9999){
						n2=new Random().nextInt(high-low)+low;
						count++;
					}
					if (count!=9999){
					System.out.println("n2: " +  n2 + " has ER: " + nurseEmploymentRate[n2] *100 );
					assignedNursesBin.add(nursesR2Bin[n2]);
					nurseDoesBin[nursesR2Bin[n2]]=r;
					for(int d=0; d<DAYS;d++){
						nurseScheduleBin[nursesR2Bin[n2]][d]=cyclicRostersType2[r][d];
					}
					vlag=false;
					}
					else
						System.out.println("No nurses left for roster " + (r+1));
				}
			}
				else
				{
					System.out.println("No nurses left for roster " + (r+1));
				}
			}
		}//end loop type 2 roster
		System.out.println("assigned overview (" + assignedNursesBin.size() +"/"+ numberOfNurses+"): " + assignedNursesBin.toString());
		System.out.println("SharedRosterBin (" + sharedRosterBin.size() +"):" + sharedRosterBin.toString());
		if(assignedNursesBin.size()>numberOfNurses) break;
		}
		//penalty=procedurePT();
		PTHTDetailsBin+=("\n>>>>>>>>>>>>>>>TOTAL PENALTY: " + penalty);
		return penalty;
	}
	public int procedurePT()
	{
            reader re = new reader();
            
		int r;
		int totaalAantal=0;
		int aantalPT=0;
		int aantalHT=0;
		int aantalFT=0;
		int mini=0;
		//PTHTDetailsBin="";
		ArrayList<Integer> PTnurses= new ArrayList<Integer>();
		
		for (int i=0;i<numberOfRostersType1+numberOfRostersType2;i++){ 
			if (sharedRosterBin.contains(i)){
				r=i;
				totaalAantal=0; aantalPT=0; aantalHT=0; aantalFT=0;
				PTnurses.clear();
				
				for (int n=0;n<numberOfNurses;n++){//algemene analyse van nurse soorten en aantal
					if (nurseDoesBin[n]==r && nurseEmploymentRate[n]==0.75){
						totaalAantal++;
						aantalPT++;
					}
					else if (nurseDoesBin[n]==r && nurseEmploymentRate[n]==0.50 ){
						totaalAantal++;
						aantalHT++;
					}
					else if (nurseDoesBin[n]==r)
						aantalFT++;
				}
				PTHTDetailsBin+=("\n\n----------------totaal aantal(PT+ HT): " +  totaalAantal + "----------------Roster" + (r+1) + "-----------------FT/PT/HT : " + aantalFT + "/" + aantalPT + "/" + aantalHT+"----------------------");
				PTHTnurses = new int [totaalAantal];
				int mh=0;
				int count=0;
				for (int n=0;n<numberOfNurses;n++){//opvullen array dat bepaalde nurse voor een roster bijhoudt
					if (nurseDoesBin[n]==r && (nurseEmploymentRate[n]==0.75 || nurseEmploymentRate[n]==0.50)){
						PTHTnurses[mh]=n;
						mh++;
						System.out.println("dubbel roster " + (r+1) + " wordt gevolgd door nurse " + nurseID[n]);
					}
				}
				mini=99999999;
				while(count!=999){
					int tempProcedurePTMH=procedurePTMH(totaalAantal, aantalPT, aantalHT);
					if(tempProcedurePTMH < mini){
							mini=tempProcedurePTMH;
							for (int d=0;d<DAYS;d++){
								for (int n=0; n<numberOfNurses;n++){
									for (int t=0;t<totaalAantal;t++){
										if (n==PTHTnurses[t]){
											BinPTRoster[PTHTnurses[t]][d] = randBin[n][d]; 
											}
										}
									}
								}
							textFin=textBin;
						}
					count++;
					}//end while
				//PTHTDetailsBin+=textFin;
				}
			}
		int violations=0;
		for (int n=0; n<numberOfNurses;n++){System.out.println("nurse " + nurseID[n] + ", Roster " + (nurseDoesBin[n]+1)) ; //komt er zo iets in nurseID? Dit was met een database
			for (int d=0;d<DAYS;d++){
				System.out.println("Day " +(d+1)+ " Usershift " + shiftDecoding(nurseScheduleBin[n][d]) +  " penalty: " + re.readPreference(n, d, shiftDecoding(nurseScheduleBin[n][d]),department) );
				violations+= re.readPreference(n, d, shiftDecoding(nurseScheduleBin[n][d]),department);
			}
		}
		return violations;
	}
        
        public int procedurePTMH(int totaalAantal, int aantalPT, int aantalHT)//hier enkel nog de voorwaarde van opeenvolgende dagen nog bij
	{
            reader r = new reader();
            
	int[] sumDay= new int [DAYS];//row total over day for all nurses
	randBin=new int[numberOfNurses][DAYS];//random number per nurse per day
	int totPenalty=999999;
	prefArrayRandom=new int [totaalAantal];
	int[] cancelled=new int [numberOfNurses];
	int[] worked=new int [numberOfNurses];
	String text="";
	
	voldoendeDagenAf.clear();
	voldoendeDagenGewerkt.clear();
	
	int high=0;;
    int low=0;
	int reqMinAss=(int) Math.round((double)totaalAantal/2); //YYYYYYYY
	int totalAss=(int) Math.round((double)(aantalPT*15 + aantalHT*10)); //XXXXXX
	int surplus=totalAss-reqMinAss*20;//ZZZZZZ = X-Y*20
	int maxLoad=0;//number of times the maximum logically possible number of nurses is assigned
	int reqMaxLoad= (reqMinAss*20-aantalPT*(20-15)) + (reqMinAss*20-aantalHT*(20-10));
	int totalSumDay=0;
	int reqTotalSumDay=aantalPT * 15 + aantalHT*10;//required total over all days for all nurses
	
	if(surplus<0) {
		high=reqMinAss;
		low=reqMinAss-1;
		reqMaxLoad=20+surplus;
		}
	else if(surplus==0) {
		high=reqMinAss;
		low=reqMinAss;
		reqMaxLoad=20;
		}
	else if (surplus>0 && surplus<=20){
		high=reqMinAss+1;
		low=reqMinAss;
		reqMaxLoad=surplus;
		}
	else if (surplus>20 && surplus <=40){
		high=reqMinAss+2;
		low=reqMinAss+1;
		reqMaxLoad=surplus-20;
		}
	else if (surplus>40 && surplus<=60){
		high=reqMinAss+3;
		low=reqMinAss+2;
		reqMaxLoad=surplus-40;
		}
	else if (surplus>60 && surplus<=80){
		high=reqMinAss+4;
		low=reqMinAss+3;
		reqMaxLoad=surplus-60;
		}
	
	for (int i=0; i<1;i++){
		for (int d=0;d<DAYS;d++){
			if(nurseScheduleBin[PTHTnurses[0]][d]<numberOfShifts-1){//nurses on this roster work
				sumDay[d]=(new Random().nextInt(high-low+1)+low);
		        if(sumDay[d]==high) maxLoad++;
		   		totalSumDay+=sumDay[d];
			    }
			}
		
		
	if (totalSumDay!= reqTotalSumDay) {/*////System.out.println("BREAK cause total sum is " +  totalSumDay + " and not " + reqTotalSumDay);*/break;}
	if (maxLoad!=reqMaxLoad){/*////System.out.println("BREAK cause total number of days with nurses at max load is " +  maxLoad + " and not " + reqMaxLoad);*/break;}		
	text=("\nRoster: " + (nurseDoesBin[PTHTnurses[0]]+1)  + " First nurse: " + (PTHTnurses[0]+1) 
			+"\nSurplus: " + surplus + "=> " + reqMaxLoad  + " dagen met " + high + " nurses en " + (20-reqMaxLoad) + " van " + low);
	text+=("\n===>> ECHT " + (aantalPT * 15 + aantalHT*10) + " = " + (reqMaxLoad*high + (20-reqMaxLoad)*low)  + " DOEL");
		if (totaalAantal==1){totPenalty=0;
			for (int d=0;d<DAYS;d++){
				text+=("\t\tday " + (d+1) + "\t" + sumDay[d]);
				randBin[0][d]=sumDay[d];
				if (randBin[0][d]==0){
					nurseScheduleBin[PTHTnurses[0]][d]=numberOfShifts-1;
					}
				totPenalty+=r.readPreference(PTHTnurses[0], d, shiftDecoding(nurseScheduleBin[PTHTnurses[0]][d]),department);
				}
			
		}//end if == 1
		if (totaalAantal>1){totPenalty=0;
			System.out.println("totaalAantal " + totaalAantal + ", aantalPT " +  aantalPT + ",en aantalHT " + aantalHT);
			for (int d=0;d<DAYS;d++){
					if(nurseScheduleBin[PTHTnurses[0]][d]<numberOfShifts-1){
						text+=("\n\t\tday " + (d+1) + "\t" + sumDay[d]);
						int teller=0;
						for (int n=0; n<numberOfNurses;n++){
							for (int t=0;t<totaalAantal;t++){
								if (n==PTHTnurses[t]){
									prefArrayRandom[t]=r.readPreference(PTHTnurses[t], d, shiftDecoding(nurseScheduleBin[PTHTnurses[t]][d]),department);
									if (voldoendeDagenAf.contains(n)){
										prefArrayRandom[t]*=100;
										randBin[n][d]=1;
										teller++;
										text+=(" \nnurse: " + nurseID[PTHTnurses[t]] +" has to work, pref:" + prefArrayRandom[t]);
									}
									else if (voldoendeDagenGewerkt.contains(n)){
										prefArrayRandom[t]*=100;
										randBin[n][d]=0;
										nurseScheduleBin[n][d]=numberOfShifts-1;
										text+=(" \nnurse: " + nurseID[PTHTnurses[t]] +" may not longer work, pref:" + prefArrayRandom[t]);
										
									}
								}
							}
						}
					bubbleSortPref();
					for (int j=0; j<sumDay[d]-teller; j++){
						for (int n=0; n<numberOfNurses;n++){
							if (n==PTHTnurses[j]){
								randBin[n][d]=1;
								worked[n]++;
								text+=(" \nnurse: " + nurseID[PTHTnurses[j]]+" will work, pref: "+ + prefArrayRandom[j]);
							}
						}
					}
					for (int n=0; n<numberOfNurses;n++){
						for (int t=0;t<totaalAantal;t++){
							if (n==PTHTnurses[t]){
								if (randBin[n][d]==0){
									cancelled[n]++;
									text+=("\nCancel n "+ cancelled[n] +" of "+ (20-maxAss[PTHTnurses[t]]) + " for nurse: " + nurseID[PTHTnurses[t]] + ", pref:  "+ prefArrayRandom[t]);
									}
								if (!voldoendeDagenAf.contains(n)){	
									if(cancelled[n]==(20-maxAss[PTHTnurses[t]])){
										voldoendeDagenAf.add(n);
										cancelled[n]=0;
										text+=(" \nnurse: " + nurseID[PTHTnurses[t]] +" has been added to VoldoendeDagenAf");
										}
									if (worked[n]==maxAss[PTHTnurses[t]]){
										voldoendeDagenGewerkt.add(n);
										worked[n]=0;
										text+=(" \nnurse: " + nurseID[PTHTnurses[t]] +" has been added to VoldoendeDagenGewerkt");
										}
									}
								}
							}
						}
						
					}
				}
			for (int d=0;d<DAYS;d++){
				for (int n=0; n<numberOfNurses;n++){
					for (int t=0;t<totaalAantal;t++){
						if (n==PTHTnurses[t]){
							if(randBin[n][d]==0){
								nurseScheduleBin[PTHTnurses[t]][d]=numberOfShifts-1;
							}
							totPenalty+=r.readPreference(n, d, shiftDecoding(nurseScheduleBin[n][d]),department);

						}
					}
					
				}
			}
		}
	}if (totPenalty!=999999)
		System.out.println("\n\tprocedurePTMH == PT Penalty For Roster: " + (nurseDoesBin[PTHTnurses[0]]+1)  + " first nurse: " + (PTHTnurses[0]+1)
				+ "\n"+text); 
		textBin=text;
	 return totPenalty;
	}
	public void bubbleSortPref()
	{
		 int tempVar;
		 int tempNurse;
	        for (int i = 0; i < prefArrayRandom.length; i++)
	        {
	                 for(int j = 1; j < prefArrayRandom.length-i; j++)
	                 {
	                         if(prefArrayRandom[j-1] > prefArrayRandom[j])
	                         {
	                         tempVar = prefArrayRandom [j-1];
	                         tempNurse= PTHTnurses[j -1];
	                         prefArrayRandom [j - 1]= prefArrayRandom [j];
	                         PTHTnurses[j - 1]=PTHTnurses[j];
	                         prefArrayRandom [j] = tempVar;
	                         PTHTnurses[j]= tempNurse;
	                         }
	                 }
	        }
	}
	
	
	public boolean checkSwitchable(int nurse,int otherNurse, int day,
			int shift, int otherShift,
			int prefShiftNurse,int prefShiftOtherNurse,
			int prefShiftNurseForOtherShift, int prefShiftOtherNurseForShift)
	{
			if(day!=0 && otherShift!=numberOfShifts-1 && shift!=numberOfShifts-1){ //de shiften verschillend van de free shift!
				if(prefShiftOtherNurseForShift<=prefShiftOtherNurse//is de preferentie voor de nieuwe shift groter dan de huidige shift
					 && prefShiftOtherNurseForShift<10 //wilt de andere nurse die shift doen
					  &&prefShiftNurseForOtherShift<10) //wilt de nurse die andere shift doen
					return checkNightShiftRule(nurse,day,otherShift); 
				else return false; 
			}
			else return false;
	}
	public boolean enoughReqNurses(int nurse,int day,int type,int sh)
	{
            reader r = new reader();
		// doorgegeven type (nurseType[]) is 1 of 2!!
		if (type1NurseAssignedToType2Roster.contains(nurse)){
			if (scheduled[type][day][sh]>r.readRequirements(type,shift[sh],department)){
				System.out.println("1TO2 Day "+ (day+1) + " S" + sh + ",=> "+scheduled[type][day][sh]+"/"+r.readRequirements(type,sh,department));
				return true;
				}
			else return false;
			}
		else{
			if (scheduled[type-1][day][sh]>r.readRequirements(type-1,shift[sh],department)){
				System.out.println("Day "+ (day+1) + " S" + sh + ",  => "+scheduled[type-1][day][sh]+"/"+r.readRequirements(type-1,sh,department));
				return true;
				}
			else return false;
			}
	}	
	public boolean daysOfConsWork(int nurse, int day) //rond welke dag men nog moet checken
	{
		int daysWithoutBreak=1;  
		int i=1; 
		while((day-i)>=0 && nurseSchedule[nurse][day-i] != (numberOfShifts-1) && i<= MAXCONSDAYSOFWORK){  //while(de vorige dag verschillend is van een vrije shift
			daysWithoutBreak +=1; 
			i++; 
		}
		i=1; 
		while((day+i)<DAYS && nurseSchedule[nurse][day+i] != (numberOfShifts-1) && i<=MAXCONSDAYSOFWORK){ //while(de volgende dag verschillend is van een vrije shift)
			daysWithoutBreak +=1; 
			i++; 
		}
		if(daysWithoutBreak<= MAXCONSDAYSOFWORK && daysWithoutBreak>1){ //MAXCONSDAYSOFWORK=5
			return true; 
		}
		else 
			return false; 	
	}
	public boolean daysOfFree(int nurse, int day)
	{
		int freeDays =1;
		int i=1;
		while((day-i)>=0 && nurseSchedule[nurse][day-i] == (numberOfShifts-1) ){  //while(de vorige dag verschillend is van een vrije shift
			freeDays +=1; 
			i++; 
		}
		while((day+i)<DAYS && nurseSchedule[nurse][day+i] == (numberOfShifts-1) ){ //while(de volgende dag verschillend is van een vrije shift)
			freeDays +=1; 
			i++; 
		}
		if(freeDays<= 2 && freeDays>1){ //MAXCONSDAYSOFWORK=5
			return true; 
		}
		else 
			return false; 	
		
	}
 	public void verticalSwapping(){ //functie om op dezelfde dag een shift te switchen. 
            
            reader r = new reader();
            
		boolean switched=false;
		loadScheduled();
		int numberOtherNurse=0;
		System.out.println("\n__________________________________Start Vertical Swapping__________________________________");
		System.out.println("number of nurses of type 1: "+numberOfNursesType1);
		System.out.println("number of nurses of type 2: "+numberOfNursesType2);
		System.out.println("______________________type 1________________________");
		for(int n=0;n<numberOfNursesType1;n++){ // alle nurses nagaan van die preferences //wat zit er achter numberOfNurses???
			if(!type1NurseAssignedToType2Roster.contains(n)){	//enkel die type1 nurses die type1 roster doen
				for(int d=0;d< DAYS;d++){ //al hun toegewezen shifts nagaan (met TOTALDAYS= 28) 
						int shift = nurseSchedule[n][d];//javashift
						int prefShiftNurse=r.readPreference(n, d, shiftDecoding(shift),department);
						if(prefShiftNurse >= 10){ //als de nurse een shift heeft met een preferentie boven 10   
							//welke shiften mag de nurse nog doen?
							while(numberOtherNurse < numberOfNursesType1 && switched==false && !type1NurseAssignedToType2Roster.contains(numberOtherNurse) ){ //loop voor de andere nurses af te gaan, stopt ook wanneer een switch werd gedaan
								int otherShift= nurseSchedule[numberOtherNurse][d];
								int prefShiftOtherNurse=r.readPreference(numberOtherNurse,d, shiftDecoding(otherShift),department);
								int prefShiftNurseForOtherShift = r.readPreference(n, d, shiftDecoding(otherShift),department);
								int prefShiftOtherNurseForShift = r.readPreference(numberOtherNurse, d, shiftDecoding(shift),department);
								
								
								if(checkSwitchable(n,numberOtherNurse,d,shift, otherShift,prefShiftNurse, prefShiftOtherNurse,
										prefShiftNurseForOtherShift, prefShiftOtherNurseForShift)==true){
									//hoevaak hierin
									System.out.println("SWITCH from: nurseSchedule["+(n+1) +"]["+(d+1)+"]=" + shiftDecoding(nurseSchedule[n][d])
											+ " to nurseSchedule["+(n+1) +"]["+(d+1)+"]=" + shiftDecoding(otherShift)
											+ "\n and from nurseSchedule["+(numberOtherNurse+1) +"]["+(d+1)+"]=" + shiftDecoding(nurseSchedule[numberOtherNurse][d])
											+ " to nurseSchedule["+(numberOtherNurse+1) +"]["+(d+1)+"]=" + shiftDecoding(shift));
									nurseSchedule[n][d]=otherShift;
									nurseSchedule[numberOtherNurse][d]=shift;
									loadScheduled();
									switched=true;
									System.out.println("Vertical: SWITCHED on day"+( d+ 1 )+ " for nurse"+(n+1)+ " (Shift"+ shiftDecoding(shift)
										+ ") ("+prefShiftNurse + " to " + prefShiftNurseForOtherShift + ") with nurse "+(numberOtherNurse+1)+" (OtherShift"+ shiftDecoding(otherShift) + ") ("+prefShiftOtherNurse + " to " + prefShiftOtherNurseForShift + ")");
									
								}
								else{
									numberOtherNurse +=1; 
								}
							}
							numberOtherNurse=0; 
						}
						switched=false;
					}
			}
		}
		System.out.println("______________________type 2______________________");
		System.out.println("T1 to T2" + type1NurseAssignedToType2Roster.toString());
		String gg="nursesR2: ";
		for( int i =0; i<nursesR2.length;i++){
		gg+=	nursesR2[i] + ", ";
		}
		System.out.println(gg);

		int f =0; //index numberOtherNurse
		numberOtherNurse=nursesR2[f]; 
		for(int n=0;n<nursesR2.length;n++){ // alle nurses nagaan van die preferences 
			for(int d=0;d< DAYS;d++){ //al hun toegewezen shifts nagaan (met TOTALdS= 28) 
				int shift = nurseSchedule[nursesR2[n]][d];
				int prefShiftNurse=r.readPreference(nursesR2[n], d, shiftDecoding(shift),department);
				if(prefShiftNurse >= 10){ //als de nurse een shift heeft met een preferentie boven 10
					//welke shiften mag de nurse nog doen?
					while(numberOtherNurse < numberOfNurses && switched==false ){ //loop voor de andere nurses af te gaan, stopt ook wanneer een switch werd gedaan
						int otherShift= nurseSchedule[numberOtherNurse][d];
						int prefShiftOtherNurse=r.readPreference(numberOtherNurse, d, shiftDecoding(otherShift),department);//current pref of other nurse
						int prefShiftNurseForOtherShift = r.readPreference(nursesR2[n], d, shiftDecoding(otherShift),department);//other shift for this nurse
						int prefShiftOtherNurseForShift = r.readPreference(numberOtherNurse, d, shiftDecoding(shift),department);//other shift for other nurse
						loadScheduled();
						if(checkSwitchable(nursesR2[n],numberOtherNurse,d,shift, otherShift,prefShiftNurse, prefShiftOtherNurse,
								prefShiftNurseForOtherShift, prefShiftOtherNurseForShift)==true){
							//hoevaak hierin
							System.out.println("SWITCH from: nurseSchedule["+(nursesR2[n]+1) +"]["+(d+1)+"]=" + shiftDecoding(nurseSchedule[nursesR2[n]][d])
									+ " to nurseSchedule["+(nursesR2[n]+1) +"]["+(d+1)+"]=" + shiftDecoding(otherShift)
									+ "\n and from nurseSchedule["+(numberOtherNurse+1) +"]["+(d+1)+"]=" + shiftDecoding(nurseSchedule[numberOtherNurse][d])
									+ " to nurseSchedule["+(numberOtherNurse+1) +"]["+(d+1)+"]=" + shiftDecoding(shift));
							nurseSchedule[nursesR2[n]][d]=otherShift;
							nurseSchedule[numberOtherNurse][d]=shift;
							loadScheduled();
							switched=true;
						}
						else{
							System.out.println("\nNOT SWITCHED on day "+( d+ 1 )+ " for nurse(includes 0!) "+(nursesR2[n]) + " (Shift"+ shift + ") ("+prefShiftNurse + " to " + prefShiftNurseForOtherShift + ") with nurse "+(numberOtherNurse)+" (OtherShift"+ otherShift + ") ("+prefShiftOtherNurse + " to " + prefShiftOtherNurseForShift + ")");
							f++;
							if (f<nursesR2.length)
								numberOtherNurse = nursesR2[f]; 
							else
								numberOtherNurse=numberOfNurses;
						}
					}
					f=0;
					numberOtherNurse=nursesR2[f]; 
				}
				switched=false;
			}
		}
		
		
		
		
	}
	public void horizontalSwapping()
	{ 
            reader r = new reader();
            
		loadScheduled();
		boolean vlag=false;
		String output="";
		
		System.out.println("\n_______________________________Start Horizontal Swapping___________________________________");
		
		int dayLowPref=0;
   		int shiftLowPref=0;
   		for(int n=0;n<numberOfNurses;n++){ 
   			System.out.println("______________________________NURSE: " + (n+1) +"_______________________\n");
				
   			for(int day=0;day<DAYS;day++){
		   			int shift = nurseSchedule[n][day]; 
		   			if(shift<numberOfShifts-1 && enoughReqNurses(n,day,nurseType[n],shiftDecoding(shift))){
		   				int prefShiftNurse=r.readPreference(n, day, shiftDecoding(shift),department); 
		   				if(prefShiftNurse>=15 ){
		   					int low =10; 
		   					for (int d = 0; d < DAYS; d++){ 
		   						System.out.println("Day: " + (d+1) + " has shift " + nurseSchedule[n][d]);
		   						if(nurseSchedule[n][d]==numberOfShifts-1){
	   								for(int s=0;s<numberOfShifts-1;s++){
		   					   			
		   								if (r.readPreference(n, d, shiftDecoding(s),department)<low 
		   										&& checkNightShiftRule(n,d,s)
		   										&& daysOfConsWork(n, d) ){//will save that day and shift with the lowest possible preference and that respects the nightShiftRule
		   									low = r.readPreference(n, d, shiftDecoding(s),department);
		   									dayLowPref=d;
		   									shiftLowPref=s;
		   									vlag=true;
		   									System.out.println("\tDay " + (day+1) + " S" + shiftDecoding(shift) + "("+ prefShiftNurse 
		   				   						+ ")\tDay" + (dayLowPref+1)+ " S" + shiftDecoding(shiftLowPref) + "("+ low+ ")");
		   				   				}
		   							}
		   						}
		   					}
		   					if(vlag){
		   						loadScheduled();//scheduled[][][] opnieuw vullen
		   						output=("SWITCHED Day " + (day+1) + " S" + shiftDecoding(shift) + "("+ prefShiftNurse 
				   							+ ")\tDay " + (dayLowPref+1)+ " S" + shiftDecoding(nurseSchedule[n][dayLowPref]) + "("
				   							+ r.readPreference(n, dayLowPref, shiftDecoding(nurseSchedule[n][dayLowPref]),department)+ ")");
				   				//aanpassing nurseSchedule
			   					nurseSchedule[n][dayLowPref]=shiftLowPref;
			   					nurseSchedule[n][day]=numberOfShifts-1;
			   					
			   					output+=("\n\tto Day " + (day+1) + " S" + shiftDecoding(nurseSchedule[n][day])+ "("
			   							+ r.readPreference(n, day, shiftDecoding(nurseSchedule[n][day]),department) +")\tDay " 
			   							+ (dayLowPref+1) + " S" + shiftDecoding(nurseSchedule[n][dayLowPref])+ "("
			   							+ r.readPreference(n, dayLowPref, shiftDecoding(nurseSchedule[n][dayLowPref]),department) +")");
			   					//
			   					System.out.println("NURSE: " + (n+1) + ": " +output);
			   					
			   					loadScheduled();//scheduled[][][] opnieuw vullen
			   					dayLowPref=0;
			   					shiftLowPref=0;
			   					low=10;
			   					vlag=false;
			   							
		   					}
		   				}
		   			}
		   	}
   		}
   		
	 }
	public void avoidIslands()
	{
		System.out.println("_____START avoidIslands____");
			
            reader r = new reader();
		 
	   	
		for(int n=0;n < numberOfNurses;n++) //per nurse nagaan 
		{ 
			int dayLowPref=999;
		   	int shiftLowPref=999;
		   	int shift =0;
			System.out.println("\nNURSE: "+(n+1));
			int consDaysOfWork=0; 
			
			loadScheduled(); 
			for(int day=0; day<DAYS; day++){ //dagen afgaan 
			shift = nurseSchedule[n][day]; 
				boolean founded =false; 
				
				if(shift != (numberOfShifts-1)){ //als de shift verschillend is aan de free shift
					consDaysOfWork+=1;
					System.out.print("Day " + (day+1) + "nurse works on Shift " + shiftDecoding(nurseSchedule[n][day])); 
					System.out.println("\tConsecDaysOfWork: " + consDaysOfWork);
				}
				else{ //als de shift een free shift is
					int tempDay=0; 
					int tempShift=0; 
					if(consDaysOfWork==1){ //1TJES WEGWERKEN!!
						System.out.println("ISLAND of one day on day "+(day)); //er stond day+1??
						if(enoughReqNurses(n,day-1,nurseType[n],shiftDecoding(nurseSchedule[n][day-1]))==true){
							tempDay=(day-1); 
							tempShift=nurseSchedule[n][day-1];
							nurseSchedule[n][day-1]=numberOfShifts-1; //wordt free shift 
							System.out.println("--> NEW FREE shift on userday "+ (day+1)); 
							founded =true;
					    } 
					}	
					else if(consDaysOfWork>=MAXCONSDAYSOFWORK){//TEVEEL DAGEN!
						System.out.println("TOO MANY DAYS of work on day " + (day) + ": " + consDaysOfWork  ); //er stond day +1 maar het zou day-1 moeten zijn +1 voor de echted ag
						if(founded==false ){ //zolang we geen dag hebben gevonden verder zoeken
							loadScheduled();
							int j=1; //de voorgaande dag is degene die de werkende dag is. 
							while(day-j>=0 && nurseSchedule[n][day-j]!=(numberOfShifts-1) && founded ==false) //day niet neg && die dag werkt men && nog geen dag gevonden
							{
								System.out.println("While loop:  dag:" + (day-j+1) +  " shift: " + shiftDecoding(nurseSchedule[n][day-j]) 
										+ " Pref " +  r.readPreference(n,day-j,shiftDecoding(nurseSchedule[n][day-j]),department));
								if(r.readPreference(n,day-j,shiftDecoding(nurseSchedule[n][day-j]),department) > 15 //preferences boven 15
										&& enoughReqNurses(n,day-j,nurseType[n],shiftDecoding(nurseSchedule[n][day-j]))) //genoeg nurses op die shift
								{ 
									tempDay=(day-j); 
									tempShift=nurseSchedule[n][day-j];
									
										nurseSchedule[n][day-j]=numberOfShifts-1; //wordt free shift
										System.out.println("--> NEW FREE shift on userday"+ (day-j+1)); 
										founded =true;
								}
								j++; 
							}
							if(founded==false) //als geen enkele dag boven de 15 zit van de dagen of voldoet enoughReqNurses
							{
								if(enoughReqNurses(n,day-1,nurseType[n],shiftDecoding(nurseSchedule[n][day-1]))) //mag er een nurse worden verwijdert op de laatste dag van die serie 
								{
									
									tempDay=(day-1); 
									tempShift=nurseSchedule[n][day-1];
									nurseSchedule[n][day-1]=numberOfShifts-1; //die dag wordt op nul gezet
									System.out.println("--> NEW FREE shift on userday"+ (day)); 
									founded =true;
								}
								else if(enoughReqNurses(n,day-consDaysOfWork+1,nurseType[n],shiftDecoding(nurseSchedule[n][day-consDaysOfWork+1]))) //mag er een nurse worden verwijdert op de eerste dag van die serie 
								{
									tempDay=(day-consDaysOfWork+1); 
									tempShift=nurseSchedule[n][day-consDaysOfWork+1];
									
									nurseSchedule[n][day-consDaysOfWork+1]=numberOfShifts-1; //die dag wordt op nul gezet
									System.out.println("--> NEW FREE (first) shift on userday"+ (day-consDaysOfWork)); 
									founded =true;
								}
								else{
									System.out.println("NOG EEEN PROBLEEEEEM"); 
								}
									
							} 
						} 
					}		
					else //NO PROBLEM 
						consDaysOfWork=0; 
					
					
					loadScheduled();
					boolean adjusted=false;
					if(founded ==true)
					{   
						//vrije dag vinden met een preferentie voor te werken en een shift dat voldoet aan de night shift rule en daysofconsWork 
						int low=200; 
						for(int d=0; d<DAYS; d++)
						{   
							if(nurseSchedule[n][d]== numberOfShifts-1) //if gelijk aan vrije dag
							{	
								if(daysOfConsWork(n,d)==true) //als het toegelaten is om er een dag bij te zetten
								{
									for(int s=0; s<numberOfShifts-1;s++) //nagaan welke shift het beste (niet de vrije) 
									{
										if(checkNightShiftRule(n,d, s) ==true) //voldoet aan night shift rule 
										{ 
											if(r.readPreference(n,d,shiftDecoding(s),department)<low) 
											{
												low = r.readPreference(n, d, shiftDecoding(s),department);
												dayLowPref=d;
												shiftLowPref=s;
												
												adjusted=true;
											}
										}
									}
								 }
							}
						}
						if(adjusted){
							nurseSchedule[n][dayLowPref]=shiftLowPref;
							loadScheduled();
						}
						System.out.println("---> NEW shift voor nurse "+(n+1)+" op dag "+ (day+1)+" S" + shiftDecoding(shiftLowPref) + " PREF:  " + shiftLowPref); 
					}
					consDaysOfWork=0; 
					if (!adjusted && founded)
					{
						nurseSchedule[n][tempDay]=tempShift;
					}
					adjusted=false;
				}
			}//alle dage afgegaan
		}//alle  nurses afgegaan
		System.out.println("____END avoidIslands____"); 
	}//close avoidIslands
	public int howMuchSurplus(int nurse,int day,int type,int sh)
	{
            reader r = new reader();
		// doorgegeven type (nurseType[]) is 1 of 2!!
		if (type1NurseAssignedToType2Roster.contains(nurse)){
			if (scheduled[type][day][sh]>r.readRequirements(type,shift[sh],department)){
				return (scheduled[type][day][sh]-r.readRequirements(type,shift[sh],department));
				}
			else return 0;
			}
		else{
			
			if (scheduled[type-1][day][sh]>r.readRequirements(type-1,shift[sh],department)){
				return (scheduled[type-1][day][sh]-r.readRequirements(type-1,shift[sh],department));
				}
			else return 0;
			}
	}	
	public boolean checkNightShiftRule(int nurse,int day, int shift) //GROVE FOUT!!! 
	{
			if (shift==0)//als de shift waarmee men wilt wisselen een early shift is dan 
			{
				if(day==0 || nurseSchedule[nurse][day-1]==0|| nurseSchedule[nurse][day-1]==5)//de vorige dag E of F
					return true; //alle shiften zijn toegelaten op de volgende dag
				else return false; 
			}
		    else if(shift==1) //als de shift een DAY1 shift is dan 
			{
				if(day==0 || nurseSchedule[nurse][day-1]==0 || nurseSchedule[nurse][day-1]==1|| nurseSchedule[nurse][day-1]==5)//de vorige dag E,D1,F toegelaten
				{
					if(day==DAYS-1 || nurseSchedule[nurse][day+1]!=0) //einde vd maand of de dag nadien verschillend van de early
						return true; 
					else return false; 
				}
				else return false; 
					
			}
		    else if(shift==2)//als de shift een DAY2 is 
		    {
		    	if(day==0 || nurseSchedule[nurse][day-1]==0 || nurseSchedule[nurse][day-1]==1 || nurseSchedule[nurse][day-1]==2|| nurseSchedule[nurse][day-1]==5)//vorige dag is E,D1,D2,F toegelaten
				{
					if(day==DAYS-1 || nurseSchedule[nurse][day+1]==2 || nurseSchedule[nurse][day+1]==3|| nurseSchedule[nurse][day+1]==4|| nurseSchedule[nurse][day+1]==5) //einde vd maand of de dag nadien 
						return true; 
					else return false; 
				}
		    	else return false; 
		    }
		    else if(shift==3)//als de shift een L is
		    {
		    	if(day==0 || nurseSchedule[nurse][day-1]!=4)//vorige dag is E,D1,D2,L,F toegelaten (dus alles behalve ne night)
				{
					if(day==DAYS-1 || nurseSchedule[nurse][day+1]==3 ||  nurseSchedule[nurse][day+1]==4|| nurseSchedule[nurse][day+1]==5) //einde vd maand of de dag nadien 
						return true; 
					else return false; 
				}
		    	else return false; 
		    }	
		    else if(shift==4)//shift N
		    {
		    	//vorige dag is alles toegelaten
		    	if(day==DAYS-1 || nurseSchedule[nurse][day+1]==4|| nurseSchedule[nurse][day+1]==5) //einde vd maand of N & F toegelaten 
					return true; 
		    	else return false; 
				
		    }
		    else if(shift==5)//shift F
		    	return true; //alles mag de dag ervoor en de dag nadien
		    else return false; 
	}
	public void rearrangeSurplusRandom()
	{
            
            reader r = new reader();
            
		System.out.println(""); 
		System.out.println(""); 
		System.out.println("_________start reArrangeSurplusRandom__________"); 
		boolean found =false; 
		
		for(int n=0;n<numberOfNursesType1;n++)//TYPE 1 nurses
		{
			if(!type1NurseAssignedToType2Roster.contains(n))
			{
				System.out.println("________________________________Nurse: "+(n+1) + "________________________");
				for(int d=0; d<DAYS; d++)//alle dagen d afgaan 
				{
					found=false; 
					System.out.println("    CHECK UP: Not FRee: "+(nurseSchedule[n][d]!=numberOfShifts-1)+" && Enough nurses "+(enoughReqNurses(n,d,nurseType[n],shiftDecoding(nurseSchedule[n][d])))); 
					
					if(nurseSchedule[n][d]!=numberOfShifts-1 && howMuchSurplus(n,d,nurseType[n],shiftDecoding(nurseSchedule[n][d]))>0 && howMuchSurplus(n,d,nurseType[n],shiftDecoding(nurseSchedule[n][d]))<2) //men werkt en genoeg op die shift
					{
						//zoeken naar de nurse op die shift met de hoogste penalty voor die shift
						int highest=0; 
						int nurseHighest=100; 
						System.out.println("start search: nursehighest = "+nurseHighest); 
						 
						int on=0; 
						
						while(on < numberOfNursesType1)
						{
						   System.out.println("nurseSchedule[n][d] "+nurseSchedule[n][d]+" == "+nurseSchedule[on][d]+" nurseSchedule[on][d]  ( d: "+d+" n: "+n+" on: "+on+")"); 
						   
						   if(nurseSchedule[n][d]==nurseSchedule[on][d] && type1NurseAssignedToType2Roster.contains(on)==false)
						   {   
							   if(r.readPreference(on,d,shiftDecoding(nurseSchedule[on][d]),department) > highest) 
							   {
								  if(r.readPreference(on, d, shiftDecoding(nurseSchedule[on][d]),department)>=10){
									   	highest = r.readPreference(on, d, shiftDecoding(nurseSchedule[on][d]),department);
									   	nurseHighest=on;
									   	System.out.println("NURSEHIGHEST "+(nurseHighest+1)+" GEVONDEN met pref "+r.readPreference(on, d, shiftDecoding(nurseSchedule[on][d]),department)); 
								  }
							   }
							}
						    on++; 
						    
						 }
					System.out.println("alle nurses afgelopen en nurseHighest "+nurseHighest); 
					
						//plaats die op een andere dag dat mag voor haar preferentie en voor nightshiftrule en voor consecutive days eventueel. 
						int od=0; 
						
						while(nurseHighest!=100 && found==false && od<DAYS)//andere dag zoeken met een vrije shift
						{
							
							System.out.println("   Search for day "+od+ " to find other shift for nurseHighest "+nurseHighest); 
							
							System.out.println("   CHECKUP: NOT SAME DAY: "+ (od!=d) + " gevonden?: " +(found==false)+" fREE: "+ (nurseSchedule[nurseHighest][od]==numberOfShifts-1)); 

							if(od!=d && found==false && nurseSchedule[nurseHighest][od]==numberOfShifts-1)
							{
								int s=0; 
								while(found==false && s<numberOfShifts-1)
								{
									System.out.println("    CHECKUP: night shift rule: "+checkNightShiftRule(nurseHighest,d,s) +" && daysOfConsWork: "+daysOfConsWork(nurseHighest, d)); 
									
									if(checkNightShiftRule(nurseHighest,d,s) && daysOfConsWork(nurseHighest, od)  )
									{
										System.out.println("---->REARRANGE: nurse "+(nurseHighest+1)+" stops with working on day "
												+(d+1)+" S: "+shiftDecoding(nurseSchedule[nurseHighest][d])+" and starts on day "+(od+1)+" S: "+shiftDecoding(s)
												);
										nurseSchedule[nurseHighest][d] = (numberOfShifts-1); //shift op nul zetten
										nurseSchedule[nurseHighest][od]=s;
										
										
										loadScheduled(); 
										System.out.println("--------->herladen scheduled"); 
										found=true; //niet meer zoeken om een andere nurses zoeken
										nurseHighest=100; 
									}
									s++; 
	
								}
							}
							od++; 
						}	
					}	
				}
			}
		}
		found=false;
		System.out.println("xxxxxxxxxxxx voor type 2 xxxxxxxxxxxxx");
		
		for(int n=0;n<nursesR2.length;n++)//TYPE  nurses
		{
			System.out.println("________________________________Nurse: "+(nursesR2[n]+1) + "________________________");
			System.out.println(type1NurseAssignedToType2Roster.toString());
			for(int d=0; d<DAYS; d++)//alle dagen d afgaan 
			{
				found=false; 
				loadScheduled();
				if(nurseSchedule[nursesR2[n]][d]!=(numberOfShifts-1) 
						&& howMuchSurplus(nursesR2[n],d,nurseType[nursesR2[n]],shiftDecoding(nurseSchedule[nursesR2[n]][d]))>0 
						&& howMuchSurplus(nursesR2[n],d,nurseType[nursesR2[n]],shiftDecoding(nurseSchedule[nursesR2[n]][d]))<4 ) //men werkt en genoeg op die shift
				{
					int highest=0; 
					int nurseHighest=100; 
					
					int on=0; 
					
					while(on<nursesR2.length)
					{
					   if(nurseSchedule[nursesR2[n]][d]==nurseSchedule[nursesR2[on]][d] )//andere nurse zoeken die dan werkt!
					   {
						   if(r.readPreference(nursesR2[on],d,shiftDecoding(nurseSchedule[nursesR2[on]][d]),department)>highest) 
						   {
							   if(r.readPreference(nursesR2[on],d,shiftDecoding(nurseSchedule[nursesR2[on]][d]),department) >=10)
							   {
								   highest = r.readPreference(on, d, shiftDecoding(nurseSchedule[nursesR2[on]][d]),department);
								   nurseHighest=nursesR2[on]; 
							 	}  
							   
						   }
						}
					    on++; 
					 }
				
					//plaats die op een andere dag dat mag voor haar preferentie en voor nightshiftrule en voor consecutive days eventueel. 
					int od=0; 
					
					while(nurseHighest!=100 && found==false && od<DAYS )//andere dag zoeken
					{
						System.out.println("day "+od+ " with nurseHighest "+nurseHighest+", we'll search another day to switch with"); 

						if(od!=d && found==false && nurseSchedule[nurseHighest][od]==numberOfShifts-1)//shift Shift niet de vrije shift bekijken
						{						
							int s=0; 
							while(found == false && s< numberOfShifts-1)
							{
								if(checkNightShiftRule(nurseHighest,d,s) && daysOfConsWork(nurseHighest, od) )
								{
										System.out.println("-->REARRANGE: nurse "+(nurseHighest+1)+" stops with working on day "
										+(d+1)+" S: "+shiftDecoding(nurseSchedule[nurseHighest][d])+" and starts on day "+(od+1)+" S: "+(shiftDecoding(s)));
												
										nurseSchedule[nurseHighest][d] = (numberOfShifts-1); //shift op nul zetten
										nurseSchedule[nurseHighest][od]=s;
										
										loadScheduled(); 
										System.out.println("--->herladen scheduled"); 
										found=true; //niet meer zoeken om een andere nurses zoeken
										nurseHighest=100; 	
								}
								s++; 
							}
							
						}
						od++; 
					}	
				}
			}
		}
		System.out.println("________stop rearrange random surplus____________" ); 
	}
        
        public void printOutput(){
            
            reader re = new reader();
            
		int workingDays=0;
		int penalty=0;
		int totalPenalty=0;
		String str="";
		textMonthlyRoster="There are " + assignedNurses.size() + " nurses assigned:\n";
		String txt="";
		costs();
		
		for (int n =0;n<numberOfNurses;n++)	{workingDays=0;penalty=0;
			if (assignedNurses.contains(n))	{
				textMonthlyRoster+="\tNurse " + nurseID[n] + ": " + nurseEmploymentRate[n]*100 + "% employed does roster " + (nurseDoes[n]+1)+"\n";
				for (int d=0; d<DAYS;d++){
					if(shiftDecoding(nurseSchedule[n][d])==0)
						str=" FR("+ re.readPreference(n, d, shiftDecoding(nurseSchedule[n][d]),department) +")\t";
					else
						str=" S" + shiftDecoding(nurseSchedule[n][d])+ "("+ 
								re.readPreference(n, d, shiftDecoding(nurseSchedule[n][d]),department) +")\t";
					textMonthlyRoster+="Day " + (d+1) + str;
					if(shiftDecoding(nurseSchedule[n][d])!=0)
							workingDays++;
					penalty+=re.readPreference(n, d, shiftDecoding(nurseSchedule[n][d]),department);
					}
				totalPenalty+=penalty;
				}
			else
				textMonthlyRoster+="Nurse " + (n+1) + ": " + nurseEmploymentRate[n]*100 + "% employed is currently not employed\n";
			textMonthlyRoster+="\n==> works: " + workingDays +" days of max allowed: " + maxAss[n] + ". Total PreferenceScore: " + penalty +"\n\n";
			}
		for(int r=0;r<numberOfRostersType1+numberOfRostersType2;r++){
			for(int n=0; n<numberOfNurses; n++){
				if(nurseDoes[n]==r)
					txt+="Roster " + (r+1)+ " ==> nurse " + nurseID[n] +"\n";
			}
		}
		System.out.println("SCHEDULE: \n" + textMonthlyRoster);
			
		CSVWriter writer;
        try{
            //writer = new CSVWriter(new FileWriter("C:\\Users\\julie.MATTIS\\OneDrive\\Documenten\\AOR\\test.csv"));
            writer = new CSVWriter(new FileWriter("C:\\Users\\Ruth Hofmans\\Desktop\\input example\\test"+department+".csv"));
            String[][] lines= new String[100][100];
            //int lineNumber = 3;
            
            for (int n=0; n<numberOfNurses;n++)
            {
                lines[n][0] = nurseID[n];
                for (int d=0; d<DAYS;d++)
                {
                    int shift = nurseSchedule[n][d];
                    int dag = d+1;
                    lines[n][dag]=Integer.toString(shiftDecoding(shift)); //ophalen shift van die nurse!
                    }
                  
                }
               
  
         
         java.util.List<String[]> data = new ArrayList<String[]>();
         for (int n=0;n<numberOfNurses;n++)
         {
          String total = new String();   
             for (int d=0;d<=DAYS;d++)
             {
                 total += lines[n][d]+";";
             }
            data.add(new String[]{total});
         
         }
         writer.writeAll(data);
         writer.close();
            
               
     }
         catch (IOException e) {
			e.printStackTrace();
        }

                
		}
        
        public void loadScheduled()
		{
			for (int t=0;t<TYPES;t++)
			{
				for (int d=0;d<DAYS;d++)
				{
					for (int s=0;s<numberOfShifts;s++)
						{
						scheduled[t][d][s]=0;
						//System.out.println("number of shifts: " +  numberOfShifts + " scheduled: for type "
						//+(t+1) + " on day " + (d+1) + " and usershift: "+ s+  " is " + scheduled[t][d][s]);
						}
				}
			}
			for (int n=0;n<numberOfNurses;n++){
				if (assignedNurses.contains(n)){
					evaluateColumnSimplified(n);
				}
			}
		}
        
        public void evaluateColumnSimplified(int n)
		{
                    reader r = new reader();
                    
			//die 'i' hier moet een 'n' zijn, is 'een' nurse
			hh =0;
			countAss=0; 
			countConsecWork=0;
			countConsec=0;
			for (int s=0;s<numberOfShifts;s++)
				countShift[s]=0;
			
			a=nurseSchedule[n][0];//nurse, days, day 1  getNurseForRosterType1[r]=n;
			violations[0] += r.readPreference(n, 0, shiftDecoding(nurseSchedule[n][0]),department);
			
			if (a<numberOfShifts-1)//er wordt gewerkt
			{countAss++; countConsecWork++;countConsec++;}
			
			countShift[a]++;
			if (type1NurseAssignedToType2Roster.contains(n))
				scheduled[nurseType[n]][0][shiftDecoding(nurseSchedule[n][0])]++;
			else
				scheduled[nurseType[n]-1][0][shiftDecoding(nurseSchedule[n][0])]++;
			
			
			
			for (int d=1;d<DAYS;d++)
			{
				violations[0]+= r.readPreference(n, d, shiftDecoding(nurseSchedule[n][d]),department);
				
				int shiftCurrentDay = nurseSchedule[n][d];
				int shiftPreviousDay = nurseSchedule[n][d-1];
				//System.out.println("SUPPORT: nurse " + nurseID[n] + " shift day " + (d+1) + " = " +cyclicRostersType1[n][d] 
				//		+" day before " + d + " =  " +cyclicRostersType1[n][d-1] );
				
				if (type1NurseAssignedToType2Roster.contains(n))
					scheduled[nurseType[n]][d][shiftDecoding(shiftCurrentDay)]++;
				else
					scheduled[nurseType[n]-1][d][shiftDecoding(shiftCurrentDay)]++;
								
				// Minimum - Maximum number of assignments (assignments of each shift type)
				if (shiftCurrentDay<numberOfShifts-1)
				{
					countAss++;
					countShift[shiftCurrentDay]++;
					countConsecWork++;
				}
				// Minimum - Maximum number of consecutive assignments
				else if ((shiftCurrentDay==numberOfShifts-1) &&(shiftPreviousDay<numberOfShifts-1)) // doesn't work today, did work yesterday
				{
					if (countConsecWork>maxConsecWork[n])
					{
						violations[1]++;// Sum the number of times the maximum consecutive assignments constraint is violated
						System.out.println("CONSTRAINT 1 nurse " + (n+1) + "D" + (d+1) + ":countConsecWork " + countConsecWork + " maxConsecWork[n] " + maxConsecWork[n]);
					}
					countConsecWork=0;
				}
				// Minimum - Maximum number of consecutive assignments of same working shifts
				if (shiftCurrentDay!=shiftPreviousDay)
				{
					
					if (countConsec > maxConsecPerShiftType[n][(shiftPreviousDay)] )
					{
						violations[2]++; // Sum the number of times the maximum consecutive assignments of the same shift type constraint is violated	
					//	System.out.println("CONSTRAINT 2: concerns nurse" + nurseID[n] + 
					//	" countConsec: " + countConsec 	+ " compared to max: " + maxConsecPerShiftType[n][(shiftPreviousDay)]
					//	+ " results in total of " + violations[2]);
					}
					countConsec =0;
					countConsec++;
				}
				else
				{
					countConsec++;
				}
			}
			if (countAss< minAss[n])
				violations[3]++; // Sum the number of times the constraint 'Minimum number of assignments' is violated.
			if (countAss > maxAss[n])
				violations[4]++; // Sum the number of times the constraint 'Maximum number of assignments' is violated.
		}
        
        public void evaluateSolution()
		{
                    reader r = new reader();
                        for (int i= 0; i<20;i++)
			violations[i]=0;
			
			loadScheduled();
			
			textConstraints=("The total preference score is " + violations[0]);
			textConstraints+=("\n\nThe constraint 'maximum number of consecutive working days' is violated " +violations[1]+" times.");
			textConstraints+=("\nThe constraint 'maximum number of consecutive working days per shift type' is violated " + violations[2] +" times.");
			textConstraints+=("\nThe constraint 'minimum number of assignments' is violated "+ violations[3] +" times." );
			textConstraints+=("\nThe constraint 'maximum number of assignments' is violated " + violations[4] + " times.");
		
			textConstraints+=("\n\nThe staffing requirements are violated as follows:\n");
			for (int d = 0;d<DAYS;d++)
			{
				for (int s =1; s<numberOfShifts;s++ )
				{
					for(int t=0; t<TYPES;t++)
					{
						a=scheduled[t][d][s];
						
						if(a<r.readRequirements(t, shift[s],department))
							{textConstraints+=("There are too few nurses of type "+ (t+1) + " in shift " + s + " on day " + (d+1)
									+ " : " + a + " < " + r.readRequirements(t, shift[s],department) + ".\n");
							kappa++;
							}
						else if (a>r.readRequirements(t, shift[s],department))
							textConstraints+=("There are too many nurses of type "+ (t+1) +" in shift " + s + " on day " + (d+1)
									+ " : " + a + " > " + r.readRequirements(t, shift[s],department) + ".\n");
					}
				}
			}
			/*JTextArea textArea = new JTextArea(textConstraints);
			JScrollPane scrollPane = new JScrollPane(textArea);  
			textArea.setLineWrap(true);  
			textArea.setWrapStyleWord(true); 
			scrollPane.setPreferredSize( new Dimension( 500, 500 ) );
			JOptionPane.showMessageDialog(null, scrollPane, "VIOLATIONS",  
					JOptionPane.WARNING_MESSAGE);*/
		}
        
        
        
        public void costs()
	{
            reader r = new reader();
            //r.readWages();
       
		costType1=0;
		costType2=0;
		costTotal=0;
		
		for (int n=0; n<numberOfNursesType1;n++){
			for(int d=0; d<DAYS;d++){
				if (d==5||d==6||d==12||d==13||d==19||d==20||d==26||d==27){//weekend ==> 1
					if(startShift[shiftDecoding(nurseSchedule[n][d])]==6){//early
						costType1+=r.readWages2(1,3);
						//System.out.println("Weekend COST nurse: " + (n+1) + " day " +(d+1) + " = " + db.getWage(1, 0, 0));
					}
					else if (startShift[shiftDecoding(nurseSchedule[n][d])]==9||startShift[shiftDecoding(nurseSchedule[n][d])]==12){//day
						costType1+=r.readWages2(2,3);
						//System.out.println("WeekendCOST nurse: " + (n+1) + " day " +(d+1) + " = " + db.getWage(1, 0, 1));
					}
					else if (startShift[shiftDecoding(nurseSchedule[n][d])]==15||startShift[shiftDecoding(nurseSchedule[n][d])]==18){//late
						costType1+=r.readWages2(3,3);
						//System.out.println("WeekendCOST nurse: " + (n+1) + " day " +(d+1) + " = " + db.getWage(1, 0, 2));
					}
					else if (startShift[shiftDecoding(nurseSchedule[n][d])]==21){//night
						costType1+=r.readWages2(4,3);
						//System.out.println("WeekendCOST nurse: " + (n+1) + " day " +(d+1) + " = " + db.getWage(1, 0, 3));
					}
				}
				else{
					if(startShift[shiftDecoding(nurseSchedule[n][d])]==6){//early
						costType1+=r.readWages2(1,2);
						//System.out.println("COST nurse: " + (n+1) + " day " +(d+1) + " = " + db.getWage(0, 0, 0));
					}
					else if (startShift[shiftDecoding(nurseSchedule[n][d])]==9||startShift[shiftDecoding(nurseSchedule[n][d])]==12){//day
						costType1+=r.readWages2(2,2);
						//System.out.println("COST nurse: " + (n+1) + " day " +(d+1) + " = " + db.getWage(0, 0, 1));
					}
					else if (startShift[shiftDecoding(nurseSchedule[n][d])]==15||startShift[shiftDecoding(nurseSchedule[n][d])]==18){//late
						costType1+=r.readWages2(3,2);
						//System.out.println("COST nurse: " + (n+1) + " day " +(d+1) + " = " + db.getWage(0, 0, 2));
					}
					else if (startShift[shiftDecoding(nurseSchedule[n][d])]==21){//night
						costType1+=r.readWages2(4,2);
						//System.out.println("COST nurse: " + (n+1) + " day " +(d+1) + " = " + db.getWage(0, 0, 3));
					}
				}
			}
		}
		for (int n=numberOfNursesType1; n<numberOfNurses;n++){
			for(int d=0; d<DAYS;d++){
				if (d==5||d==6||d==12||d==13||d==19||d==20||d==26||d==27){//weekend ==> 1
					if(startShift[shiftDecoding(nurseSchedule[n][d])]==6){//early
						costType2+=r.readWages2(5,3);
                                                //System.out.println("WeekendCOST nurse: " + (n+1) + " day " +(d+1) + " = " + db.getWage(1, 1, 0));
					}
					else if (startShift[shiftDecoding(nurseSchedule[n][d])]==9||startShift[shiftDecoding(nurseSchedule[n][d])]==12){//day
						costType2+=r.readWages2(6,3);
						//System.out.println("WeekendCOST nurse: " + (n+1) + " day " +(d+1) + " = " + db.getWage(1, 1, 1) );
					}
					else if (startShift[shiftDecoding(nurseSchedule[n][d])]==15||startShift[shiftDecoding(nurseSchedule[n][d])]==18){//late
						costType2+=r.readWages2(7,3);
						//System.out.println("WeekendCOST nurse: " + (n+1) + " day " +(d+1) + " = " + db.getWage(1, 1, 2));
					}
					else if (startShift[shiftDecoding(nurseSchedule[n][d])]==21){//night
						costType2+=r.readWages2(8,3);
						//System.out.println("WeekendCOST nurse: " + (n+1) + " day " +(d+1) + " = " + db.getWage(1, 1, 3));
					}
				}
				else{
					if(startShift[shiftDecoding(nurseSchedule[n][d])]==6){//early
						costType2+=r.readWages2(5,3);
						//System.out.println("COST nurse: " + (n+1) + " day " +(d+1) + " = " + db.getWage(0, 1, 0));
					}
					else if (startShift[shiftDecoding(nurseSchedule[n][d])]==9||startShift[shiftDecoding(nurseSchedule[n][d])]==12){//day
						costType2+=r.readWages2(6,3);
						//System.out.println("COST nurse: " + (n+1) + " day " +(d+1) + " = " + db.getWage(0, 1, 1));
					}
					else if (startShift[shiftDecoding(nurseSchedule[n][d])]==15||startShift[shiftDecoding(nurseSchedule[n][d])]==18){//late
						costType2+=r.readWages2(7,3);
						//System.out.println("COST nurse: " + (n+1) + " day " +(d+1) + " = " + db.getWage(0, 1, 2));
					}
					else if (startShift[shiftDecoding(nurseSchedule[n][d])]==21){//night
						costType2+=r.readWages2(8,3);
						//System.out.println("COST nurse: " + (n+1) + " day " +(d+1) + " = " + db.getWage(0, 1, 3));
					}
				}
			}
		}
		costTotal=Math.round((costType1+costType2)*100)/100;
		costType1=Math.round(costType1*100)/100;
		costType2=Math.round(costType2*100)/100;
	}
        
        public int getNurseScheduleFinal(int n, int d){
		return shiftDecoding(nurseScheduleFinal[n][d]);
	}

    
}