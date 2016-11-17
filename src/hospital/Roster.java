
package hospital;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import java.util.Random;
import javax.swing.JOptionPane;


public class Roster {
    
        private final int NURSES = 100;
        private final int SHIFTS = 6;           // Weten we nog niet
        private final int TYPES = 2;
	private final int DAYS = 28; 
	private final int ROSTERS = 100;        // Wat is dit?
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

        
        // Concstructor maken, departement meegeven
        public Roster(char department){
            
        }
        
        public int shiftDecoding (int shiftCode)
	{
		int userShiftID=10;
		for (int s =0; s<numberOfShifts; s++)
		{
			if (shift[s]==shiftCode)
			userShiftID=s;
		}
		if (userShiftID ==10)
    		JOptionPane.showMessageDialog(null,"No correct shiftcode","Error",JOptionPane.WARNING_MESSAGE);
		return userShiftID;
	}
	
	public void readShiftSystem()
	{
		numberOfShifts= db.getNumberOfShifts(department)-1;// -1 opdat de free shift niet meegerekend zou worden
		// Early shift		Code 0
		// Day shift		Code 1
		// Late shift		Code 2
		// Night shift		Code 3
		// Day off			Code 4
		////////////USER
		// Day off 			Code 0
		// StartHour to EndHour Code 1 etc
		
	for (int s =1; s <= numberOfShifts; s++)//vanaf 1 beginnen omdat zo de free shift niet wordt meegerekend
	{
		startShift[s]=db.getArrayShift(s).getStartHour();
		endShift[s]=db.getArrayShift(s).getEndHour();
		////System.out.println("shift: " + s + ": start at : " + startShift[s]);
	}
	int d=0; // dag 1
	for (int s = 1; s<=numberOfShifts;s++)
	{
		if ((startShift[s] >= 3) && (startShift[s] < 9) && (req[d][0] == 0))			// If the shifts start at 3 am or 6 am we define an early shift (and there is no other shift defined as an early shift)
		{	req[d][0]=1;
			shift[s] = 0;
			////System.out.println("usershift: " + s + " that starts at: " + startShift[s]+ " is now java shift: " + shift[s]);
		}	
		else if ((startShift[s] >= 3) && (startShift[s] < 9) && (req[d][0] != 0))
		{	req[d][1]=1;
			shift[s] = 1;
			////System.out.println("usershift: " + s + " that starts at: " + startShift[s]+ " is now java shift: " + shift[s]);
		}				
		if ((startShift[s] >= 9) && (startShift[s] < 12) && (req[d][1] == 0))			// If the shifts start at 9 am we define a day shift (and there is no other shift defined as a day shift)
		{	req[d][1]=1;
			shift[s] = 1;
			////System.out.println("usershift: " + s + " that starts at: " + startShift[s]+ " is now java shift: " + shift[s]);
		}			
		else if ((startShift[s] >= 9) && (startShift[s] < 12) && (req[d][1] != 0))
		{	req[d][2]=1;
			shift[s] = 2;
			////System.out.println("usershift: " + s + " that starts at: " + startShift[s]+ " is now java shift: " + shift[s]);
		}			
		if ((startShift[s] >= 12) && (startShift[s] < 18) && (req[d][2] == 0))		// If the shifts start at 12 am, 3 pm or 6 pm we define a late shift (and there is no other shift defined as a late shift)
		{	req[d][2]=1;
			shift[s] = 2;
			////System.out.println("usershift: " + s + " that starts at: " + startShift[s]+ " is now java shift: " + shift[s]);
		}
		else if ((startShift[s] >= 12) && (startShift[s] < 18) && (req[d][2] != 0))
		{	req[d][3]=1;		
			shift[s] = 3;
			////System.out.println("usershift: " + s + " that starts at: " + startShift[s]+ " is now java shift: " + shift[s]);
		}
		if ((startShift[s] >= 18) && (startShift[s] < 21) && (req[d][3] == 0))		// If the shifts start at 12 am, 3 pm or 6 pm we define a late shift (and there is no other shift defined as a late shift)
		{	req[d][2]=1;
			shift[s] = 3;
			////System.out.println("usershift: " + s + " that starts at: " + startShift[s]+ " is now java shift: " + shift[s]);
		}
		else if ((startShift[s] >= 18) && (startShift[s] < 21) && (req[d][3] != 0))
		{	req[d][3]=1;		
			shift[s] = 4;
			////System.out.println("usershift: " + s + " that starts at: " + startShift[s]+ " is now java shift: " + shift[s]);
		}
		if ((startShift[s] >= 21) || (startShift[s] < 3) && (req[d][4] == 0))			// If the shifts start at 9 pm or 12 pm we define a night shift (and there is no other shift defined as a night shift)
		{	req[d][3]=1;
			shift[s] = 4;
			////System.out.println("usershift: " + s + " that starts at: " + startShift[s]+ " is now java shift: " + shift[s]);
		}
		else if ((startShift[s] >= 21) || (startShift[s] < 3) && (req[d][4] != 0))
			System.out.println("Read problem shifts input");
	}
	shift[0] = numberOfShifts;
	////System.out.println("usershift: " + 0 + " that starts at: " + startShift[0]+ " is now java shift: " + shift[0]);
	// According to the input data, the day off (code 0) is associated with shift (numberOfShifts-1) (the free shift). 

	for (int s = 1; s <= numberOfShifts; s++)	// Determine the length of the shifts
	{	if (startShift[s] < endShift[s])
			{
			hrs[shift[s]] = endShift[s] - startShift[s];
			////System.out.println("length shift " + s + " : "+ hrs[shift[s]]);
			}
		else
			{
			hrs[shift[s]] = (24 - startShift[s]) + endShift[s];
			////System.out.println("length shift " + s + ": " + hrs[shift[s]]);
			}
	}

	hrs[shift[0]] = 0; // The free shift contains no duty time

	for (d = 0; d <DAYS; d++)	// Copy staffing requirements to the other days								// Copy staffing requirements to the other days
	{	for (int s = 1; s <= numberOfShifts; s++)							
		{
			req[d][shift[s]] = db.getArrayShift(s).getReqNumberOfNurses();
			////System.out.println("s = "+s+"shift[s]= "+ shift[s]);
			////System.out.println("staffing req for day " + (d+1) + " and usershift " + s + ": " + req[d][shift[s]] + " number of nurses");
		}
	}
	
	numberOfShifts++; // Increase the number of shifts by one as a day off is also considered as a shift, i.e. the free shift
	//System.out.println("_________________________end readshiftSystem_____________________");
	
	}
	public void readPersonnelCharacteristics()
	{
		////System.out.println("Number of Nurses: " + numberOfNurses);

		for (int n=0;n<numberOfNursesType1;n++)
		{
			nurseEmploymentRate[n]=(double)db.getArrayNurse(n).getEmploymentRate()/100;	
			nurseType[n]=db.getArrayNurse(n).getType();
			////System.out.println("nurseID: " + db.getArrayNurse(n).getNurseID() +" nurseEmploymentRate: " + db.getArrayNurse(n).getEmploymentRate() + " APPLIED that becomes: " + nurseEmploymentRate[n] + " and of type " + nurseType[n]  ); 
		}
		for (int n=numberOfNursesType1;n<numberOfNurses;n++)
		{
			nurseEmploymentRate[n]=(double)db.getArrayNurse(n).getEmploymentRate()/100;	
			nurseType[n]=db.getArrayNurse(n).getType();
			////System.out.println("nurseID: " + db.getArrayNurse(n).getNurseID() +" nurseEmploymentRate: " + db.getArrayNurse(n).getEmploymentRate() + " APPLIED that becomes: " + nurseEmploymentRate[n] + " and of type " + nurseType[n]  ); 
		}
		////System.out.println("_________________________end readPersonnelCharacteristics_____________________");
	}
	public void readCyclicRoster()
	{
		for (int r=0;r<numberOfRostersType1;r++)
		{
			reqFTERosterType1[r]= db.getReqFTERosterType(r, 1);
			for (int d=0;d<DAYS;d++)
			{
				cyclicRostersType1[r][d]=shift[db.getCyclicRosterType(r,d, 1)]; 
				////System.out.println("Type 1, Cyclic roster : " + (r+1)+  " for department " +department+ " on day " + (d+1) 
					//	+ " is " + cyclicRostersType1[r][d] + " and there are " + reqFTERosterType1[r] + " FTE required.") ;
			}
		}
		
		for (int r=(numberOfRostersType1);r<(numberOfRostersType1+numberOfRostersType2);r++) 
		{
			reqFTERosterType2[r]= db.getReqFTERosterType(r, 2);
			for (int d=0;d<DAYS;d++)
			{
				cyclicRostersType2[r][d]=shift[db.getCyclicRosterType(r,d,2)]; 
				////System.out.println("Type 2, Cyclic roster : " + (r+1)+  " for department " +department+ " on day " + (d+1) 
						//+ " is " + cyclicRostersType2[r][d]+ " and there are " + reqFTERosterType2[r] + " FTE required.") ;
			}
		}
		////System.out.println("_________________________end readCyclicRoster_____________________");
		
	}
	public void readMonthlyRosterRules()
	{
		for (int n=0; n < numberOfNursesType1+numberOfNursesType2; n++)
		{
			// Read the min/max number of 
			//assignments over the complete scheduling period
			minAss[n] = db.getArrayNurse(n).getMinNumberOfAssMonth();
			maxAss[n] = db.getArrayNurse(n).getMaxNumberOfAssMonth();
			//individual Employment rates depending of FT or PT nurse
			minAss[n]=(int)Math.round(nurseEmploymentRate[n]*minAss[n]);
			maxAss[n]=(int)Math.round(nurseEmploymentRate[n]*maxAss[n]);
			//Read the min/max number of consecutive work days
			minConsecWork[n] = db.getArrayNurse(n).getMinNumberOfConsecWork();
			maxConsecWork[n] = db.getArrayNurse(n).getMaxNumberOfConsecWork();
			////System.out.println("MONTHlyRosterRules nurse: " + db.getArrayNurse(n).getNurseID() 
				//	+ " min and max ass: " + minAss[n] + "/" + maxAss[n] 
					//+ " min and max consecWork: "  + minConsecWork[n] + "/" + maxConsecWork[n]);
			
			// Read in first the constraints with respect to the working shifts
			for(int s=0;s<numberOfShifts;s++) //om de free shift te includen, s van nul laten beginnen en de 0 als shiftID toevoegen in dB
			{
				minConsecPerShiftType[n][shift[s]]=db.getArrayShift(s).getMinNumberOfConsecDaysPerShiftType();
				maxConsecPerShiftType[n][shift[s]]=db.getArrayShift(s).getMaxNumberOfConsecDaysPerShiftType();
				extremeMaxConsec[n][shift[s]] = 10;
				extremeMinConsec[n][shift[s]] = 10;
				minNumberOfAssPerShiftMonth[n][shift[s]] = db.getArrayShift(s).getMinNumberOfAssPerShiftMonth();
				maxNumberOfAssPerShiftMonth[n][shift[s]] = db.getArrayShift(s).getMaxNumberOfAssPerShiftMonth();
				/*////System.out.println("MonthlyRosterRules shift: " + s + " min and max consec days per shift type: "
						+ minConsecPerShiftType[n][shift[s]] + "/" +maxConsecPerShiftType[n][shift[s]] 
						+" min and max number of ass per shift in a month: " + minNumberOfAssPerShiftMonth[n][shift[s]] 
						+"/" +maxNumberOfAssPerShiftMonth[n][shift[s]] );*/
			}
			
		
		}
		////System.out.println("_________________________end readMonthlyRoster_____________________");
		
	}
	public void readInput()
	{
		readShiftSystem();
		readPersonnelCharacteristics();
		readCyclicRoster();
		readMonthlyRosterRules();
		////System.out.println("_________________________end readInput_____________________");

		
	}
 
}
