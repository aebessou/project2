
package com.mycompany.mavenproject1;
import java.net.http.HttpRequest.BodyPublishers;

/**
 *
 * @author aebessou
 */
public class Mavenproject1 {
     public static void main(String[] args) throws Exception {
        Transcript transcript = new Transcript();
        transcript.setAudio_url("https://bit.ly/3yxKEIY");
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(transcript);
        
        System.out.println(jsonRequest);

        HttpRequest postRequest = HttpRequest.newBuilder()
            .uri(newURI("https://api.assemblyai.com/v2/transcript/" + transcript.getId())
            .header("Authorization", 29f3bf3416c64d6488a394ff81ee5fda)
            .POST(BodyPublishers.ofString(jsonRequest))
            .build();
        
            
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> postResponse = httpClient.send(postRequest, BodyHandlers.ofString());

        System.out.println(postResponse.body());

        transcript = gson.fromJson(postResponse.body(), Transcript.class);

        System.out.println(transcript.getId());

       
           
        HttpResponse<String> getResponse = httpClient.send(getRequest, BodyHandlers.ofString());
        transcript = gson.fromJson(postResponse.body(), Transcript.class);

        while (true) {
            HttpResponse<String> getResponse = httpClient.send(getRequest, BodyHandlers.ofString());
            transcript = gson.fromJson(getResponse.body(), Transcript.class);
            System.out.println(transcript.getStatus());

            if ("completed".equals(transcript.getStatus()) || "error".equals(transcript.getStatus())) {
                break;
            }
            Thread.sleep(1000);
        }
        System.out.println("Transcription completed!");
        System.out.println(transcript.getText());
    }
    
}
