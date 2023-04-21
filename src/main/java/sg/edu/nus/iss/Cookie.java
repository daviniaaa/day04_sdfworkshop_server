package sg.edu.nus.iss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cookie {
    // read data from cookie file and put into list
    List<String> cookies = null;

    public void readCookieFile(String filename) throws IOException {
        // instantiate the cookies collection object
        cookies = new ArrayList<>();

        // use File object to pass the file name
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("The file '" + filename + "' does not exist. Exiting...");
            System.exit(0);
        }

        // use FileReader and BufferedReader to read from cookie file
        // instantiate FileReader followed by a BufferedReader
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        // while loop to loop through the file
        // read each line and add into te cookie collection object
        String line = "";

        while ((line = br.readLine()) != null) {
            cookies.add(line);
        }

        // close the BufferedReader and FileReader in reverse order
        br.close();
        fr.close();

    }

    // read from list and randomly pick one
    public String getRandomCookie() {
        if (cookies == null) {
            return "There are no cookies.";
        } else {
            String fortune = "";
            // fortune = cookies.get((int) Math.round(Math.random() * (cookies.size() - 1) ));
            Random random = new Random();
            fortune = cookies.get(random.nextInt(cookies.size()));
            // get a random number between 0(inclusive) and the number passed in this argument n(exclusive)

            return fortune;
        }
        
    }
}
