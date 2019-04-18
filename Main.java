import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
class Main{
    public static void main(String[] args)throws IOException, FileNotFoundException{
        Scanner in = new Scanner(System.in);
        HashMap<String, String> t1 = setUp(in, "mainOpcodes.txt");
        HashMap<String, String> t2 = setUp(in, "rFormatOpcodes.txt");
        HashMap<String, String> t3 = setUp(in, "bltzgezOpcodes.txt");
        HashMap<String, String> t4 = setUp(in, "tlbOpcodes.txt");
        in = new Scanner(System.in);
        


        in.close();
    }

    public static HashMap<String, String> setUp(Scanner in, String fileName) throws FileNotFoundException{
        in = new Scanner(new File(fileName));
        HashMap<String, String> t = new HashMap<>();
        while(in.hasNextLine()){
            String[] l = in.nextLine().split(" ");
            t.put(l[0], l[1]);
        }
        in.close();
        return t;
    }
}