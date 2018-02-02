/**
 * Purpose: The purpose of the program is to display hurricane data from a text file, find averages, and find maxs/mins according to the user's range of years.
 *
 * @author Briana Berger
 * @version 12/25/2017
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.Random;
import java.util.Scanner;
public class HurricaneSelector
{
    public static void main(String[] args) throws IOException
    {
        String [] names = new String [156];
        String [] months = new String [156];
        int [] years = new int [156];
        int [] pressures = new int [156];
        double [] windSpeeds = new double [156];
        int [] category = new int [156];
        int sumCategories = 0;
        int sumPressures = 0;
        double sumWindSpeeds = 0;
        int sumCat1 = 0;
        int sumCat2 = 0;
        int sumCat3 = 0;
        int sumCat4 = 0;
        int sumCat5 = 0;
        int minCat = Integer.MAX_VALUE;
        int maxPres = Integer.MIN_VALUE;
        int minPres = Integer.MAX_VALUE;
        int maxCat = Integer.MIN_VALUE;
        double minWindSpeeds = Double.MAX_VALUE;
        double maxWindSpeeds = Double.MIN_VALUE;
        double numberOfHurricanes = 0;
        int i = 0;
        
        File fileInName = new File("HurricaneData.txt");
        Scanner inFile = new Scanner(fileInName);
        
        File fileOutName = new File("SummaryOfCategories.txt");
        PrintWriter outFile  = new PrintWriter(fileOutName);
        
        Scanner in = new Scanner(System.in);
        
        //Parses data from HurricaneData.txt into their respective arrays.
        while (inFile.hasNext())
        {
            years[i] = inFile.nextInt();
            months[i] = inFile.next();
            pressures[i] = inFile.nextInt();
            windSpeeds[i] = inFile.nextInt();
            names[i] = inFile.next();
            i++;
        }   
        
        //Converts all values in the windSpeeds array from knots to mph.
        for(int j = 0; j < windSpeeds.length; j++)
        {
            double windSpeedMPH = Math.round(((double)(windSpeeds[j] * 1.152)) * 10.0) /10.0;
            windSpeeds[j] = windSpeedMPH;
            
            //Uses the Saphir-Simpson Wind Speed Scale to determine the category of each storm and stores the data in an array.
            //Category 1
            if(windSpeeds[j] >= 74 && windSpeeds[j] <= 95)
            {
                category[j] = 1;
            }
            
            //Category 2
            else if(windSpeeds[j] >= 96 && windSpeeds[j] <= 110)
            {
                category[j] = 2;
            }
            
            //Category 3
            else if(windSpeeds[j] >= 111 && windSpeeds[j] <= 129)
            {
                category[j] = 3;
            }
            
            //Category 4
            else if(windSpeeds[j] >= 130 && windSpeeds[j] <= 156)
            {
                category[j] = 4;
            }
            
            //Category 5
            else if(windSpeeds[j] >= 157)
            {
                category[j] = 5;
            }
            
            //Uncategorized
            else
            {
                category[j] = 0;
            }
        }
        
        System.out.print("Start Year for Hurricane Data(ex: 2003): ");
        int bottomYear = in.nextInt();
        if (bottomYear < 1995 && bottomYear > 2015)
        {
            System.out.println("The dataset has no information for the indicated start year. Please try again with a year on or between 1995 and 2015.");
            return;
        }
        
        System.out.print("End Year for Hurricane Data(ex: 2005): ");
        int upperYear = in.nextInt();
        
        //Ensures that the year ranges are logically sound.
        if (upperYear < 1995 || upperYear > 2015)
        {
            System.out.println("The dataset has no information for the indicated end year. Please try again with a year on or between 1995 and 2015.");
            return;
        }
        else if (bottomYear < 1995 || bottomYear > 2015)
        {
            System.out.println("The dataset has no information for the indicated start year. Please try again with a year on or between 1995 and 2015.");
            return;
        }
        else if(bottomYear <= upperYear)
        {
            System.out.println();
            System.out.printf("%50s %n","Hurricanes " + bottomYear + " - " + upperYear);
            System.out.printf("%6s %16s %16s %20s %20s %n", "Year", "Hurricane", "Category", "Pressure (mb)", "Wind Speed (mph)");
            System.out.println("=======================================================================================");
            
            int pos = 0;
            int bottomYearIndexPos = 0;
            int upperYearIndexPos = 0;
            
            //Find index position of bottom year aka start year.
            while (years[pos] < bottomYear)
            {
                bottomYearIndexPos = pos;
                pos++;
            }
            bottomYearIndexPos = bottomYearIndexPos + 1;
            
            //Find index position of upper year aka end year.
            while(years[pos] <= upperYear)
            {
                upperYearIndexPos = pos;
                pos++;
            }
            
            //For loop with int pos = bottom year index position. Condition: bottom year index position != upper year index position. pos++
            for (pos = bottomYearIndexPos; pos < upperYearIndexPos; pos++)
            {
                //Print out the info according to pos.
                System.out.printf("%6s %16s %12s %16s %20s %n", years[pos], names[pos], category[pos], pressures[pos], windSpeeds[pos]);
                numberOfHurricanes++;
                //Find sum of categories, sum of pressures, sum of wind speeds.
                sumCategories += category[pos];
                sumPressures += pressures[pos];
                sumWindSpeeds += windSpeeds[pos];
                
                //Use if and else if statements to sum up the number of cateogry 1, 2, 3, 4, 5s.
                if(category[pos] == 1)
                {
                    sumCat1 ++;
                }
                else if(category[pos] == 2)
                {
                    sumCat2 ++;
                }
                else if(category[pos] == 3)
                {
                    sumCat3 ++;
                }
                else if(category[pos] == 4)
                {
                    sumCat4 ++;
                }
                else if(category[pos] == 5)
                {
                    sumCat5 ++;
                }
                
                //Min and Max Code for Categories
                if(category[pos] < minCat)
                {
                    minCat = category[pos];
                }
                else if(category[pos] > maxCat)
                {
                    maxCat = category[pos];
                }
                
                //Min and Max Code for Pressures
                if(pressures[pos] < minPres)
                {
                    minPres = pressures[pos];
                }
                else if(pressures[pos] > maxPres)
                {
                    maxPres = pressures[pos];
                }
                
                //Min and Max Code for Wind Speeds
                if(windSpeeds[pos] < minWindSpeeds)
                {
                    minWindSpeeds = windSpeeds[pos];
                }
                else if(windSpeeds[pos] > maxWindSpeeds)
                {
                    maxWindSpeeds = windSpeeds[pos];
                }
            }
        }
        else
        {
            System.out.println("You cannot have an end year lower than the start year. Please try again.");
            return;
        }
        System.out.println("=======================================================================================");
        
        //Find average and print it out for category, P, WS
        double averageCat = Math.round(((double)(sumCategories/numberOfHurricanes)) * 10.0) / 10.0;
        double averagePres = Math.round(((double)(sumPressures/numberOfHurricanes)) * 10.0) / 10.0;
        double averageWS = Math.round(((double)(sumWindSpeeds/numberOfHurricanes)) * 10.0) / 10.0;
        System.out.printf("%20s %17s %16s %19s %n", "Average:", averageCat, averagePres, averageWS);
        
        //Print out minimums for category, p, ws
        System.out.printf("%20s %15s %16s %20s %n", "Minimum:", minCat, minPres, minWindSpeeds);
        
        //Print out max for cat, p, ws.
        System.out.printf("%20s %15s %17s %20s %n", "Maximum:", maxCat, maxPres, maxWindSpeeds);
        

        //print out summary of categories aka the sum of each cat to outFile.
        outFile.println();
        outFile.println("Summary of Categories");
        outFile.printf("%10s %2s %n","Cat 1:", sumCat1);
        outFile.printf("%10s %2s %n","Cat 2:", sumCat2);
        outFile.printf("%10s %2s %n","Cat 3:", sumCat3);
        outFile.printf("%10s %2s %n","Cat 4:", sumCat4);
        outFile.printf("%10s %2s %n","Cat 5:", sumCat5);
        outFile.close();
    }
}
