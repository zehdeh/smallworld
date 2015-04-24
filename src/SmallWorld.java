import java.io.*;

import sw.Configuration;
import sw.ui.Window;

public class SmallWorld {
	public static void main(String[] args) {
		Configuration conf = new Configuration("res/config.xml");

		Window window = new Window(conf);
	}
}
