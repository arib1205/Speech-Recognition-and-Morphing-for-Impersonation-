
import com.darkprograms.speech.microphone.MicrophoneAnalyzer;
import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.Recognizer;
import java.io.*;
import net.sourceforge.javaflacencoder.FLACFileWriter;

public class ambientListening {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        MicrophoneAnalyzer mic = new MicrophoneAnalyzer(FLACFileWriter.FLAC);
        mic.setAudioFile(new File("TestAudio.flac"));
        while (true) {
            mic.open();
            final int THRESHOLD = 8;
            int volume = mic.getAudioVolume();
            boolean isSpeaking = (volume > THRESHOLD);
            if (isSpeaking) {
                try {
                    System.out.println("RECORDING...");
                    mic.captureAudioToFile(mic.getAudioFile());
                    do {
                        Thread.sleep(1000);
                    } while (mic.getAudioVolume() > THRESHOLD);
                    System.out.println("Recording Complete!");
                    System.out.println("Recognizing...");
                    Recognizer rec = new Recognizer(Recognizer.Languages.ENGLISH_INDIA, "AIzaSyBFc0li2oYRZG8d44Yo7-ioPLN6D9cDXeY");
                    GoogleResponse response = rec.getRecognizedDataForFlac(mic.getAudioFile(), 3);
                    display(response);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error Occured");
                } finally {
                    mic.close();
                }
            }
        }
    }

    private static void display(GoogleResponse gr) {
        if (gr.getResponse() == null) {
            System.out.println((String) null);
            return;
        }
        System.out.println("Google Response: " + gr.getResponse());
    }
}
