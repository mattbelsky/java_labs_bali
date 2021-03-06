package coding_challenges;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class BufferedReaderChallenge {
    public static void main(String[] args) {
        String path = "/home/matthew/Documents/CodingNomads/labs/java_labs_bali/coding_challenges/refugees_per_capita.csv";

        // Reads the CSV file, puts the data into an ArrayList, then sums the population and refugee numbers by year and
        // calculates and displays refugee/population ratio, and measures the run time.
        long startTime = System.currentTimeMillis();

        ArrayList<Refugees> refugeeData = readFile(path);
        sumAndRatioRefugees(refugeeData);

        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;

        System.out.println(timeElapsed + " milliseconds to execution.");
    }

    // Reads the CSV file line by line and puts the data into an ArrayList of Refugee objects, which is returned.
    public static ArrayList<Refugees> readFile(String path) {
        ArrayList<Refugees> refugeeData = new ArrayList();

        try (FileReader fr = new FileReader(path); BufferedReader br = new BufferedReader(fr)) {

            String sCurrentLine;
            sCurrentLine = br.readLine(); // Reads the first line of text so the while loop ignores it.

            while ((sCurrentLine = br.readLine()) != null) {
                String[] data = sCurrentLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Ignores commas between quotes.
                Refugees obj = new Refugees();
                obj.setYear(Integer.parseInt(data[1]));
                obj.setCountry(data[2]);

                // If population is not set
                try {
                    obj.setPopulation(Double.parseDouble(data[3]));
                } catch (NumberFormatException e) {
                    obj.setPopulation(0.0);
                }

                // If number of refugees is not set
                try {
                    obj.setRefugees(Double.parseDouble(data[4]));
                } catch (ArrayIndexOutOfBoundsException e) {
                    obj.setRefugees(0.0);
                }

                // If refugees per capita is not listed
                try {
                    obj.setRefPerCap(Double.parseDouble(data[5]));
                } catch (ArrayIndexOutOfBoundsException e) {
                    obj.setRefPerCap(0.0);
                }

                refugeeData.add(obj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return refugeeData;
    }

    // Sums the recorded population for each year & the refugee numbers over time & displays the ratio of refs/pop.
    public static void sumAndRatioRefugees(ArrayList<Refugees> refugeeData) {
        DecimalFormat newFormat = new DecimalFormat("0.00");

        /*  Runs through each recorded year and the refugeeData objects that correspond to that year and sums and
            displays the total population and refugee numbers for each year.

            Note: it seems easier to just use the years as "magic numbers" since the data is already sorted, method
            calls to get the min and max years would just use more resources, and we're not going to modify the file.

            lastJVal initializes j in the inner loop with 1 + the index of the last refugeeData object whose year = i in
            the latest iteration of the outer loop.

            Doing this rather than iterating through the entire list decreases the run time by a little less than half
            (3-6 ms vs 6-12 ms).
         */
        int lastJVal = 0;
        for (int i = 2001; i <= 2015; i++) {
            double sumPop = 0.0;
            double sumRef = 0.0;
            int year = 0;
            try {
                // Inner for loop will generate exception at the end of its run.
                /*  min is where j is the first value of refugeeData where its year = i
                    max is where year is <= to i
                */
                for (int j = lastJVal; (year = refugeeData.get(j).getYear()) <= i; j++) {
                    if (year == i) {
                        sumPop += refugeeData.get(j).getPopulation();
                        sumRef += refugeeData.get(j).getRefugees();
                    }
                    lastJVal = j + 1;
                }
            } catch (IndexOutOfBoundsException e) {
                // Do nothing.
            }

            // Prints the data for each year.
            System.out.println(i);
            System.out.println("Total population of all countries:\t\t\t" + sumPop);
            System.out.println("Ratio of refugees to global population:\t\t" + sumRef);
            System.out.println("During the recorded period, refugees accounted for " +
                    newFormat.format(sumRef / sumPop * 100) + "% of the global population.\n");
        }
    }
}