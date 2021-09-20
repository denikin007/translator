package com.translator;

import com.translator.service.TranslatorService;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.springframework.util.StreamUtils;

public class TranslatorServiceTest {

    @Test
    void copyFileReverse(){
        String inURL="src/main/resources/Original.txt";
        String outURL="src/main/resources/estrofasEnOrdenInverso.txt";

        TranslatorService translator = new TranslatorService();
        try{
            translator.copyInputStreamToOutputStreamReserve(inURL,outURL);
        }catch (IOException e){
            System.out.println(e);
        }
    }
    @Test
    public void countStanzas() throws IOException {
        String inURL="src/main/resources/estrofasEnOrdenInverso.txt";
        InputStream in = new FileInputStream(inURL);
        String inputFileContent = getStringFromInputStream(in);
        TranslatorService translator = new TranslatorService();
        int resultado = translator.countStanzas(inputFileContent);
        assertEquals(resultado,16);
    }

    @Test
    public void saveFileIdentifyWordsRepeated() throws IOException{
        String inURL="src/main/resources/estrofasEnOrdenInverso.txt";
        String outURL="src/main/resources/statistics.txt";
        InputStream in = new FileInputStream(inURL);
        String inputFileContent = getStringFromInputStream(in);
        TranslatorService translator = new TranslatorService();
        translator.saveFileIdentifyWordsRepeated(inputFileContent,outURL);
    }

    @Test
    public void replaceToWordFile() throws IOException{
        String inURL="src/main/resources/estrofasEnOrdenInverso.txt";
        String outURL="src/main/resources/finaloutput.txt";
        InputStream in = new FileInputStream(inURL);
        String inputFileContent = getStringFromInputStream(in);
        TranslatorService translator = new TranslatorService();
        translator.saveFileReplaceToWord(inputFileContent,"Denikin",outURL);
    }

    private String getStringFromInputStream(InputStream input) throws IOException{
        StringWriter writer = new StringWriter();
        IOUtils.copy(input, writer,"UTF-8");
        return writer.toString();
    }
}
