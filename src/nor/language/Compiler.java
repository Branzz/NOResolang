package nor.language;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

public class Compiler {

	private char[] code;
	private String stringCode;
	private HashMap<String, List<Integer>> functions;
	private final char[] usedKeys = { '<', '>', '^', 'v', '@', '#', ',', '.', '|'};//, '*' 

	private ArrayList<Boolean> memory;
	private int pointerIndex;
	private boolean pointerValue;

//	private boolean looping;
	private boolean definingFunction;
	private boolean readingFunctionName;
	private String functionName;

	private int startFunctionIndex;
	private int startFunctionNameIndex;
//	private int EndFunctionNameIndex;

	public Compiler(String inputCode) {
		inputCode = inputCode.replaceAll(" ", "").replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "");
		String uncommentedStringCode = "";
		boolean commenting = false;
		for (int i = 0; i < inputCode.length(); i++) {
			if (inputCode.charAt(i) == '*')
				commenting ^= true;
			if (!commenting)
				uncommentedStringCode += inputCode.substring(i, i + 1);
		}
		stringCode = uncommentedStringCode.replaceAll("\\*", "");
		code = stringCode.toCharArray();
		memory = new ArrayList<Boolean>();
		memory.add(false);
		functions = new HashMap<String, List<Integer>>();
//		pointerValue = false;
		//TODO Declare all variables
	}

	public void run() {
		for (int i = 0; i < code.length; i++) {
			switch (code[i]) {
			case '<':
				if (--pointerIndex < 0) {
					memory.add(0, false);
					if (memory.size() > Integer.MAX_VALUE)
						;//TODO exception
					pointerIndex = 0;
				};
				break;
			case '>':
				if (++pointerIndex >= memory.size()) {
						memory.add(false);
					if (memory.size() > Integer.MAX_VALUE)
						;//TODO exception
				}
				break;
			case '^':
				memory.set(pointerIndex, nor(pointerValue, memory.get(pointerIndex)));
				break;
			case 'v':
				pointerValue = nor(pointerValue, memory.get(pointerIndex));
				break;
			case '@':
				if (!nor(pointerValue, memory.get(pointerIndex))) {
					int level = 1;
					do {
						if (code[i] == '#')
							level++;
						else if (code[i] == '@')
							level--;
						i++;
					} while (i < code.length && !(code[i] == '#' && level == 0));
				}
				if (i >= code.length)
					;//TODO exception
					break;
			case '#':
				int level = -1;
				do {
					if (code[i] == '#')
						level++;
					else if (code[i] == '@')
						level--;
					i--;
				} while (i > 0 && !(code[i] == '@' && level == 0));
				i--;
				if (i + 1 < 0)
					;//TODO exception
				break;
			case '.':
				String charValue = "";
				for (int j = pointerIndex - Math.min(16, pointerIndex + 1) + 1; j <= pointerIndex; j++)
					charValue += memory.get(j) ? "1" : "0";
				System.out.print((char) Integer.parseInt(charValue, 2));
				break;
			case ',':
				int inputValue = JOptionPane.showInputDialog(null, "Input char:", "", JOptionPane.QUESTION_MESSAGE).charAt(0);
				for (int j = 0; j < 16 && (inputValue >> j >= 1); j++) {
					if (j > pointerIndex) {
						memory.add(0, false);
						if (memory.size() > Integer.MAX_VALUE)
							;//TODO exception
						pointerIndex++;
					}
					memory.set(pointerIndex - j, nor(memory.get(pointerIndex - j), (inputValue >> j & 1) == 1));
				}
				break;
//			case '|':
//				if (readingFunction) {
//					functions.put(stringCode.substring(startFunctionNameIndex, startFunctionIndex), startFunctionIndex + "," + i);
//					readingFunction = false;
//				}
//				break;
			default:
				if (startFunctionNameIndex != -1)
					startFunctionNameIndex = i;

				//move i and skip the content of the function if it's definition to the point where it would execute it
				readingFunctionName = true;

				if (readingFunctionName)
					functionName += code[i];

				if (i < code.length - 1 && isUsedKey(code[i + 1])) { //found function
					readingFunctionName = false;
					startFunctionIndex = i + 1;
					int functionNameLength = startFunctionIndex - startFunctionNameIndex; //TODO
					if (functions.containsKey(stringCode.substring(startFunctionNameIndex, startFunctionIndex))) { //it's defined
//						String[] value = functions.get(functionName).split(",");
//						for (int i0 = Integer.parseInt(value[0]); i0 <= Integer.parseInt(value[1]); i0++) //two ways to do it: 1, use assist method and run through it. 2, use the looping boolean
							for (int i0 = functions.get(stringCode.substring(startFunctionNameIndex, startFunctionIndex)).get(0); i0 <= functions.get(stringCode.substring(startFunctionNameIndex, startFunctionIndex)).get(1); i0++)
//							execute(code[i0]);
							;
						}
					else //it's not defined yet
						for (int j = i; j < code.length - functionNameLength; j++) {//TODO check to work
							if (stringCode.substring(j, j + functionNameLength).equals(functionName)) {
								ArrayList<Integer> values = new ArrayList<Integer>();
								values.add(startFunctionIndex);
								values.add(i);
								functions.put(stringCode.substring(startFunctionNameIndex, startFunctionIndex), values);
							}
						}
//						readingFunction = true;
					startFunctionNameIndex = -1;
				}

				break;
			}
//			System.out.print(i + " " + pointerIndex + " " + (pointerValue ? "1" : "0") + "\t");
//			for (boolean b : memory)
//				System.out.print(b ? "1" : "0");
//			System.out.println();
		}
	}

	private boolean nor(boolean value1, boolean value2) {
		return !(value1 || value2);
	}

	private boolean isUsedKey(char ch) {
		for (char c : usedKeys)
			if (ch == c)
				return true;
		return false;
	}

}
