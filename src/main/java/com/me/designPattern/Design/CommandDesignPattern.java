package com.me.designPattern.Design;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * <code>
 * Add 
 * Divide 
 * Substract 
 * Multiply
 * </code>
 * 
 * @author lmisra
 *
 */
public class CommandDesignPattern {
	private static HashMap<String, Operation> HM_CMDS = new HashMap<String, Operation>();

	// Approach 1 : Create object using new operator.
	// Disadvantage : Addition of new operation requires an entry in HM_CMDS
	// resulting changes in existing code.
	static {
		/**
		 * 
		 * <code>
		 
		//	Approach 1 : Create object using new operator.
		// 	Disadvantage : Addition of new operation requires an entry in HM_CMDS resulting changes in existing code.
		 
				HM_CMDS.put(Commands.ADD, new Add());
				HM_CMDS.put(Commands.DIVIDE, new Divide());
				HM_CMDS.put(Commands.SUBSTRACT, new Substract());
				HM_CMDS.put(Commands.MUL, new Multiply());
				
		//	Approach 2 : put the command name and fully qualified class path in an xml file or in a properties file
		 				 use reflection to instantiate object and populate map.
		// Disadvantage : ** Why to maintain a property file , can it be done without using a property file.
		 	* </code>
		 */
		final String path = System.getProperty("user.dir")
				+ "\\src\\main\\java\\com\\me\\designPattern\\Design\\cmds.properties";
		try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
			stream.forEach(s -> {
				String cmdAndClassPath[] = s.split("=");
				try {
					HM_CMDS.put(cmdAndClassPath[0].trim(),
							(Operation) Class.forName(cmdAndClassPath[1].trim()).newInstance());
				} catch (Exception e) {
					e.printStackTrace();
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Approach 3 : To be continued.....

	}

	Integer calculate(String CMD, Integer p1, Integer p2) {
		Integer retunValue = null;
		Operation op = HM_CMDS.get(CMD.trim());
		retunValue = op != null ? op.execute(p1, p2) : retunValue;
		return retunValue;
	}

	public static void main(String[] args) {
		CommandDesignPattern pattern = new CommandDesignPattern();
		System.out.println("ADD 		2 and 4 = " + pattern.calculate(Commands.ADD, 2, 4));
		System.out.println("Substract 	4 and 2 = " + pattern.calculate(Commands.SUBSTRACT, 4, 2));
		System.out.println("DIVIDE 		4 and 2 = " + pattern.calculate(Commands.DIVIDE, 4, 2));
		System.out.println("MUL 		4 and 2 = " + pattern.calculate(Commands.MUL, 4, 2));
	}
}

interface Operation {
	Integer execute(Integer op1, Integer op2);
}

class Add implements Operation {
	public Integer execute(Integer op1, Integer op2) {
		return op1 + op2;
	}
}

class Divide implements Operation {
	public Integer execute(Integer op1, Integer op2) {
		return op1 / op2;
	}
}

class Substract implements Operation {
	public Integer execute(Integer op1, Integer op2) {
		return op1 - op2;
	}
}

class Multiply implements Operation {
	public Integer execute(Integer op1, Integer op2) {
		return op1 * op2;
	}
}

interface Commands {
	String ADD = "ADD";
	String DIVIDE = "DIVIDE";
	String SUBSTRACT = "SUBSTRACT";
	String MUL = "MUL";
}