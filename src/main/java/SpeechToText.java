import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.GSpeechDuplex;
import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.Recognizer;
import java.io.File;
import java.nio.file.Files;
import net.sourceforge.javaflacencoder.FLACFileWriter;

public class SpeechToText {
public static void main(String[] args) {
    Microphone mic = new Microphone(FLACFileWriter.FLAC);
    File file = new File("testfile2.flac");//Name your file whatever you want
    try {
        mic.captureAudioToFile(file);
    } catch (Exception ex) {//Microphone not available or some other error.
        System.out.println("ERROR: Microphone is not availible.");
        ex.printStackTrace();
        //TODO Add your error Handling Here
    }
    /* User records the voice here. Microphone starts a separate thread so do whatever you want
     * in the mean time. Show a recording icon or whatever.
     */
    try {
        System.out.println("Recording...");
        Thread.sleep(5000);//In our case, we'll just wait 5 seconds.
                    mic.close();
    } catch (InterruptedException ex) {
        // TODO Auto-generated catch block
        ex.printStackTrace();
    }

    mic.close();//Ends recording and frees the resources
    System.out.println("Recording stopped.");

    Recognizer recognizer = new Recognizer(Recognizer.Languages.ENGLISH_INDIA, "AIzaSyDL3_P0vtw7LmCmh-ESpLTlu1Y_kMy3piE"); //Specify your language here.
    //Although auto-detect is avalible, it is recommended you select your region for added accuracy.
    try {
        int maxNumOfResponses = 4;
        GoogleResponse response = recognizer.getRecognizedDataForFlac(file, maxNumOfResponses, (int)mic.getAudioFormat().getSampleRate());
        System.out.println("Google Response: " + response.getResponse());
        System.out.println("Google is " + Double.parseDouble(response.getConfidence())*100 + "% confident in"
                + " the reply");
        //System.out.println("Other Possible responses are: ");
        for(String s: response.getOtherPossibleResponses()){
            System.out.println("\t" + s);
        }
    } catch (Exception ex) {
        // TODO Handle how to respond if Google cannot be contacted
        System.out.println("ERROR: Google cannot be contacted");
        ex.printStackTrace();
    }

    file.deleteOnExit();//Deletes the file as it is no longer necessary.

}

}