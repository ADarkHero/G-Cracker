package gcracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author ADarkHero
 */
public class GCracker {

    public static String pass = "";
    public static boolean hashNotFound = true;
    public static String tryPass = "";
    public static String tryHash = "";
    public static int charCount = 1;
    //Tons of counters
    private static int[] i = new int[16];
    public static long stepcount = 0;
    //List of possible chars
    public static char[] charList = {' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'ä', 'Ä', 'ö', 'Ö', 'ü', 'Ü', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'ß', '!', '"', '§', '$', '%', '&', '/', '(', ')', '=', '?',
        '#', '+', '*', '-', '_', '.', ',', ':', ';', '<', '>'};
    //DateXTime
    public static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    public static Date date = new Date();
    //Writers
    public static PrintWriter pWriter = null;
    public static PrintWriter sWriter = null;
    public static PrintWriter stWriter = null;
    public static PrintWriter hWriter = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        for (int x = 0; x < i.length; x++) {
            i[x] = 0;
        }
        i[0] = 1;                                               //Saves 1 Hashtry

        Scanner scan = new Scanner(System.in);
        System.out.println("Which hash is searched? (y to load last hash OR input your own hash)");
        String hash = scan.nextLine();
        if (hash.equals("y")) {
            FileReader fr = new FileReader("hash.txt");
            BufferedReader br = new BufferedReader(fr);

            hash = br.readLine();
            System.out.println("Loaded Hash: " + hash);
        } else if (hash.length() == 32) {                           //Failsave
            hWriter = new PrintWriter(new FileWriter("hash.txt"), true);
            hWriter.println(hash);
        }
        System.out.println("Load Savegame? (y to load last save OR input your own save)");
        String save = scan.nextLine();


        if (save.length() > 16 || save.equals("y")) {          //Insert own Save
            if (save.equals("y")) {                             //Read Save from data
                FileReader fire = new FileReader("save.txt");
                BufferedReader bure = new BufferedReader(fire);

                save = bure.readLine();
                System.out.println("Loaded Save: " + save);
                
                FileReader filre = new FileReader("steps.txt");
                BufferedReader bufre = new BufferedReader(filre);
                stepcount = Long.parseLong(bufre.readLine());
                System.out.println("Current Steps: " + stepcount);
            }
            String[] saveSplit = save.split(" ");              //Split Savegame

            for (int x = 0; x < saveSplit.length; x++) {
                i[x] = Integer.parseInt(saveSplit[x]);
            }
        } 

        System.out.println("");
        System.out.println("Startime: " + dateFormat.format(date));
        System.out.println("Please wait...");
        System.out.println("");

        String crackedHash = crack(hash);

        System.out.println(crackedHash);

