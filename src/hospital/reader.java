
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

    
    public static void main(String[] args) {
                
        CSVReader reader;
        try {
            reader = new CSVReader(new FileReader("C:\\Users\\Ruth Hofmans\\Desktop\\input example\\C input.csv"));
            String[][] lines= new String[20][20];
            String [] nextLine;
        int lineNumber = 0;
               
        while ((nextLine = reader.readNext()) != null) {
            
            String[] cells = nextLine[0].split(";");
            lines[lineNumber] = cells;
            
             lineNumber ++; 
            
           }
        
            int length = Integer.parseInt(lines[1][1]);
            System.out.print("length: "+length + "\n");
            int shifts = Integer.parseInt(lines[1][0]);
            System.out.print("shifts: "+shifts + "\n");
            
            int [] startShift = new int [shifts];
            System.arraycopy(lines,0, startShift, 0, shifts);
            System.out.println("startshift: "+startShift);
                    
            int [] startShiftA = {Integer.parseInt(lines[4][0]), Integer.parseInt(lines[5][0]),Integer.parseInt(lines[6][0])};
            int [] startShiftB  = {Integer.parseInt(lines[4][1]), Integer.parseInt(lines[5][1]),Integer.parseInt(lines[6][1])};
            int [] startShiftC  = {Integer.parseInt(lines[4][2]), Integer.parseInt(lines[5][2]),Integer.parseInt(lines[6][2])};
            int [] startShiftD  = {Integer.parseInt(lines[4][3]), Integer.parseInt(lines[5][3]),Integer.parseInt(lines[6][3])};
            
            int [] reqA  = {Integer.parseInt(lines[9][0]), Integer.parseInt(lines[10][0]),Integer.parseInt(lines[11][0])};
            int [] reqB  = {Integer.parseInt(lines[9][1]), Integer.parseInt(lines[10][1]),Integer.parseInt(lines[11][1])};
            int [] reqC  = {Integer.parseInt(lines[9][2]), Integer.parseInt(lines[10][2]),Integer.parseInt(lines[11][2])};
            int [] reqD  = {Integer.parseInt(lines[9][3]), Integer.parseInt(lines[10][3]),Integer.parseInt(lines[11][3])};
            
            int budgetA = Integer.parseInt(lines[14][0]);
            int budgetB = Integer.parseInt(lines[14][1]);
            int budgetC = Integer.parseInt(lines[14][2]);
            int budgetD = Integer.parseInt(lines[14][3]);
            
            System.out.print(startShiftA + ""+ startShiftB +""+ startShiftC +""+ startShiftD + "\n");
            System.out.print(reqA +""+ reqB +""+ reqC+""+ reqD+"\n");
            System.out.print(budgetA +""+budgetB+""+budgetC+""+budgetD+"\n");
            
               
     }
         catch (IOException e) {
			e.printStackTrace();
        }
     
    }
}
    