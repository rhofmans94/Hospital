
package hospital;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import java.util.Random;
import javax.swing.JOptionPane;

import hospital.reader;


public class Roster {
    
        private final int NURSES = 100;
        private final int SHIFTS = 6;           // Weten we nog niet
        private final int TYPES = 2;
	private final int DAYS = 28; 
	private final int ROSTERS = 100;        // Individueel rooster voor elke nurse
	private final int MAXCONSECUTIVEDAYS = 5; 
        
        private char department; // a,b,c,d
        
        //private int kappa=0;   Wat is dit?
	
	int k, l, m, p, q, hh, kk, h1, h2;
	
	private int numberOfNurses;
	private int numberOfNursesType1;
	private int numberOfNursesType2;
	private int numberOfShifts;
	private int numberOfRostersType1;
	private int numberOfRostersType2;
        private int lengthOfShift;
	
	private int violations; 
	
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
	private int [][] nurseScheduleFinal=new int [numberOfNurses][DAYS];
	int [] nurseDoesFinal;
	//private String PTHTDetails;
	private int [] nursesR2Final;
	
	//procedureBA
	ArrayList<Integer> type1NurseAssignedToType2Roster;
	ArrayList<Integer> assignedNurses;
	HashSet<Integer> sharedRoster;
	private int [][] nurseSchedule=new int [numberOfNurses][DAYS];
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
	private int [][] nurseScheduleBin=new int [numberOfNurses][DAYS];
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
        //private int [] violations = new int[DAYS * SHIFTS];
	private String textConstraints; //will summarize the constraints
	
	//evaluateSolution variables
	private int a;//number of nurses scheduled on a particular day and shift
	//reArrange variables
	private boolean [][] alreadySwitched=new boolean[NURSES][DAYS]; 
	//cost
	private double costType1;
	private double costType2;
	private double costTotal;
	
/*
	cyclicRostersType1 = new int [numberOfRostersType1][DAYS];
	cyclicRostersType2 = new int [numberOfRostersType1+numberOfRostersType2][DAYS];
	reqFTERosterType1 = new int[numberOfRostersType1];
	reqFTERosterType2 = new int [numberOfRostersType1+numberOfRostersType2];
	nurseSchedule = new int [numberOfNurses][DAYS];
	nurseScheduleBin = new int [numberOfNurses][DAYS];
	nurseScheduleFinal = new int [numberOfNurses][DAYS];
*/
		
	
        
        // Concstructor maken, departement meegeven
        public Roster(){
            
        }
        
        public Roster(char department){
            this.department = department;
        }
        
        public void shiftDecoding (int shiftCode)
	{
		for (int s =0; s<numberOfShifts; s++)
		{
			if (shift[s]==shiftCode);
                        break;
		}
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

	hrs[shift[0]] = 0; // The free shift contains no duty time

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
			//reqFTERosterType1[s]= db.getReqFTERosterType(s, 1);
			for (int d=0;d<DAYS;d++)
			{
                                int [][] cyclR1 = r.getCyclicRostersType1();
				cyclicRostersType1[s][d]=shift[cyclR1[s][d]]; 
				System.out.println("Type 1, Cyclic roster : " + (s+1)+  " for department " +department+ " on day " + (s+1) 
						+ " is " + cyclicRostersType1[s][d] + " and there are " + "reqFTERosterType1[r]" + " FTE required.") ;
			}
		}
		
		for (int s=(numberOfRostersType1);s<(numberOfRostersType1+numberOfRostersType2);s++) 
		{
			//reqFTERosterType2[s]= db.getReqFTERosterType(s, 2);
			for (int d=0;d<DAYS;d++)
			{
                                int [][] cyclR2 = r.getCyclicRostersType2();
				cyclicRostersType2[s][d]=shift[cyclR2[s][d]]; 
				System.out.println("Type 2, Cyclic roster : " + (s+1)+  " for department " +department+ " on day " + (d+1) 
						+ " is " + cyclicRostersType2[s][d]+ " and there are " + "reqFTERosterType2[r]" + " FTE required.") ;
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
				////System.out.println("AFTER HORIZONTAM");
				//evaluateSolution();
				verticalSwapping();
				////System.out.println("AFTER VERTICLA");
				//evaluateSolution();
				rearrangeSurplusRandom();
				////System.out.println("AFTER REAARRANE");
				//evaluateSolution();
				avoidIslands();
				////System.out.println("AFTER ISLANDS");
				count2++;
			}
			violationss=0;
			for (int n=0; n<numberOfNurses;n++){
 				for (int d=0;d<DAYS;d++){
 					violationss+= db.getNursePreferencesTotal(n, d, shiftDecoding(nurseSchedule[n][d]));
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
					//System.out.println("!!!!!!!!!!!!!!!!!!!!MIN SO FAR "  + minimum + "!!!!!!!!!!!!!!!!!!!!!!\n");
	 			}
		count1++;
		}
		//System.out.println("xxxx xxxx xxxx finalSolution xxxx xxxx xxxx");
		printOutputFinal();
		evaluateSolutionFinal();
		//excelResult(); 
		//System.out.println("EXCEL ---> GA GAAN ZIEN IN UW WORKSPACE VOOR DE OUTPUT!!!");
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
			while(assignedNursesBin.contains(n1)){
				n1=new Random().nextInt(high-low)+low;
				System.out.println("New n1 " + n1);
			}
			PTHTDetailsBin+=("\nn1: " +  n1 + " has ER: " + nurseEmploymentRate[n1] *100 +"%" );
			assignedNursesBin.add(n1);
			nurseDoesBin[n1]=r;
			for(int d=0; d<DAYS;d++){
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
						////System.out.println("dubbel roster " + (r+1) + " wordt gevolgd door nurse " + db.getArrayNurse(n).getNurseID());
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
		for (int n=0; n<numberOfNurses;n++){////System.out.println("nurse " + db.getArrayNurse(n).getNurseID() + ", Roster " + (nurseDoesBin[n]+1)) ;
			for (int d=0;d<DAYS;d++){
				////System.out.println("Day " +(d+1)+ " Usershift " + shiftDecoding(nurseScheduleBin[n][d]) +  " penalty: " + db.getNursePreferencesTotal(n, d, shiftDecoding(nurseScheduleBin[n][d])) );
				violations+= db.getNursePreferencesTotal(n, d, shiftDecoding(nurseScheduleBin[n][d]));
			}
		}
		return violations;
	}

    
}