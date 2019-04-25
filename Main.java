import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

class Main {
  enum opcodes {
    R_FORMAT, I_FORMAT, J_FORMAT;
  }

  public static void main(String[] args) throws IOException, FileNotFoundException {
    Scanner in = new Scanner(System.in);
    HashMap<String, String> t1 = setUp(in, "mainOpcodes.txt");
    HashMap<String, String> t2 = setUp(in, "rFormatOpcodes.txt");
    HashMap<String, String> t3 = setUp(in, "bltzgezOpcodes.txt");
    HashMap<String, String> registers = setUp(in, "registers.txt");
    in = new Scanner(System.in);
    boolean isHex = true;
    System.out.println("Would you like to type in the lines in binary? (default is Hex)");
    String ans = in.nextLine();
    if (ans.length() != 0 && ans.toLowerCase().charAt(0) == 'y')
      isHex = false;
    System.out.println("Paste the lines you wish to convert:");

    while (in.hasNextLine()) {
      String line = in.nextLine();
      if (isHex) {
        BigInteger bi = new BigInteger(line, 16);
        line = bi.toString(2);
        for (int i = line.length(); i < 32; i++)
          line = "0" + line;
        // System.out.println(line.length());
      }
      opcodes op = getOpcode(line);
      // System.out.println(op);
      if (op == opcodes.R_FORMAT) {
        String[] a = calculateR_FORMAT(line);
        // System.out.println(Arrays.toString(a));
        if (!a[4].equals("00000")) {
          System.out.println(t2.get(a[5]) + ", " + registers.get(Integer.parseInt(a[3], 2) + "") + ", "
              + registers.get(Integer.parseInt(a[2], 2) + "") + ", " + Integer.parseInt(a[4], 2));
        } else
          System.out.println(t2.get(a[5]) + ", " + registers.get(Integer.parseInt(a[3], 2) + "") + ", "
              + registers.get(Integer.parseInt(a[1], 2) + "") + ", " + registers.get(Integer.parseInt(a[2], 2) + ""));
      } else if (op == opcodes.J_FORMAT) {
        String[] a = calculateJ_FORMAT(line);
        System.out.println(t1.get(a[0]) + ", " + a[1]);
      } else { // Get the I formats
        String[] a = calculateI_FORMAT(line);
        if (a[0] == "000001") {
          System.out.println(t3.get(a[2]) + ", " + registers.get(Integer.parseInt(a[1], 2) + "") + ", "
              + Integer.parseInt(Integer.parseInt(a[4], 2) + ""));
        } else {
          if (a[1].equals("00000"))
            System.out.println(
                t1.get(a[0]) + ", " + registers.get(Integer.parseInt(a[2], 2) + "") + ", " + Integer.parseInt(a[3], 2));
          else
            System.out.println(t1.get(a[0]) + ", " + registers.get(Integer.parseInt(a[2], 2) + "") + ", "
                + registers.get(Integer.parseInt(a[1], 2) + "") + ", " + Integer.parseInt(a[3], 2));
        }
      }
    }

    in.close();
  }

  public static HashMap<String, String> setUp(Scanner in, String fileName) throws FileNotFoundException {
    in = new Scanner(new File(fileName));
    HashMap<String, String> t = new HashMap<>();
    while (in.hasNextLine()) {
      String[] l = in.nextLine().split(" ");
      t.put(l[0], l[1]);
    }
    in.close();
    return t;
  }

  public static opcodes getOpcode(String l) {
    l = l.substring(0, 6);
    int ll = Integer.parseInt(l, 2);
    if (ll == 0)
      return opcodes.R_FORMAT;
    else if (ll == 2 || ll == 3)
      return opcodes.J_FORMAT;
    return opcodes.I_FORMAT;
  }

  public static String[] calculateR_FORMAT(String line) {
    System.out.println(line.substring(26));
    // Order: opcode rs rt rd shamt funct
    String[] i = { s(line, 0, 6), s(line, 6, 11), s(line, 11, 16), s(line, 16, 21), s(line, 21, 26),
        (line.substring(26)) };
    return i;
  }

  public static String[] calculateJ_FORMAT(String line) {
    // Order: opcode target address
    String[] i = { s(line, 0, 6), line.substring(6) };
    return i;
  }

  public static String[] calculateI_FORMAT(String line) {
    // Order: opcode rs rt address/immediate
    String[] i = { s(line, 0, 6), s(line, 6, 11), s(line, 11, 16), line.substring(16) };
    return i;
  }

  public static String s(String line, int start, int end) {
    return line.substring(start, end);
  }
}