        //Write pass in file
        pWriter = new PrintWriter(new FileWriter("pass.txt"), true);
        pWriter.println(crack(hash));
        //Writes number of steps in file
        stWriter = new PrintWriter(new FileWriter("steps.txt"), true);
        stWriter.println(stepcount);
        //Writes save in file
        String currentSave = "";
        for (int x = 0; x < i.length; x++) {
            currentSave += i[x] + " ";
        }
        sWriter = new PrintWriter(new FileWriter("save.txt"), true);
        sWriter.println(currentSave);
        System.out.println("Steps taken: " + stepcount);
    }

    private static String crack(String hash) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        while (hashNotFound) {
            tryPass = newPass();
            md.update(tryPass.getBytes());          //Let's Hash!
            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            tryHash = sb.toString();

            if (tryHash.equals(hash)) {
                hashNotFound = false;
                pass = tryPass;
            }
        }

        return pass;
    }

    private static String newPass() throws IOException {
        String pass = "";

        int charCount = charList.length;

        pass = "";

        for (int x = 0; x < i.length; x++) {
            pass += charList[i[x]];
        }

        i[0]++;

        //BESCHD CRAP CODE EU
        if (i[0] >= charCount) {
            i[0] = 0;
            i[1]++;
        }
        if (i[1] >= charCount) {
            i[0] = 0;
            i[1] = 0;
            i[2]++;
        }
        if (i[2] >= charCount) {
            i[0] = 0;
            i[1] = 0;
            i[2] = 0;
            i[3]++;
        }
        if (i[3] >= charCount) {
            i[0] = 0;
            i[1] = 0;
            i[2] = 0;
            i[3] = 0;
            i[4]++;
        }
        if (i[4] >= charCount) {
            i[0] = 0;
            i[1] = 0;
            i[2] = 0;
            i[3] = 0;
            i[4] = 0;
            i[5]++;
        }
        if (i[5] >= charCount) {
            i[0] = 0;
            i[1] = 0;
            i[2] = 0;
            i[3] = 0;
            i[4] = 0;
            i[5] = 0;
            i[6]++;
        }
        if (i[6] >= charCount) {
            i[0] = 0;
            i[1] = 0;
            i[2] = 0;
            i[3] = 0;
            i[4] = 0;
            i[5] = 0;
            i[6] = 0;
            i[7]++;
        }
        if (i[7] >= charCount) {
            i[0] = 0;
            i[1] = 0;
            i[2] = 0;
            i[3] = 0;
            i[4] = 0;
            i[5] = 0;
            i[6] = 0;
            i[7] = 0;
            i[8]++;
        }
        if (i[8] >= charCount) {
            i[0] = 0;
            i[1] = 0;
            i[2] = 0;
            i[3] = 0;
            i[4] = 0;
            i[5] = 0;
            i[6] = 0;
            i[7] = 0;
            i[8] = 0;
            i[9]++;
        }
        if (i[9] >= charCount) {
            i[0] = 0;
            i[1] = 0;
            i[2] = 0;
            i[3] = 0;
            i[4] = 0;
            i[5] = 0;
            i[6] = 0;
            i[7] = 0;
            i[8] = 0;
            i[9] = 0;
            i[10]++;
        }
        if (i[10] >= charCount) {
            i[0] = 0;
            i[1] = 0;
            i[2] = 0;
            i[3] = 0;
            i[4] = 0;
            i[5] = 0;
            i[6] = 0;
            i[7] = 0;
            i[8] = 0;
            i[9] = 0;
            i[10] = 0;
            i[11]++;
        }
        if (i[11] >= charCount) {
            i[0] = 0;
            i[1] = 0;
            i[2] = 0;
            i[3] = 0;
            i[4] = 0;
            i[5] = 0;
            i[6] = 0;
            i[7] = 0;
            i[8] = 0;
            i[9] = 0;
            i[10] = 0;
            i[11] = 0;
            i[12]++;
        }
        if (i[12] >= charCount) {
            i[0] = 0;
            i[1] = 0;
            i[2] = 0;
            i[3] = 0;
            i[4] = 0;
            i[5] = 0;
            i[6] = 0;
            i[7] = 0;
            i[8] = 0;
            i[9] = 0;
            i[10] = 0;
            i[11] = 0;
            i[12] = 0;
            i[13]++;
        }
        if (i[13] >= charCount) {
            i[0] = 0;
            i[1] = 0;
            i[2] = 0;
            i[3] = 0;
            i[4] = 0;
            i[5] = 0;
            i[6] = 0;
            i[7] = 0;
            i[8] = 0;
            i[9] = 0;
            i[10] = 0;
            i[11] = 0;
            i[12] = 0;
            i[13] = 0;
            i[14]++;
        }
        if (i[14] >= charCount) {
            i[0] = 0;
            i[1] = 0;
            i[2] = 0;
            i[3] = 0;
            i[4] = 0;
            i[5] = 0;
            i[6] = 0;
            i[7] = 0;
            i[8] = 0;
            i[9] = 0;
            i[10] = 0;
            i[11] = 0;
            i[12] = 0;
            i[13] = 0;
            i[14] = 0;
            i[15]++;
        }

        pass = pass.trim();             //Kill tha spaces!

        stepcount++;

        if (stepcount % 50000000 == 0) {
            String currentSave = "";
            date = new Date();
            double stepoutput = (double) stepcount / 1000000000;
            System.out.println(dateFormat.format(date) + " Current Savegame: (" + stepoutput + "mrd Hashes tried)");
            for (int x = 0; x < i.length; x++) {
                currentSave += i[x] + " ";
            }
            System.out.println(currentSave);                //Print Save
            sWriter = new PrintWriter(new FileWriter("save.txt"), true); //Printer for Savegames
            sWriter.println(currentSave);                   //Write Save to data
            stWriter = new PrintWriter(new FileWriter("steps.txt"), true); //Printer for Savegames
            stWriter.println(stepcount);                   //Write Stepcount to data

            System.out.println("");
        }

        return pass;
    }
}
