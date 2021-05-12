import java.util.ArrayList;
import java.util.Random;

// Solver class
public class AMeanSolver{

    // MAIN METHOD
    public static void main(String[] args){

        ProblemData sampleData = ProblemData.readSampleOffers();
        AMeanSolver solver = new AMeanSolver(sampleData);

        double maxProfit = solver.solveByRecursion(0, sampleData.areaCapacity);

        sampleData.showProblem();
        solver.presentSolution();
        System.out.println("Max profit: " + maxProfit);
    }


    // Attributes
    private ProblemData problem;
    private ArrayList<Offer> takenOffers = new ArrayList<Offer>();

    // Constructor
    public AMeanSolver(ProblemData problem){     
        this.problem = problem;
    }

    // Present Solution
    public void presentSolution(){

        String content = "Academic license - for non-commercial use only - Expires 2021-06-20" + "\n"
                        + "Optimize a model with " + this.problem.offers.size()  + " variables" + "\n"
                        + "Model fingerprint: " + this.hashCode() + "\n\n";

        content += "\n Taken Offers: \n\n";

        for(Offer o : takenOffers){
            content += o.toString();
        }

        System.out.println(content);


    }

    // SOLUTION METHOD 1
    public double solveByRecursion(int nextIndex, double freeArea){

        Offer currentOffer = problem.offers.get(nextIndex);
        System.out.println("\n\nNext Index: " + nextIndex + " MAX: " + (problem.offers.size() -1));
        System.out.println("Free area : " + freeArea);
        System.out.println("Current area : " + currentOffer.getArea());
        System.out.println("Current profit : " + currentOffer.getProfit());
        // Check if this is last item
        if(nextIndex == problem.offers.size() -1){

            // If There is enough area
            if (freeArea >= currentOffer.getArea()){
                System.out.println("case 1 return " + currentOffer.getProfit());
                // Take
                takenOffers.add(currentOffer);
                return currentOffer.getProfit();

            }else{ // Otherwise
                System.out.println("case 2 return 0");
                // Don't Take
                return 0;

            } 

        }// Otherwise Middle Process
        else{

            // If there is not enogh area
            if (freeArea < currentOffer.getArea()){
                System.out.println("case 3 return next");
                // Don't take and go next
                return 0 + solveByRecursion(nextIndex + 1, freeArea);

            }else{ // Otherwise
                
                // There are 2 options

                    // Take it
                double takeOption  = currentOffer.getProfit() + solveByRecursion(nextIndex + 1, freeArea - currentOffer.getArea());
                    // Leave it
                double leaveOption = 0 + solveByRecursion(nextIndex + 1, freeArea);
                // Return Which one has greater profit
                if( takeOption > leaveOption){
                    System.out.println("case 4 return " + takeOption);
                    takenOffers.add(currentOffer);
                    return takeOption;

                }else{
                    System.out.println("case 5 return " + leaveOption);
                    return leaveOption;

                }

            }
        }
    }

}

class ProblemData{

    // Constants
    private static int SAMPLE_COUNT = 3;
    private static int SAMPLE_AREA_MIN_LIMIT = 200;
    private static int SAMPLE_AREA_MAX_LIMIT = 500;
    private static int SAMPLE_PROFIT_MIN_LIMIT = 200;
    private static int SAMPLE_PROFIT_MAX_LIMIT = 500;
    private static int SAMPLE_CAPACITY_MIN_LIMIT = 400;
    private static int SAMPLE_CAPACITY_MAX_LIMIT = 2000;

    // Attributes
    public ArrayList<Offer> offers;
    public double areaCapacity;
    private static Random random = new Random();

    public static ProblemData readSampleOffers(){

        ProblemData pd = new ProblemData();
        ArrayList<Offer> tempOffers = new ArrayList<Offer>();

        for(int i = 0 ; i < SAMPLE_COUNT ; i++){

            Offer tempOffer = new Offer("Offer_"+(i+1), SAMPLE_AREA_MIN_LIMIT + random.nextInt(SAMPLE_AREA_MAX_LIMIT - SAMPLE_AREA_MIN_LIMIT) , SAMPLE_PROFIT_MIN_LIMIT + random.nextInt(SAMPLE_PROFIT_MAX_LIMIT - SAMPLE_PROFIT_MIN_LIMIT));
            tempOffers.add(tempOffer);

        }

        pd.offers = tempOffers;
        pd.areaCapacity = SAMPLE_CAPACITY_MIN_LIMIT + random.nextInt(SAMPLE_CAPACITY_MAX_LIMIT - SAMPLE_CAPACITY_MIN_LIMIT);
        
        return pd;
    }

    public void showProblem(){

        System.out.println("OFFERS : \n");
        for (Offer o : offers){
            System.out.println(o);
        }

        System.out.println("CAPACITY: " + areaCapacity);
    }

}


// Offer Class
class Offer{

    // Attribute
    private String offerName;
    private double area;
    private double profit;

    // Constructor
    public Offer(String name,double area, double profit){

        this.offerName = name;
        this.area = area;
        this.profit = profit;

    }

    // Getter Setter
    public String getOfferName(){
        return this.offerName;
    }

    public String toString(){
        return "\n--- Offer ---" + "\n"
            + "Offer Name: " + this.offerName + "\n"
            + "Area      : " + this.area      + "\n"
            + "Profit    : " + this.profit    + "\n";
    }

    public double getArea(){
        return this.area;
    }

    public double getProfit(){
        return this.profit;
    }

        
}


