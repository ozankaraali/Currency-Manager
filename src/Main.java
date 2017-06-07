import jdk.nashorn.api.scripting.URLReader;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


/**
 * Created by ozan on 6/6/17.
 * API used: fixer.io
 */
public class Main {

    public static double getRate(String base, String symbol){
        return getRateTable(base,1).get(symbol);
    }
    public static ArrayList<String> currencies(){
        JSONObject object = null;
        String jsonData = "";
        BufferedReader br = null;
        URL a;
        try {
            a = new URL("http://api.fixer.io/latest");
            try {
                String line;
                br = new BufferedReader(new URLReader(a));
                while ((line = br.readLine()) != null) {
                    jsonData += line + "\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null)
                        br.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            object = new JSONObject(jsonData);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Object[] arr = object.getJSONObject("rates").keySet().toArray();
        String[] arry = Arrays.copyOf(arr, arr.length+1, String[].class);
        arry[arr.length]=object.getString("base");
        ArrayList<String> retn =new ArrayList<>(Arrays.asList(arry));
        Collections.sort(retn);
        return retn;
    }
    public static TreeMap<String,Double> getRateTable(String base,double amountOfMoney){
        JSONObject object = null;
        String jsonData = "";
        BufferedReader br = null;
        URL a;
        try {
            a = new URL("http://api.fixer.io/latest?base="+base);
            try {
                String line;
                br = new BufferedReader(new URLReader(a));
                while ((line = br.readLine()) != null) {
                    jsonData += line + "\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null)
                        br.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            object = new JSONObject(jsonData);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        TreeMap<String, Double> map = new TreeMap<>();
        JSONObject jObject = new JSONObject(object.getJSONObject("rates").toString());
        Iterator<?> keys = jObject.keys();

        while( keys.hasNext() ){
            String key = (String)keys.next();
            Double value = jObject.getDouble(key);
            map.put(key, value*amountOfMoney);

        }
        return map;
    }


}