package th.co.truecorp.vcare.http.servlet.test.poc;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

import org.json.JSONObject;

 
public class HttpURLConnectionExample {
 
 
	public static void main(String[] args) throws Exception {
		String    Url="http://www.itruemart.com/ajax/product/check-stock-by-variant?option_pkey%5B0%5D=21148921143375&product_pkey=2822822072828";


		Url="http://www.itruemart.com/th/ajax/search2?q=Mini+Photo+Album+CAIUL+for+Instax+Mini+Film";
		//System.out.println(getUrlContents(Url));
		//Url="http://www.itruemart.com/th/ajax/search2?q=%20Mini%20Photo%20Album%20CAIUL%20for%20Instax%20Mini%20Film";
		//String  Param="?collection=Everyday WOW&q=Mini Photo Album CAIUL for Instax Mini Film&per_page=12&page=1";
		//String Param2="http://www.itruemart.com/th/ajax/search2?collection=Everyday%20WOW&q=%20Mini%20Photo%20Album%20CAIUL%20for%20Instax%20Mini%20Film&per_page=12&page=1";
		//String pa="Mini Photo Album CAIUL for Instax Mini Film";
		//pa="q="+URLEncoder.encode(pa, "UTF-8");
		//System.out.println(pa);
		for (int i = 2; i > 1; i++) {
			Date d =new Date();
			boolean check1=false;
			SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss",Locale.US); 
			String x =getUrlContents(Url);//excutePost(Url, pa);
			//System.out.println("date :"+dt.format(d) +" =>"+x);
			if(x!=null){
				JSONObject jx = new JSONObject(x);
				JSONObject data=jx.getJSONArray("data").getJSONObject(0);
				System.out.println(dt.format(d) +" > "+data.getString("sell_price")+"  "+data.getString("normal_price")+" "+ data.getString("special_price")+" "+data.getString("product_name_th")  );
				check1=("1".equals(data.getString("sell_price"))||"1".equals(data.getString("special_price")));
				//check1=true;
			}else {
				System.out.println("Error Call");
				i=0;
				break;
			}


			if(check1){
				PlayMidiAudio();
				i=0;
				break;
			}
			Thread.sleep(2000);
		}


		System.out.println("End");
	}

	private static String getUrlContents(String theUrl)
	  {
	    StringBuilder content = new StringBuilder();
	 
	    // many of these calls can throw exceptions, so i've just
	    // wrapped them all in one try/catch statement.
	    try
	    {
	      // create a url object
	      URL url = new URL(theUrl);
	 
	      // create a urlconnection object
	      URLConnection urlConnection = url.openConnection();
	 
	      // wrap the urlconnection in a bufferedreader
	      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	 
	      String line;
	 
	      // read from the urlconnection via the bufferedreader
	      while ((line = bufferedReader.readLine()) != null)
	      {
	        content.append(line + "\n");
	      }
	      bufferedReader.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    return content.toString();
	  }
	
	public static String excutePost(String targetURL, String urlParameters)
	  {
	    URL url;
	    HttpURLConnection connection = null;  
	    try {
	    	//Proxy
	    	/*Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy", 80));
	    	Authenticator authenticator = new Authenticator() {

	            public PasswordAuthentication getPasswordAuthentication() {
	                return (new PasswordAuthentication("varako2","Admin@452".toCharArray()));
	            }
	        };
	        Authenticator.setDefault(authenticator);
	          connection = (HttpURLConnection)url.openConnection(proxy);
	        */
	        
	        
	      //Create connection
	      url = new URL(targetURL);
	     
	      connection = (HttpURLConnection)url.openConnection();

	      connection.setRequestMethod("POST");
	      //connection.setRequestProperty(key, value);
	      //connection.setRequestProperty("User-Agent", "Mozilla/5.0");
	      connection.setRequestProperty("Content-Type",  "application/x-www-form-urlencoded");
	      //connection.setRequestProperty("Content-Length", "" +  Integer.toString(urlParameters.getBytes().length));
	      connection.setRequestProperty("Content-Language", "en-US");  
	      connection.setRequestProperty("charset", "utf-8");
	      connection.setUseCaches (false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);
	      connection.setInstanceFollowRedirects(false); 
	      
	     

	      DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
	      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr));
	      writer.write(urlParameters);
	      writer.close();
	      wr.flush();
	      wr.close();
	      System.out.println( connection.getResponseCode() + " " + connection.getResponseMessage() );  
 Permission p = connection.getPermission();  
	      
	      if( p != null ){  
	      System.out.println( "Permission: " + p.getActions() + " " + p.getName() );
	      }
	      //Send request
	     /* DataOutputStream wr = new DataOutputStream ( connection.getOutputStream ());
	      wr.writeBytes (urlParameters);
	      wr.flush ();
	      wr.close ();*/

	      //Get Response	
	      InputStream is = connection.getInputStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	      String line;
	      StringBuffer response = new StringBuffer(); 
	      while((line = rd.readLine()) != null) {
	        response.append(line);
	        response.append('\r');
	      }
	      rd.close();
	      return response.toString();

	    } catch (Exception e) {

	      e.printStackTrace();
	      return null;

	    } finally {

	      if(connection != null) {
	        connection.disconnect(); 
	      }
	    }
	  }
	
	
	public static void PlayMidiAudio(){
		Sequencer sequencer;
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			File f =new File("C:/windows/media/town.mid");
			FileInputStream fs = new FileInputStream(f);
			InputStream is = new BufferedInputStream(fs);
			sequencer.setSequence(is);
			sequencer.start();
			Thread.sleep(10000);
			sequencer.stop();
			sequencer.close();

		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
