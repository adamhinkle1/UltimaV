package hexEdit;
import java.io.*;
import java.util.*;
public class rewrite {
//"/Users/adamhinkle/Desktop/U/SAVED.GAM"
	public static void main(String[] args) throws IOException {
		String hex;
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter file path for saved.gam:");
		String path = in.nextLine();
		RandomAccessFile game = new RandomAccessFile(path, "rw");  //this object allows us to modify binary data within path
		
		
	    System.out.println("How much Strength would you like?(99 max)");
	    int str = in.nextInt();
	    for (int i = 0; i < 16; i++) {  //loop will assign desired stat to each character
	    	game.seek((14 + 32*i));  //each characters data is 32 bytes long.  Strength value stored in byte offset 14
		    game.write(str);
	    }
	    System.out.println("How much Intelligence would you like?(99 max)");  
	    int intel = in.nextInt();
	    for (int i = 0; i < 16; i++) {
	    	game.seek((16 + 32*i));
		    game.write(intel);
	    }
	    System.out.println("How much Dexterity would you like?(99 max)");
	    int dex = in.nextInt();
	    for (int i = 0; i < 16; i++) {
	    	game.seek((15 + 32*i));
		    game.write(dex);
	    }
	    System.out.println("How much HP would you like?(999 max)");  //stats whose value is stored on multiple bytes requires conversion to littleEndian
	    int hp = in.nextInt();
	    hex = littleEnd(hp,4);  //get little endian hexidecimal string
	    String hp1 = hex.substring(0,2);  //substring length 2 holds hexidecimal info for first byte
	    String hp2 = hex.substring(2,4);  // second byte
	    int ihp1 = Integer.parseInt(hp1, 16);  //convert hex string into integer
	    int ihp2 = Integer.parseInt(hp2, 16);
	    for (int i = 0; i < 16; i++) {
	    	game.seek((18 + 32*i));   
		    game.write(ihp1);  //write first byte into file
		    game.write(ihp2);  //write second byte into file
	    }
	    System.out.println("How much MAX HP would you like?(999 max)");
	    int mhp = in.nextInt();
	    hex = littleEnd(mhp,4);
	    String mhp1 = hex.substring(0,2);
	    String mhp2 = hex.substring(2,4);
	    int imhp1 = Integer.parseInt(mhp1, 16);
	    int imhp2 = Integer.parseInt(mhp2, 16);
	    for (int i = 0; i < 16; i++) {
	    	game.seek((20 + 32*i));
		    game.write(imhp1);
		    game.write(imhp2);
	    }
	    System.out.println("How much EXP would you like?(9999 max)");
	    int exp = in.nextInt();
	    hex = littleEnd(exp,4);
	    String exp1 = hex.substring(0,2);
	    String exp2 = hex.substring(2,4);
	    int iexp1 = Integer.parseInt(exp1, 16);
	    int iexp2 = Integer.parseInt(exp2, 16);
	    for (int i = 0; i < 16; i++) {
	    	game.seek((22 + 32*i));
		    game.write(iexp1);
		    game.write(iexp2);
	    }
	    System.out.println("How much GOLD would you like?(9999 max)");
	    int gold = in.nextInt();
	    hex = littleEnd(gold,4);
	    String g1 = hex.substring(0,2);
	    String g2 = hex.substring(2,4);
	    int ig1 = Integer.parseInt(g1, 16);
	    int ig2 = Integer.parseInt(g2, 16);
	    game.seek(516);
	    game.write(ig1);
	    game.write(ig2);
	    System.out.println("How many keys would you like?(999 max)");
	    int key = in.nextInt();
	    game.seek(518);
	    game.write(key);
	    System.out.println("How many Skull Keys would you like?(99 max)");
	    int skey = in.nextInt();
	    game.seek(523);
	    game.write(skey);
	    System.out.println("How many GEMS would you like?");
	    int gem = in.nextInt();
	    game.seek(519);
	    game.write(gem);
	    System.out.println("How many Black Badge would you like?(99 max)");
	    int BB = in.nextInt();
	    game.seek(536);
	    game.write(BB);
	    System.out.println("How many Magic Carpet would you like?(99 max)");
	    int MC = in.nextInt();
	    game.seek(522);
	    game.write(MC);
	    System.out.println("How many Magic Axes would you like?(99 max)");
	    int MA = in.nextInt();
	    game.seek(576);
	    game.write(MA);
	    in.close();
	    game.close();
	    System.out.println("Enjoy!");
	}
	//method accepts an integer value, along with the number of bytes which it occupies. returns String in little endian hexidecimal
	public static String littleEnd(int hex, int size) {
		String hexs = Integer.toHexString(hex);  
		String sub = "";
		while (hexs.length() < size) {  //pad with leading 0's for appropriate byte length
			hexs = "0" + hexs;
		}
		String littleEndhex = "";  //new string will be littleEndian
		for (int i = 0; i < hexs.length(); i +=2) {  
			sub = hexs.substring(size-i-2,size-i);   //iterate backwards through string, forming 2-char substrings
			littleEndhex  += sub;  //append 2-char substrings to littleEndian string
		}
		return littleEndhex;  //return littleEndian string
	}
}

