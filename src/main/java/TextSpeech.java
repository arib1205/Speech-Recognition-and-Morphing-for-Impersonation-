import java.util.Locale;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
public class TextSpeech {
 public static void main(String[] args){
 try
 {
    System.setProperty("freetts.voices",
    "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
    
   Central.registerEngineCentral
    ("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
   Synthesizer  synthesizer =
    Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
   synthesizer.allocate();
   synthesizer.resume();
   synthesizer.speakPlainText("Can you hear me now?", null);
   synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
   synthesizer.deallocate();
  }
   catch(Exception e)
   {
     e.printStackTrace();
   }
 }
}