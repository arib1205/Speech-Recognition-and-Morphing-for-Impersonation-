
import com.darkprograms.speech.microphone.MicrophoneAnalyzer;
import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.Recognizer;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import net.sourceforge.javaflacencoder.FLACFileWriter;

public class Test2 {

    private final static MicrophoneAnalyzer microphone = new MicrophoneAnalyzer(FLACFileWriter.FLAC);

    public static void main(String[] args) throws IOException {
        ambientListening();
    }

    public static void ambientListening() throws IOException {
        File file = new File("test.flac");
        try {
            microphone.captureAudioToFile(file);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
        final int SILENT = microphone.getAudioVolume();
        boolean hasSpoken = false;
        boolean[] speaking = new boolean[10];
        Arrays.fill(speaking, false);
        for (int i = 0; i < 100; i++) {
            for (int x = speaking.length - 1; x > 1; x--) {
                speaking[x] = speaking[x - 1];
            }
            int frequency = microphone.getFrequency();
            int volume = microphone.getAudioVolume();
            speaking[0] = frequency < 255 && volume > SILENT && frequency > 85;
            System.out.println(speaking[0]);
            boolean totalValue = false;
            for (boolean bool : speaking) {
                totalValue = totalValue || bool;
            }
            //if(speaking[0] && speaking[2] && speaking[3] && microphone.getAudioVolume()>10){
            if (totalValue && microphone.getAudioVolume() > 20) {
                hasSpoken = true;
            }
            if (hasSpoken && !totalValue) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            }
        }
        if (hasSpoken) {
            System.out.println("Sample rate is: " + (int) microphone.getAudioFormat().getSampleRate());
            Recognizer rec = new Recognizer(Recognizer.Languages.ENGLISH_INDIA, "AIzaSyBFc0li2oYRZG8d44Yo7-ioPLN6D9cDXeY");
            GoogleResponse out = rec.getRecognizedDataForWave(file);
            System.out.println("Google Response: " + out);
        }
        ambientListening();
    }
}
