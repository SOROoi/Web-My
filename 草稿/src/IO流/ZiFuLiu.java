package IO流;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ZiFuLiu {

	public static void main(String[] args){
		// TODO Auto-generated method stub
		try {
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(""));
			BufferedWriter bwriter = new BufferedWriter(new FileWriter(""));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(new StringBuffer("xxa").reverse().toString());
	}

}
