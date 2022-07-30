import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author Cagatay KARASU
 */

public class IOApp {

    static String filename="transactions.txt";
    static int limitRecord=100;
    static Record[] records;
    static int lastIndex;

    static class Record
    {
        String name;
        Integer price, number;
    }

    // -----------------------------------------------------------------------------------------------//

    public static void main(String[] args) throws IOException {

        initialProcess(); // Do not change

        choiceMenu();
    }

    // -----------------------------------------------------------------------------------------------//

    private static void choiceMenu() throws IOException {

        File file = new File(filename);

//        if (file.exists()) {
//            System.out.println("File already exists");
//            System.exit(0);
//        }

        Scanner scn = new Scanner(System.in);
        int choice = 0;

        while (choice != 6) {

            System.out.print("------------MENU------------\n" +
                    "------------1-ADD RECORD------------\n" +
                    "------------2-REMOVE RECORD------------\n" +
                    "------------3-SEARCH RECORD------------\n" +
                    "------------4-LIST ALL------------\n" +
                    "------------5-DELETE ALL------------\n" +
                    "------------6-CLOSE------------\n" +
                    "Please make a choice : ");
            choice = scn.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Please enter Name : ");
                    String name = scn.next();
                    name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();

                    System.out.print("Please enter Price : ");
                    int price = scn.nextInt();

                    System.out.print("Please enter Number : ");
                    int number = scn.nextInt();

                    addRecord(name, price, number);
                    update();
                    System.out.println("Record is added");

                    break;

                case 2:

                    name = "";
                    if (removeRecord(name)) {
                        lastIndex--;
                        update();
                        System.out.println("Record is removed");
                    }
                    else
                        System.out.println("Record is not available!");
                    break;

                case 3:

                    searchRecord("");
                    break;

                case 4:

                    listRecord();
                    break;

                case 5:

                    System.out.println("Are you sure? Y/N");
                    String makeSure = scn.next();
                    makeSure = makeSure.substring(0,1).toUpperCase();
                    if (makeSure.equals("Y")) {
                        deleteall();
                        update();
                    }
                    else
                        System.out.println("Invalid answer!");

                    break;

                case 6:
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // -----------------------------------------------------------------------------------------------//

    private static void listRecord() {

        if (lastIndex != 0) {
            for   (int i = 0; i<lastIndex ; i++) {
                System.out.println(records[i].name + " " + records[i].number + " " + records[i].price);
            }
        }
        else
            System.out.println("List is empty!");
    }

    // -----------------------------------------------------------------------------------------------//

    private static void addRecord(String name, Integer price, Integer number) {

            Record record = new Record();
            record.name = name;
            record.price = price;
            record.number = number;
            records[lastIndex] = record;
            lastIndex++;
    }

    // -----------------------------------------------------------------------------------------------//

    private static void update() throws IOException {

        FileWriter fileWriter = new FileWriter(filename);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        for (int i = 0; i < lastIndex; i++) {

            bufferedWriter.write(records[i].name + "\t" + records[i].price + "\t" + records[i].number +"\n");
        }
        bufferedWriter.close();
    }

    // -----------------------------------------------------------------------------------------------//

    private static boolean removeRecord(String name) {

        boolean status = false;

        Scanner scn = new Scanner(System.in);
        System.out.print("Please enter Name : ");
        name = scn.nextLine();
        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();

        for (int i = 0; i < lastIndex; i++) {
            if (records[i].name.equals(name)) {
                records[i] = new Record();
                int j;
                for (j = i; j < lastIndex-i; j++) {
                    records[j] = records[j+1];
                }
                records[j] = new Record();

                status = true;
                break;
            }
        }
        return status;
    }

    // -----------------------------------------------------------------------------------------------//

    private static void searchRecord(String name) {

        Scanner scn = new Scanner(System.in);
        System.out.print("Please enter Name : ");
        name = scn.nextLine();
        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();

        boolean status = false;

        int i;
        for (i = 0; i < lastIndex; i++) {
            if (records[i].name.equals(name)) {
                status = true;
                break;
            }
        }

        if (status) {
            System.out.println(records[i].name + "\t" + records[i].price + "\t" + records[i].number);
        }
        else
            System.out.println(name + " is not found!");
    }

    // -----------------------------------------------------------------------------------------------//

    private static void deleteall() {

     //   FileWriter fileWriter = new FileWriter(filename);
     //   fileWriter.write("");

        lastIndex = 0;
    }

    // -----------------------------------------------------------------------------------------------//

    // initialProcess() method must not be changed.
    private static void initialProcess() {

        records=new Record[limitRecord];

        for (int i=0;i<limitRecord;i++) {
            records[i]=new Record();
        }

        try {

            Reader reader=new InputStreamReader(new FileInputStream(filename),"Windows-1254");

            BufferedReader br=new BufferedReader(reader);

            String strLine;

            int i=0;

            while((strLine=br.readLine())!=null) {

                StringTokenizer tokens=new StringTokenizer(strLine,"\t");
                String [] t=new String[3];
                int j=0;

                while (tokens.hasMoreTokens()) {

                    t[j]=tokens.nextToken();
                    j++;
                }

                records[i].name=t[0];
                records[i].price=Integer.valueOf(t[1]);
                records[i].number=Integer.valueOf(t[2]);
                i++;
            }

            lastIndex=i;
            reader.close();
        }

        catch (Exception e) {

            System.err.println("Error: "+e.getMessage());
        }
    }
}
