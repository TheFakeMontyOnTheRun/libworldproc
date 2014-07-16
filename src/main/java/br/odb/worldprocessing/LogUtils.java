/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.odb.worldprocessing;

/**
 * 
 * @author monty
 */
class LogUtils {

	public static int LEVEL_VERBOSE = 1;
	public static int LEVEL_NORMAL = 0;

	static int level = 0;

	static int getLevel() {
		return level;
	}
}
