// Daniel Yu 501120369
import java.util.TreeMap;
import java.util.Map;
public class Rating implements Cloneable {
    private Map<Integer, Integer> ratings = new TreeMap<Integer, Integer>(); // object for rating that has rating, amount of times rated
    public Rating(){ // make new blank rating object
        for(int i = 1; i < 6; i++){
            ratings.put(i, 0);
        }
    }

    public Rating(Map<Integer, Integer> rating, int rate){ // make new rating object with old rating object and a customer's rate
        for(int i = 1; i < 6; i++){  // go though ratings map
            if (i == rate) // check if rating matches customer's rating
                ratings.put(i, rating.get(i) + 1); // add the old rating object plus the customer's rating
            else    
                ratings.put(i, rating.get(i)); // just add the old rating object
        }
    }

    public void print(){
        for(int i: ratings.keySet()){ // go thorugh ratings and print ratings
            System.out.printf("\nRating: %-5s Number of rates: %-9s ", i, ratings.get(i));
        }
    }

    public Rating addRate(Rating oldRate, int rate){ // make new rating object and add the rate
        Rating newRate = new Rating(oldRate.ratings, rate); // other objects also change when modified so create new object
        return newRate;
    }

    public int averageRating(){ // calculate average rating of product
        double total = 0;
        double totalRates = 0;
        for(int i: ratings.keySet()){ // go through all ratings of a product
            total+= (i * ratings.get(i)); // calculate total
            totalRates+= ratings.get(i); // calculate total rates
        }
        if (totalRates == 0) // check if no rates for divide by zero error
            return 0;
        return (int)(Math.ceil(total / totalRates)); // return average
    }
}
