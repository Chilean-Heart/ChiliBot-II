package com.team2576.lib.util;

import java.util.ArrayList;
import java.util.Vector;

/**
*
* @author Lucas
*/

public class ChiliFunctions {
	
	/**
	 * Gives the sign of a number.
	 *
	 * @param val The value to extract sign from.
	 * @return 1 if the value was larger than 0, -1 if the value was smaller than 0 and 0 if the value was 0
	 */
	public static double signof(double val) {
		//Ternary magic!
		return val != 0 ? val / Math.abs(val) : 0;
	}
   
	/**
	* Function to obtain absolute value of a number.
	* 
	* @param val value to obtain absolute value
	* @return Absolute value of 'val'
	*/
   public static double abs(double val) {
       if(val < 0) {
           return val * -1;
       }
       return val;
   }
   
   /**
    * Dead band. Used to control inputs from joysticks
    *
    * @param val The value from a joystick axis.
    * @param threshold The minimum value from which to output the original axis value
    * @return The original value if larger than threshold, else 0
    */
   public static double deadBand(double val, double threshold) {
	   //Ternary magic!
	   //return Math.abs(val) > threshold ? val : 0;
	   
	   if(Math.abs(val) > threshold) {
		   return val;
	   }
	   return 0;
   }
   
   /**
    * Fills an array with zeros. Used to allocate all memory required by the array to a known value (0).
    *
    * @param array The array to fill
    * @return A double[] containing only zeros.
    */
   public static double[] fillArrayWithZeros(double[] array) {
	   for (int i = 0; i < array.length; i++) {
		   array[i] = 0;
	   }
	   return array;
   }
   
   /**
    * Fills an array with a value.
    *
    * @param array The array to fill
    * @param value The value to fill the array with
    * @return An array of type double[] filled with the value given
    */
   public static double[] fillArrayWithValue(double[] array, double value) {
	   for (int i = 0; i < array.length; i++) {
		   array[i] = value;
	   }
	   return array;
   }
   
   /**
    * Checks if array is filled with zeros.
    *
    * @param array An empty array of doubles
    * @return true, if array solely contains 0
    */
   public static boolean isArrayWithZeros(double[] array){
	   for (int i = 0; i < array.length; i++) {
		   if (array[i] != 0) {
			   return false;
		   }
	   }
	   return true;
   }
   
   /**   
    * Clamps value between two values.
    * 
    * @param val Value to clamp
    * @param min Minimum value for val
    * @param max Maximum value for val
    * @return val Clamped value between two values
    */   
   public static double clamp(double val, double min, double max){
	   
	   //Ternary magic!
	   //return val > max ? max : (val < min ? min : val);
	   
       if(val > max) {
           return max;
       } else if(val < min) {
           return min;
       } else {
       return val;
       }
   }
   
   /**
    * Set a value to zero if it is larger than a maximum value.
    *
    * @param val the val
    * @param max the max
    * @return the double
    */
   public static double overFlowToZero(double val, double max) {
	   //Ternary magic!
	   //return val > max ? 0 : val;
	   
	   if(val > max) {
		   return 0;
	   }
	   return val;
   }
   
   /**
    * Clamps an output between [-1, 1].
    *
    * @param val The original output
    * @return The clamped value
    */
   public static double clamp_output(double val) {
      return clamp_output(val,1.0);
   }
   
   /**
    * Clamps an output.
    *
    * @param val The original output
    * @param max The absolute value of the maximum range of the output
    * @return The clamped value
    */
   public static double clamp_output(double val, double max) {
	   //Ternary magic!
	   //return (Math.abs(val) > max ? (max * (val / Math.abs(val))) : val);
	   
	   if(Math.abs(val) > max) {
		   if(val < 0){
			   return -max;
		   }
		   return max;
	   }
	   return val;	
   }
   
   /**
    * Normalize speeds. Used mainly in drive equations
    *
    * @param values An array containing the values to be normalized
    * @return An array of doubles the same length as <b>values</b> 
	*		  containing the normalized values
    */
   public static double[] normalize(double[] values) {
       double max_val = 0;
       double[] return_vals = new double[values.length];
       for(int i = 0; i < values.length; i++){
           if(Math.abs(values[i]) > max_val){
               max_val = Math.abs(values[i]);
           }
       }
       if(max_val <= 1){
           return values;
       } else {
           for(int j = 0; j < values.length; j++){
               return_vals[j] = values[j] / max_val;
           }
       }
       return return_vals;
   }
   
   
   /**
    * Rotates a vector. Used in holonomic drive train equations
    *
    * @param x The x component of the vector
    * @param y The y component of the vector
    * @param theta The angle of the vector
    * @return The components of the vector in an array of size 2 named <b>out</b>. <ul><li>x = out[0]</li> <li>y = out[1]</li><ul>
    */
   public static double[] rotateVector(double x, double y, double theta) {
       double cosA = Math.cos(theta * (3.14159 / 180.0));
       double sinA = Math.sin(theta * (3.14159 / 180.0));
       double out[] = new double[2];
       out[0] = x * cosA - y * sinA;
       out[1] = x * sinA + y * cosA;
       return out;
   }

   /**
    * Finds an object within a given two-dimensional vector of vectors.
    *
    * @param outer_index The index for the vector within the outermost vector
    * @param inner_index The index for the object within the innermost vector
    * @param vector 	 The vector of vectors to search within
    * @return The object asked located at that index
    */   
   @SuppressWarnings("rawtypes")
   public static Object doubleDimensionVectorValue(Vector vector, byte outer_index, byte inner_index) {
	   Object temp = vector.elementAt(outer_index);
	   Vector temp2 = (Vector) temp;
	   return temp2.elementAt(inner_index); 
   }
   
   /**
    * Transpose an ArrayList<> of containing double[].
    *
    * @param array The array to tranpose. Must be an ArrayList<double[]>
    * @return The transposed array as a double[][]
    */
   public static double[][] transpose(ArrayList<double[]> array) {
	   if (array == null || array.size() == 0) return null;
   
	   int width = array.size();
	   int height = array.get(0).length;

	   double[][] array_new = new double[height][width];

	   for (int x = 0; x < width; x++) {
		   for (int y = 0; y < height; y++) {
			   array_new[y][x] = array.get(x)[y];
		   }
	   }
	   return array_new;
   }
   
}
