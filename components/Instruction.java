package components;

public class Instruction {
    private int instructionBinaryCode = 0;
    private String line;


    public Instruction(String Line) {
        this.line = Line;
        initialize();
    }

    public int getBinaryInt() {
        return instructionBinaryCode;
    }

    private void initialize() {
    	String [] instruction = line.split(" ");
    	
        // Switch on the OP code
    	switch(instruction[0]) {
            case "ADD": {
                break;
            }
            case "SUB": {
                instructionBinaryCode += 0b00010000000000000000000000000000;
                break;
            } 
            case "MUL": {
                instructionBinaryCode += 0b00100000000000000000000000000000;
                break;
            }
            case "MOVI": {
                instructionBinaryCode += 0b00110000000000000000000000000000;
                break;
            }
            case "JEQ": {
                instructionBinaryCode += 0b01000000000000000000000000000000;
                break;
            }
            case "AND": {
                instructionBinaryCode += 0b01010000000000000000000000000000;
                break;
            }
            case "XORI": {
                instructionBinaryCode += 0b01100000000000000000000000000000;
                break;
            }
            case "JMP": {
                instructionBinaryCode += 0b01110000000000000000000000000000;
                break;
            }
            case "LSL": {
                instructionBinaryCode += 0b10000000000000000000000000000000;
                break;
            }
            case "LSR": {
                instructionBinaryCode += 0b10010000000000000000000000000000;
                break;
            }
            case "MOVR": {
                instructionBinaryCode += 0b10100000000000000000000000000000;
                break;
            }
            case "MOVM": {
                instructionBinaryCode += 0b10110000000000000000000000000000;
                break;
            }
            case "NOP": {
                instructionBinaryCode =  0b11000000000000000000000000000000;
                return;
            }
        }
    	
        // Based on the OP type, encode rest of the instruction
    	switch(instruction[0]) {
            case "ADD":
            case "SUB":
            case "MUL":
            case "AND": {
                instructionBinaryCode += RegisterNumber(instruction[1]) << 23;
                instructionBinaryCode += RegisterNumber(instruction[2]) << 18;
                instructionBinaryCode += RegisterNumber(instruction[3]) << 13;
                break;
            }
            case "LSL":
            case "LSR": {
                instructionBinaryCode += RegisterNumber(instruction[1]) << 23;
                instructionBinaryCode += RegisterNumber(instruction[2]) << 18;
                instructionBinaryCode += Integer.parseInt(instruction[3]);
                break;
            }
            case "JEQ":
            case "XORI":
            case "MOVR":
            case "MOVM": {
                instructionBinaryCode += RegisterNumber(instruction[1]) << 23;
                instructionBinaryCode += RegisterNumber(instruction[2]) << 18;
                instructionBinaryCode += twosCompliment(Integer.parseInt(instruction[3]));
                break;
            }
            case "MOVI": {
                instructionBinaryCode += RegisterNumber(instruction[1]) << 23;
                instructionBinaryCode += twosCompliment(Integer.parseInt(instruction[2]));
                break;
            }
            case "JMP": {
                instructionBinaryCode += Integer.parseInt(instruction[1]);
                break;
            }
    	}
    }

    /**
     * Takes an integer number and translates it to string binary, adding zero padding to the left until it is required bits long.
     * @param num denary integer in the form of a String
     * @param bitLength number of bits binary string should end up as
     * @return binary string
     */
    public static String bitPadding(String num, int bitLength) {
        // Convert number to binary string
    	// String num = Integer.toBinaryString(Integer.parseInt(num));

        // Iterate until string is of required length
    	while(num.length() < bitLength)
    	{
    		num = "0" + num;
    	}
    	
    	return num;
	}

    /**
     * Converts register string to binary string value of that register.
     * ex. R3 -> 00011
     * @param Register register string
     * @return binary string value of register index
     */
    public static int RegisterNumber(String Register)
    {
    	return Integer.parseInt(Register.substring(1));
    }

    private static int twosCompliment(int x) {
        if (x > 0) {
            return x & 0b00000000000000111111111111111111;
        }

        return x;
    }
}