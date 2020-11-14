/**
 * 
 */
package auctionhouse;

/**
 * @author pbj
 */
public class Money implements Comparable<Money> {
 
    private double value;
    
    /**
     * Returns a particular amount of pounds of double type multiplied by a hundred and rounded.
     * 
     * @param pounds the amount of money in pounds
     * @return       the amount of money in pounds multiplied by a hundred and rounded
     */
    
    private static long getNearestPence(double pounds) {
        return Math.round(pounds * 100.0);
    }
 
    /** 
     * Executes the method getNearestPence() on the input and returns the result divided by a hundred.
     * 
     * @param pounds the amount of money in pounds
     * @return       the amount of money in pounds multiplied by a hundred, rounded and divided by a hundred
     */
    
    private static double normalise(double pounds) {
        return getNearestPence(pounds)/100.0;
        
    }
 
    /**
     * Converts the input of string type to double type, executes the method normalise() on it and assigns the result to the variable value.
     * 
     * @param pounds the amount of money in pounds
     */
    
    public Money(String pounds) {
        value = normalise(Double.parseDouble(pounds));
    }
    
    /**
     * Assigns the input of double type to the variable value.
     * 
     * @param pounds the amount of money in pounds
     */
    
    private Money(double pounds) {
        value = pounds;
    }
    
    /**
     * Returns a new instance of the class Money whose value field is the sum of the value of the input and the value stored in the variable value.
     * 
     * @param m an instance of the class Money
     * @return  a new instance of the class Money whose value field equals the sum of the value of the input of the method and 
     *          the value stored in the variable value
     */
    
    public Money add(Money m) {
        return new Money(value + m.value);
    }
    
    /**
     * Returns a new instance of the class Money whose value field is the value stored in the variable value minus the value of the input.
     * 
     * @param m an instance of the class Money
     * @return  a new instance of the class Money whose value field equals the difference of the value stored in the variable value
     *          and the value of the input of the method
     */
    
    public Money subtract(Money m) {
        return new Money(value - m.value);
    }
 
    /**
     * Returns a new instance of the class Money whose value field equals value incremented by a percentage of the input and normalised.
     * 
     * @param percent the amount of increment in percentage
     * @return        a new instance of the class Money whose value field has been incremented by a percentage of the input
     *                and then normalised through the method normalise()
     */
    
    public Money addPercent(double percent) {
        return new Money(normalise(value * (1 + percent/100.0)));
    }
    
    /**
     * Converts the value of the variable value to String up to two decimal places.
     * 
     * @return the value of the variable value of string type up to two decimal places
     */
    
    @Override
    public String toString() {
        return String.format("%.2f", value);

    }
    
    /**
     * Compares the value of the input with the value stored in the variable value.
     * 
     * @param m an instance of the class Money
     * @return  a value greater than zero, less than zero or zero 
     *          if the value stored in the variable value is greater than, less than or equal to the value of the input, respectively
     */
    
    public int compareTo(Money m) {
        return Long.compare(getNearestPence(value),  getNearestPence(m.value)); 
    }
    
    /**
     * Compares the value of the input with the other instance's value according to the method compareTo().
     * 
     * @param m an instance of the class Money
     * @return  True if the value of the input is greater than the other instance's value and false otherwise
     */
    
    public Boolean lessEqual(Money m) {
        return compareTo(m) <= 0;
    }
    
    /**
     * Checks if the input is an instance of the class Money and if it is, checks if the input's value equals the other instance's value. 
     * 
     * @param o an object
     * @return  False if the input is not an instance of the class Money, and true if
     *          the input is an instance of the class Money and its value equals the value of the other instance 
     */
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Money)) return false;
        Money oM = (Money) o;
        return compareTo(oM) == 0;       
    }
    
    /**
     * Executes the method getNearestPence() on the variable value and returns its hash code.
     * 
     * @return the value of hash code
     */
    
    @Override
    public int hashCode() {
        return Long.hashCode(getNearestPence(value));
    }
      

}
