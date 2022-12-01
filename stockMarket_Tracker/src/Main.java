import java.io.*;
import com.opencsv.CSVWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void writeCSV(String[] Data,String location) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(location, true));
            writer.writeNext(Data);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void clearAllCSV(String location) {
        try {
            FileWriter fileWriter = new FileWriter(location);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    static String getStockPrice() throws IOException {
        URL url=new URL("https://www.google.com/finance/quote/RELIANCE:NSE?sa=X&ved=2ahUKEwiC7q3T35v6AhWzTWwGHc8sDGQQ3ecFegQIGBAY");//link of stock to be tracked from google finance
        URLConnection urlConnection=url.openConnection();
        InputStreamReader inputStreamReader= new InputStreamReader(urlConnection.getInputStream());
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

        String line = bufferedReader.readLine();

        while (line!=null){
            if (line.contains("₹")) {
                int start = line.indexOf('₹');
                return line.substring(start+1,start+9);
            }
            line=bufferedReader.readLine();
        }
        return "ERROR";
    }
    static String getdate() {
        Date date=new Date();
        return String.valueOf(date).substring(4,10)+String.valueOf(date).substring(String.valueOf(date).length()-5,String.valueOf(date).length());
    }
    static String getday() {
        Date date=new Date();
        return String.valueOf(date).substring(0,3);
    }
    static String getTime(){
        Date date=new Date();
        return String.valueOf(date).substring(11,16);
    }
    static String removeCommaFromNumbers(String s){
        StringBuilder str = new StringBuilder();
        int n=s.length();
        for (int i=0;i<n;i++){
            char c=s.charAt(i);
            if (c!=','){
                str.append(c);
            }
        }
        return String.valueOf(str);
    }
    static void blockMaker(String location) throws IOException{
        String[] data=new String[8];//{Date,Time,Day,Opening Price,Closing Price,Max Price,Min Price,Trend}
        data[0]=getdate();
        data[1]=getTime();
        data[2]=getday();
        data[3]=getStockPrice();
        Double max = Double.parseDouble(removeCommaFromNumbers(data[3]));
        Double min = max;
        for (int i=0;i<20;i++){
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Double d = Double.parseDouble(removeCommaFromNumbers(getStockPrice()));
            max=Double.max(max,d);
            min=Double.min(min,d);
        }
        data[4]=getStockPrice();
        data[5]= String.valueOf(max);
        data[6]= String.valueOf(min);
        if (Double.parseDouble(removeCommaFromNumbers(data[3]))>Double.parseDouble(removeCommaFromNumbers(data[4]))){
            data[7]="-1";
        }else{data[7]="1";}
        writeCSV(data,location);
        hammerDetector(data);
    }
    static void hammerDetector(String[] data){}//coming soon

    public static void main(String[] args) throws IOException {
        String mainDataFile ="src/data.csv";
        blockMaker(mainDataFile);

    }
    }

