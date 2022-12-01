import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.util.concurrent.TimeUnit;
public class StockTracker {
    public void sortIntoDAYS(String target){
        ArrayList<String> list = readDataCSV(target);
        ArrayList<String> temp = new ArrayList<>();
        int s = list.size();
        String str= String.valueOf(list.get(0));
        for (int i=0;i<s;i=i+8){
            if (str.equals(list.get(i))){
                temp.add(list.get(i));
                temp.add(list.get(i+1));
                temp.add(list.get(i+2));
                temp.add(list.get(i+3));
                temp.add(list.get(i+4));
                temp.add(list.get(i+5));
                temp.add(list.get(i+6));
                temp.add(list.get(i+7));
            }else {
                str=list.get(i);
                compressToRow(temp,"src/DayData.csv");
                temp.clear();
                temp.add(list.get(i));
                temp.add(list.get(i+1));
                temp.add(list.get(i+2));
                temp.add(list.get(i+3));
                temp.add(list.get(i+4));
                temp.add(list.get(i+5));
                temp.add(list.get(i+6));
                temp.add(list.get(i+7));
            }
        }
        if (temp.size()>1){
            compressToRow(temp,"src/DayData.csv");
            temp.clear();}
    }
    public void sortIntoHOURS(String target){
        ArrayList<String> list = readDataCSV(target);
        ArrayList<String> temp = new ArrayList<>();
        int s = list.size();
        String str= (list.get(1).substring(0,2)),date=list.get(0);
        for (int i=0;i<s;i=i+8){
            if (str.equals(list.get(i+1).substring(0,2))&&date.equals(list.get(i))){
                temp.add(list.get(i));
                temp.add(str+"--");
                temp.add(list.get(i+2));
                temp.add(list.get(i+3));
                temp.add(list.get(i+4));
                temp.add(list.get(i+5));
                temp.add(list.get(i+6));
                temp.add(list.get(i+7));
            }else {
                str=list.get(i+1).substring(0,2);
                date=list.get(i);
                compressToRowHourly(temp,"src/HourData.csv");
                temp.clear();
                temp.add(list.get(i));
                temp.add(str+"--");
                temp.add(list.get(i+2));
                temp.add(list.get(i+3));
                temp.add(list.get(i+4));
                temp.add(list.get(i+5));
                temp.add(list.get(i+6));
                temp.add(list.get(i+7));
            }
        }
        if (temp.size()>1){
            compressToRowHourly(temp,"src/HourData.csv");
            temp.clear();}
    }
    public void compressToRow(ArrayList<String> list,String location){
        String date= String.valueOf(list.get(0)),day= String.valueOf(list.get(2));
        Double open = Double.valueOf(removeCommaFromNumbers(String.valueOf(list.get(3)))), close,
                max = Double.valueOf(list.get(5)),
                min = Double.valueOf(list.get(6));
        int i=3,s=list.size();
        close = Double.valueOf(removeCommaFromNumbers(String.valueOf(list.get(s-4))));
        while (i+3<list.size()){
            if (Double.parseDouble(list.get(i+2))>max){
                max = Double.valueOf(list.get(i+2));
            }
            if (Double.parseDouble(list.get(i+3))<min){
                min = Double.valueOf(list.get(i+3));
            }
            i=i+8;
        }
        String[] returnData = {date,day, String.valueOf(open), String.valueOf(close)
                , String.valueOf(max), String.valueOf(min)};
        writeCSV(returnData,location);
    }
    public void compressToRowHourly(ArrayList<String> list,String location){
        String date= list.get(0),hour=list.get(1),day= list.get(2);
        Double open = Double.valueOf(removeCommaFromNumbers(list.get(3))), close,
                max = Double.valueOf(list.get(5)),
                min = Double.valueOf(list.get(6));
        int i=3,s=list.size();
        close = Double.valueOf(removeCommaFromNumbers(String.valueOf(list.get(s-4))));
        while (i+3<list.size()){
            if (Double.parseDouble(list.get(i+2))>max){
                max = Double.valueOf(list.get(i+2));
            }
            if (Double.parseDouble(list.get(i+3))<min){
                min = Double.valueOf(list.get(i+3));
            }
            i=i+8;
        }
        String[] returnData = {date,hour,day, String.valueOf(open), String.valueOf(close)
                , String.valueOf(max), String.valueOf(min)};
        writeCSV(returnData,location);
    }
    public void writeCSV(String[] Data,String location) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(location, true));
            writer.writeNext(Data);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void readCSV(String file){
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList readDataCSV(String file){
        ArrayList<String> list=new ArrayList<>();
        try {

            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {
                    list.add(cell);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public void clearAllCSV(String location) {
        try {
            FileWriter fileWriter = new FileWriter(location);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String removeUnwantedChars(String data){
        StringBuilder str=new StringBuilder();
        int k=data.length();
        for (int i=0;i<k;i++){
            int n=(int)data.charAt(i);
            if ((n>=48&&n<=57)||n=='.'||n==','){
                str.append(data.charAt(i));
            }
        }
        return String.valueOf(str);
    }
    public String getStockPrice(String stock) throws IOException {
        URL url=new URL(stock);
        URLConnection urlConnection=url.openConnection();
        InputStreamReader inputStreamReader= new InputStreamReader(urlConnection.getInputStream());
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

        String line = bufferedReader.readLine();

        while (line!=null){
            if (line.contains("₹")) {
                int start = line.indexOf('₹');
                return removeUnwantedChars(line.substring(start+1,start+9));
            }
            line=bufferedReader.readLine();
        }
        return "ERROR";
    }
    public String getdate() {
        Date date=new Date();
        return String.valueOf(date).substring(4,10)+String.valueOf(date).substring(String.valueOf(date).length()-5,String.valueOf(date).length());
    }
    public String getday() {
        Date date=new Date();
        return String.valueOf(date).substring(0,3);
    }
    public String getTime(){
        Date date=new Date();
        return String.valueOf(date).substring(11,16);
    }
    public String removeCommaFromNumbers(String s){
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
    public void blockMaker(String location,String stock) throws IOException{
        String[] data=new String[8];//{Date(0),Time(1),Day(2),Opening Price(3),Closing Price(4),Max Price(5),Min Price(6),Trend(7)}
        data[0]=getdate();
        data[1]=getTime();
        data[2]=getday();
        data[3]=getStockPrice(stock);
        Double max = Double.parseDouble(removeCommaFromNumbers(data[3]));
        Double min = max;
        for (int i=0;i<20;i++){
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Double d = Double.parseDouble(removeCommaFromNumbers(getStockPrice(stock)));
            max=Double.max(max,d);
            min=Double.min(min,d);
        }
        data[4]=getStockPrice(stock);
        data[5]= String.valueOf(max);
        data[6]= String.valueOf(min);
        if (Double.parseDouble(removeCommaFromNumbers(data[3]))>Double.parseDouble(removeCommaFromNumbers(data[4]))){
            data[7]="-1";
        }else{data[7]="1";}
        writeCSV(data,location);
    }
    public void hammerDetector(String[] data){
        double open = Double.parseDouble(removeCommaFromNumbers(data[3])),
                close = Double.parseDouble(removeCommaFromNumbers(data[4])),
                max=Double.parseDouble(data[5]),min=Double.parseDouble(data[6]);
        double handle = max-min;
        double block = Math.abs(open-close);
        int i=0;
        if (((open+close)/2>0.6*max)||((open+close)/2<1.4*min)){
            if (block>handle/10&&block<handle/4){
                i++;
                System.out.println("FOUND HAMMER");
                writeCSV(data,"src/hammers.csv");
            }
        }
        if (i==0){
            System.out.println("NOT HAMMER");
        }
    }
}
