package draw;

public class ScreenResolution {
	private static int width = 1024;
	private static int height = 768;

	public static int getSize(String str){
		if (str.equals("width")){
			return width;
		}
		else if(str.equals("height")){
			return height;
		}
		else{
			return 0;
		}
	}
}