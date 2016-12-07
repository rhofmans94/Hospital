/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            //writer = new CSVWriter(new FileWriter("C:\\Users\\julie.MATTIS\\OneDrive\\Documenten\\AOR\\test.csv"));
            writer = new CSVWriter(new FileWriter("C:\\Users\\Ruth Hofmans\\Desktop\\input example\\test"+department+".csv"));
            String[][][] lines= new String[100][100][100];
            //int lineNumber = 3;
            
            for (int n=0; n<5;n++)
            {
                lines[n][0][0] = "ID"+n;
                for (int d=0; d<5;d++)
                {
                    for(int s=0;s<5;s++)
                    {
                    int dag = d+1;
                    lines[n][dag][s]=Integer.toString(s); //ophalen shift van die nurse!
                    }
                  
                }
               
            }
            
        //System.out.println(lines[0][0]+ " "+lines[0][1]+" "+lines[0][2]);
        //System.out.println(lines[1][0]+ " "+lines[1][1]+" "+lines[1][2]);
        //System.out.println(lines[2][0]+ " "+lines[2][1]+" "+lines[2][2]);
            
         
         List<String[]> data = new ArrayList<String[]>();
         for (int n=0;n<5;n++)
         {
          String total = new String();   
             for (int d=0;d<=5;d++)
             {
                 for (int s=0;s<5;s++)
                 {
                 total += lines[n][d][s]+";";
                 }
                 
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
    }

//opencsv.sourceforge.net
    

