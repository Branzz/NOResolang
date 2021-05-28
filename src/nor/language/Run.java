package nor.language;

public class Run {

	public static void main(String[] args) {
//		CompilerExecute co1 = new CompilerExecute(
//				">a>>a<a"
//				);
		for (String code : new String[] {
				"^>^>>>>.", // print 0
				"^>^>>>>.^.^<^>.^.^<^<^>>.^.^<^>.^.^<^<^<^>>>.^.", // print 0123456789
				">>>>,^<^<^<^<^<^>>>>>.", // print input
				",^<^<^<^<^<^>>>>>.^@^.^#", }) // halting; 1: 111... 0: 0
		new Compiler(code).run();

//		",>>>>>>>>,v<<<<<<<<"


		/*
		 ,^<^<^<^<^<^>>>>>.^@^.^#
		...# makes infinite loop from start to #
		@... ends the program
		make these have errors?
		^>^>>>>,^<^<^<^>>>>^>^>>>>,^<^<^<^>>>> >>>>> adder start
		*/
	}

}
