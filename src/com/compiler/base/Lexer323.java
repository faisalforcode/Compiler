package com.compiler.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.EnumUtils;

public class Lexer323 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileName = "";
		String line = null;
		System.out.println("Enter input path:");
		Scanner scan = new Scanner(System.in);
		fileName = scan.nextLine();
		scan.close();
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			FileWriter fileWriter = new FileWriter(fileName.substring(0, fileName.length() - 4) + "_out.txt");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write("Token			Lexeme\r\n--------------------------------------------");
			while ((line = bufferedReader.readLine()) != null) {

				bufferedWriter.write(lexer(line));
			}
			bufferedReader.close();
			bufferedWriter.close();
			System.out.println("Output written to " + fileName.substring(0, fileName.length() - 4) + "_out.txt");
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	private static String lexer(String line) {
		String records = "";
		ArrayList<String> arr = new ArrayList<String>();
		String tokenMake = "";
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == '.' || line.charAt(i) == '#' || Character.isLetterOrDigit(line.charAt(i))) {

				tokenMake += line.charAt(i);
			} else {
				if (!tokenMake.isEmpty() && !tokenMake.contains(" "))
					arr.add(tokenMake);
				tokenMake = "";
			}

			if (!(line.charAt(i) == '.' || line.charAt(i) == '#' || Character.isLetterOrDigit(line.charAt(i)))) {
				if (line.charAt(i) == ':') {
					arr.add(":=");
					i++;
					continue;
				}
				if (line.charAt(i) != ' ')
					arr.add(line.charAt(i) + "");

			}

		}
		arr.add(tokenMake);

		for (String words : arr) {
			if (words.contains("##")) {
				records = "\r\n**************************\r\nCompiler error - consecutive # not allowed\r\n************************** ";
				break;
			}

			if (words.isEmpty() || words.contains(" ") || words.contains("\r\n"))
				continue;
			else if (checkKeyword(words))
				records += "\r\nkeyword			" + words;
			else if (checkSeperator(words))
				records += "\r\nseperator		" + words;
			else if (checkOperator(words))
				records += "\r\noperator		" + words;
			else if (checkReal(words))
				records += "\r\nreal			" + words;
			else if (checkInteger(words))
				records += "\r\ninteger			" + words;

			else {
				records += "\r\nidentifier		" + words;
			}
		}
		return records;

	}

	private static boolean checkInteger(String words) {

		return (Character.isDigit(words.charAt(0)));
	}

	private static boolean checkReal(String words) {
		// TODO Auto-generated method stub
		if (words.contains("."))
			return true;
		return false;
	}

	private static boolean checkOperator(String words) {
		// TODO Auto-generated method stub
		if (words.equals("@") || words.equals(":=") || words.equals("=") || words.equals("|") || words.equals("&")
				|| words.equals(">") || words.equals("<") || words.equals("*") || words.equals("+") || words.equals("-")
				|| words.equals("/"))
			return true;
		return false;
	}

	private static boolean checkSeperator(String words) {
		if (words.equals("(") || words.equals(")") || words.equals("{") || words.equals("}") || words.equals(";"))
			return true;
		return false;
	}

	private static boolean checkKeyword(String words) {
		return contains(words);
	}

	/*
	 */
	public static boolean contains(String word) {
		for (Keywords key : Keywords.values()) {
			System.out.println(key + word);
			if (word.equalsIgnoreCase(key.toString())) {
				return true;
			}
		}
		return false;
	}

}
