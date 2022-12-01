import java.io.*;
public class Main {
    public static void main(String[] args) throws IOException {

        String mainDataFile ="src/data.csv";
        String dayDATA ="src/DayData.csv";
        String hourData = "src/HourData.csv";
        String stock="https://www.google.com/finance/quote/RELIANCE:NSE?sa=X&ved=2ahUKEwiavP6vgpr7AhVuRmwGHW8TD1IQ_AUoAXoECAEQAw";//link of stock to be tracked from google finance
        StockTracker stockTracker = new StockTracker();

//        int i=0;
//        while(i==0){
//            stockTracker.blockMaker(mainDataFile,stock);
//        }

//        System.out.println(stockTracker.getStockPrice(stock));
//        stockTracker.sortIntoDAYS(mainDataFile);
//        stockTracker.sortIntoHOURS(mainDataFile);

//        System.out.println(stockTracker.getStockPrice(stock));
//        System.out.println(stockTracker.readDataCSV(mainDataFile).toString());
//        stockTracker.clearAllCSV(mainDataFile);//clears entire CSV File
//        stockTracker.blockMaker(mainDataFile,stock);//{Date(0),Time(1),Day(2),Opening Price(3),Closing Price(4),Max Price(5),Min Price(6),Trend(7)}
//        System.out.println(stockTracker.getStockPrice(stock));
//        String[] test={"Nov 06 2022","22:01","Sun","2,991","2,983.30","2991","2950","1"};//legit hammer
//        String[] test={"Nov 06 2022","22:01","Sun","3,008.07","2,983.34","3019","2978","1"};// not a legit hammer
//        stockTracker.hammerDetector(test);

    }
    }

