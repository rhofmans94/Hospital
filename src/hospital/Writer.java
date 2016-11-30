/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Ruth Hofmans
 */
public class Writer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        char department = 'A';
        
                
        CSVWriter writer;
        try{
            //reader = new CSVReader(new FileReader("C:\\Users\\julie.MATTIS\\OneDrive\\Documenten\\AOR\\test.csv"));
            writer = new CSVWriter(new FileWriter("C:\\Users\\Ruth Hofmans\\Desktop\\input example\\test.csv"));
            String[][] lines= new String[100][100];
            int lineNumber = 3;
            
            for (int n=0; n<5;n++)
            {
                lines[n+3][0] = "ID"+n;
                for (int d=0; d<5;d++)
                {
                    lines[3+n][d+1]=Integer.toString(d+1);
                }
            }
            
            System.out.println(lines[4][0]+ " "+lines[4][1]);
                
       while (lineNumber < 8) {
            
            String[] write = lines[lineNumber];
            String[] entries = "a,b,c".split(",");
            System.out.println(write[0]);
            writer.writeNext(entries);
            
             lineNumber ++; 
            
           }     
        
            
            
               
     }
         catch (IOException e) {
			e.printStackTrace();
        }
     
         
} 
    }

//opencsv.sourceforge.net
    

