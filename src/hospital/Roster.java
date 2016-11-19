
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
	private int[][] cyclicRostersType1;
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
		System.out.println("Number of Nurses: " + numberOfNurses);
                System.out.println("Number of Nurses type 1: " + numberOfNursesType1);
                System.out.println("Number of Nurses type 2: " + numberOfNursesType2);

		for (int n=0;n<numberOfNurses;n++)
		{			
                   double [] rate = r.getEmploymentRate();
                   int [] type = r.getType();
                   String [] ID = r.getID();
                   nurseEmploymentRate[n] = (rate [n]/100);
                   nurseType[n]=type[n];
                   nurseID[n]=ID[n];
                   
		System.out.println("nurseID: " + nurseID[n] +" nurseEmploymentRate: " + (rate[n]) + "% APPLIED that becomes: " + nurseEmploymentRate[n] + " and of type " + nurseType[n]  ); 
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



    
}