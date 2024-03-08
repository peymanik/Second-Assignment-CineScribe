import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import org.json.JSONObject;
public class Actors {
    public static final String API_KEY = "evP76YB/11plpDd/EPY8uw==Jx0wy1d21IZ6APjs";   // TODO --> add your api key about Actors here
    String netWorth;
    Boolean isAlive;

    public Actors(String netWorth, boolean isAlive){
        //TODO --> (Write a proper constructor using the get_from_api functions)
        this.netWorth = netWorth;
        this.isAlive = isAlive;
    }
    @SuppressWarnings({"deprecation"})
    /**
     * Retrieves data for the specified actor.
     * @param name for which Actor should be retrieved
     * @return a string representation of the Actors info or null if an error occurred
     */
    public String getActorData(String name) {
        try {
            URL url = new URL("https://api.api-ninjas.com/v1/celebrity?name="+
                    name.replace(" ", "+")+"&apikey="+API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-Api-Key", API_KEY);
            System.out.println(connection);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                int jsonLength =  response.toString().length();
                return response.toString().substring(1, jsonLength-1);

            } else {
                return "Error: " + connection.getResponseCode() + " " + connection.getResponseMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public double getNetWorthViaApi(String actorsInfoJson){
        //TODO --> (This function must return the "NetWorth")
        JSONObject json = new JSONObject(actorsInfoJson);
        return json.getDouble("net_worth");
    }

    public boolean isAlive(String actorsInfoJson){
        //TODO --> (If your chosen actor is alive it must return true otherwise it must return false)
        JSONObject json = new JSONObject(actorsInfoJson);
        return json.getBoolean("is_alive");
    }

    public String getDateOfDeathViaApi(String actorsInfoJson){
        //TODO --> (If your chosen actor is deceased it must return the date of death)  -->
        if(!isAlive(actorsInfoJson)) {
            JSONObject json = new JSONObject(actorsInfoJson);
            return json.getString("death");
        }
        else{
            return "";
        }
    }

    public String getNationality(String actorsInfoJson){
        JSONObject json = new JSONObject(actorsInfoJson);
        return json.getString("nationality");
    }

}