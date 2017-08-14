import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by natali on 8/14/2017.
 */
public class TestFacebookApi {
    public static void main(String[] args) throws IOException {

        String[] urls = new String[]{
                "joe.colella.397?fref=grp_mmbr_list"
        };

        for(int i=0;i< urls.length;i++)
        {
            String result = sendGetRequest(urls[i]);
            //Lets see what we got from API
            System.out.println(result);
        }
    }


    public static String sendGetRequest(String userString) throws IOException{
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try
        {
            //Define a HttpGet request; You can choose between HttpPost, HttpDelete or HttpPut also.
            //Choice depends on type of method you will be invoking.
            HttpGet getRequest = new HttpGet("https://www.7xter.com/ads/findmyid/index.php?username="+userString+".html");

            //Set the API media type in http accept header
            getRequest.addHeader("accept", "application/xml");

            //Send the request; It will immediately return the response in HttpResponse object
            HttpResponse response = httpClient.execute(getRequest);

            //verify the valid error code first
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200)
            {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }

            //Now pull back the response object
            HttpEntity httpEntity = response.getEntity();
            String apiOutput = EntityUtils.toString(httpEntity);


            return apiOutput;
        }
        finally
        {
            //Important: Close the connect
            httpClient.getConnectionManager().shutdown();
        }
    }

}


