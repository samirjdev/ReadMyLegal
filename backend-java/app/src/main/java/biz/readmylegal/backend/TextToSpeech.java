package biz.readmylegal.backend;


import java.io.FileOutputStream;
import java.io.IOException;

import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import com.google.protobuf.Duration;
import com.google.protobuf.TextFormat;
import java.io.FileOutputStream;
import java.io.OutputStream;








public class TextToSpeech {
   /*

    public static void ShorttextToSpeech(String finalText) throws IOException{

 
       Version 1 Long Test.
    TextToSpeechClient client = TextToSpeechClient.create();
    //Call the synthesizeAudio() method on the client object.


    SynthesizeSpeechRequest request = new SynthesizeSpeechRequest();

    request.setText("Hello, world!");
    request.setOutputFormat("mp3");
    request.setAudioEncoding("LINEAR16");
    request.setSampleRateHertz(16000);
    SynthesizeSpeechResponse response = client.synthesizeSpeech(request);

    // Get the synthesized audio.
    byte[] audioContent = response.getAudioContent();

    FileOutputStream outputStream = new FileOutputStream("output.mp3");
    outputStream.write(audioContent);
    outputStream.close();
 

        //short text.
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            // Set the text input to be synthesized
            SynthesisInput input = SynthesisInput.newBuilder().setText(finalText).build();
      
            // Build the voice request, select the language code ("en-US") and the ssml voice gender
            // ("neutral")
            
            VoiceSelectionParams voice =
                VoiceSelectionParams.newBuilder()
                    .setLanguageCode("en-US")
                    .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                    .build();
      
            // Select the type of audio file you want returned
            AudioConfig audioConfig =
                AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.MP3).build();
      
            // Perform the text-to-speech request on the text input with the selected voice parameters and
            // audio file type
            SynthesizeSpeechResponse response =
                textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
      
            // Get the audio contents from the response
            ByteString audioContents = response.getAudioContent();
      
            // Write the response to the output file.
            try (OutputStream out = new FileOutputStream("output.mp3")) {
              out.write(audioContents.toByteArray());
              System.out.println("Audio content written to file \"output.mp3\"");
            }
          }


    }
*/







    public static void LongtextToSpeech(String finalText) throws Exception{
            try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            // Specify the text to be synthesized
            //String inputText = "This is a long text to be converted to speech.";

            // Configure the synthesis request
            SynthesisInput input = SynthesisInput.newBuilder().setText(finalText).build();
            VoiceSelectionParams voice =
                VoiceSelectionParams.newBuilder()
                    .setLanguageCode("en-US")
                    .setName("en-US-Wavenet-D")
                    .build();
            AudioConfig audioConfig =
                AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.LINEAR16)
                    .setSpeakingRate(1.0)
                    .setPitch(0.0)
                    .setVolumeGainDb(0.0)
                    .setSampleRateHertz(16000)
                    .build();

            // Send the request to the Text-to-Speech API
            SynthesizeSpeechResponse response =
                textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

            // Save the synthesized audio to a file
            try (OutputStream out = new FileOutputStream("output.wav")) {
                ByteString audioContents = response.getAudioContent();
                out.write(audioContents.toByteArray());
            }
        }



    }


    
}
