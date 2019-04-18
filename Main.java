import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
class Main{
    enum opcodes{
        R_FORMAT, I_FORMAT, J_FORMAT;
    }
    public static void main(String[] args)throws IOException, FileNotFoundException{
        Scanner in = new Scanner(System.in);
        HashMap<String, String> t1 = setUp(in, "mainOpcodes.txt");
        HashMap<String, String> t2 = setUp(in, "rFormatOpcodes.txt");
        HashMap<String, String> t3 = setUp(in, "bltzgezOpcodes.txt");
        HashMap<String, String> t4 = setUp(in, "tlbOpcodes.txt");
        in = new Scanner(System.in);
        System.out.println("Paste the lines you wish to convert (32-bit please):");
        while(in.hasNextLine()){
            String line = in.nextLine();
            System.out.println(getOpcode(line));
        }


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

    public static opcodes getOpcode(String l){
        l = l.substring(0,6);
        int ll = Integer.parseInt(l, 2);
        if(ll==0)
            return opcodes.R_FORMAT;
        else if(ll==2 || ll==3)
            return opcodes.J_FORMAT;
        return opcodes.I_FORMAT;
    }
}