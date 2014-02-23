import amazon.SubSegment4;


public class Executor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		args = new String[6];
		String texted=  "This is a test. This is a programming test. This is a programming test in any language.";
		args[1] ="4";
		args[2] ="this";
		args[3]="this";
		args[4]="this";
		args[5]="this";
		StringBuilder text = new StringBuilder(texted);
	//	for(int i=0;i<2900;i++){
			//text.append(texted);
		//}
		args[0]=text.toString();
		long startTime =System.nanoTime();
		SubSegment4.main(args);
		double elapsed = (System.nanoTime()-startTime)/1e9;
		System.out.println("Elapsed time in seconds is["+elapsed+"]");
	}

}
