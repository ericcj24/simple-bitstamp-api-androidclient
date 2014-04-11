package edu.illinois.jchen93.bitstampapiandroid;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TransactionAPI{
	//{"date": "1395551812", "tid": 4151239, "price": "564.23", "amount": "0.05000000"}
	
	
	private final static String TAG="debug info(Trnasaction)";
	
	
    public static Vector<Vector<Double>> HttpGetTransactions() throws Exception {
    	Vector<Vector<Double>> v = new Vector<Vector<Double>>();

        String path = "https://www.bitstamp.net/api/transactions/";
        URL url=new URL(path);
        HttpURLConnection c=(HttpURLConnection)url.openConnection();
        c.setRequestMethod("GET");
    	c.setReadTimeout(15000);
        try {
        	
        	c.connect();
            
            ObjectMapper mapper = new ObjectMapper();
            ArrayList<Transactions> array = mapper.readValue(c.getInputStream(), new TypeReference<ArrayList<Transactions>>() { });

            /*for(int i=0; i<array.size(); i++){
            	long dateLong = Long.parseLong(array.get(i).date)*1000;
            	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            	String formattedDate =  sdf.format(dateLong);
            	System.out.println("date: "+formattedDate+ " tid " + array.get(i).tid + " price "+array.get(i).price+" amount "+array.get(i).amount);
            }*/
            
            
            Vector<Double> x = new Vector<Double>();
            Vector<Double> y = new Vector<Double>();
            for(int i=0; i<array.size(); i++){
            	x.add(Double.parseDouble(array.get(i).getDate()));
            	y.add(Double.parseDouble(array.get(i).getPrice()));
            }

            v.add(x);
            v.add(y);
   
            }catch(java.net.ConnectException e){
            	Log.e(TAG, e.toString());
            	
            }catch(java.net.UnknownHostException e){
            	Log.e(TAG, e.toString());
            }catch (Exception e) {
				// TODO Auto-generated catch block
            	Log.e(TAG, e.toString());
			}finally{
				c.disconnect();
			}
        return v;
    }

}