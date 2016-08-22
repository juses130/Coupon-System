package com.testpack;

import java.util.InputMismatchException;

/**
 * 
 * This is a Helper Class - Sharing data.
 * The main object of this Class is to share data (Strings, longs, ints, booleans) 
 * between other classes and methods around the TESTS. and only for the tests.
 * 
 * @author Raziel
 */

public abstract class SharingData {

	// Premitives Variables
	
	private static String varchar1;
	private static String varchar2;
	private static String varchar3;
	private static String varchar4;
	private static String ExceptionMessage = null;
	
	private static short shortNum1;
	
	private static int intNum1;
	private static int intNum2;
	
	private static int intNumChoice;

	private static long longNum1;
	private static long longNum2;
	private static long longNum3;
	private static long IdsShare;
	
	
	// boolean
//	private static boolean flag1 = false;


	private SharingData() {}
	
	
	//////////////////////
	//////////////////////
	
	
	public static String getVarchar1() {
		return varchar1;
	}

	public static void setVarchar1(String varchar) {
		SharingData.varchar1 = varchar;
	}

	public static int getIntNum1() {
		return intNum1;
	}

	public static void setIntNum1(int intNum) {
		SharingData.intNum1 = intNum;
	}

	public static long getLongNum1() {
		return longNum1;
	}

	public static void setLongNum1(long longNum) {
		SharingData.longNum1 = longNum;
	}

	public static String getVarchar2() {
		return varchar2;
	}


	public static void setVarchar2(String varchar2) {
		SharingData.varchar2 = varchar2;
	}


	public static int getIntNum2() {
		return intNum2;
	}


	public static void setIntNum2(int intNum2) {
		SharingData.intNum2 = intNum2;
	}


	public static long getLongNum2() {
		return longNum2;
	}


	public static void setLongNum2(long longNum2) {
		SharingData.longNum2 = longNum2;
	}


	public static String getVarchar3() {
		return varchar3;
	}


	public static void setVarchar3(String varchar3) {
		SharingData.varchar3 = varchar3;
	}


	public static int getIntNumChoice() {
		return intNumChoice;
	}


	public static void setIntNumChoice(int choice) {
		
		try {
			if (choice < 0 || choice > 2) {
				throw (new IllegalArgumentException());
			} // IF - choice
			else {
				SharingData.intNumChoice = choice;
			} // else
		} // try
		// Catch the two problems we can get. Strings and Numbers.
		catch (InputMismatchException | IllegalArgumentException e) {
			System.out.println("Error - Please take a look again in the Option Numbers.");

		} // catch
		
	}


	public static String getVarchar4() {
		return varchar4;
	}


	public static void setVarchar4(String varchar4) {
		SharingData.varchar4 = varchar4;
	}


	public static long getLongNum3() {
		return longNum3;
	}


	public static void setLongNum3(long longNum3) {
		SharingData.longNum3 = longNum3;
	}
	
	public static short getShortNum1() {
		return shortNum1;
	}


	public static void setShortNum1(short shortNum1) {
		SharingData.shortNum1 = shortNum1;
	}


	public static long getIdsShare() {
		return IdsShare;
	}


	public static void setIdsShare(long IdShare) {
		SharingData.IdsShare = IdShare;
	}


	public static String getExceptionMessage() {
		return ExceptionMessage;
	}


	public static void setExceptionMessage(String ExceptionMessage) {
		SharingData.ExceptionMessage = "\n" + ExceptionMessage;
	}


	
	//////////////
	
	
	
}
