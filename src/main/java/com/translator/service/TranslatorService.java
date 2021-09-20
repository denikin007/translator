package com.translator.service;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Service
public class TranslatorService {
    public void copyInputStreamToOutputStreamReserve(String inputFileURL,String outputFileURL) throws IOException {
        File outputFile = new File(outputFileURL);
        InputStream in = new FileInputStream(inputFileURL);
        OutputStream out = new FileOutputStream(outputFile);

        String inputFileContent = getStringFromInputStream(in);
        String invertida="";
        //System.out.println(inputFileContent);

        invertida = reverseStringToInputStream(inputFileContent);

        //countStanzas(inputFileContent);
        //replaceToWordFile(inputFileContent,"Denikin","src/main/resources/finaloutput.txt");
        //saveFileIdentifyWordsRepeated(inputFileContent,"src/main/resources/statistics.txt");
        StreamUtils.copy(invertida, StandardCharsets.UTF_8, out);
    }

    private String getStringFromInputStream(InputStream input) throws IOException{
        StringWriter writer = new StringWriter();
        IOUtils.copy(input, writer,"UTF-8");
        return writer.toString();
    }

    private String reverseStringToInputStream(String texto){
        StringBuilder estrofas = new StringBuilder();
        Scanner in = new Scanner(texto);
        String invertido = "";
        while(in.hasNextLine()){
            String line = in.nextLine();
            if(line.equals("")){
                estrofas.append(invertido);
                invertido = "";
                estrofas.append("\n");
            }else{
                invertido = line +"\n"+ invertido;
            }
        }
        //System.out.println(estrofas.toString());
        return estrofas.toString();
    }

    public int countStanzas(String texto){
        StringBuilder estrofas = new StringBuilder();
        Scanner in = new Scanner(texto);
        int resultado = 0;
        while(in.hasNextLine()){
            String line = in.nextLine();
            if(line.equals("")){
                resultado++;
            }
        }
        //System.out.println(resultado);
        return resultado;
    }

    public void saveFileIdentifyWordsRepeated(String texto,String outFile) throws IOException{
        File outputFile = new File(outFile);
        OutputStream out = new FileOutputStream(outputFile);
        String resultado ="";
        Map<String,String> valoresRepetidos = getWordRepeatedAndCount(texto);
        resultado = "La paralabra repetida es: " + valoresRepetidos.get("word") +" , "+" con una cantidad de: " + valoresRepetidos.get("count");
        StreamUtils.copy(resultado, StandardCharsets.UTF_8, out);
    }

    public void saveFileReplaceToWord(String texto, String wordNew, String outFile) throws IOException{
        File outputFile = new File(outFile);
        OutputStream out = new FileOutputStream(outputFile);
        Map<String,String> valores = getWordRepeatedAndCount(texto);
        String nuevoTexto = replaceToWordRepeated(texto,wordNew,valores.get("word"));
        StreamUtils.copy(nuevoTexto, StandardCharsets.UTF_8, out);
    }

    private String replaceToWordRepeated(String texto,String wordNew, String wordOld){
        return texto.replace(wordOld,wordNew);
    }

    private Map<String,String> getWordRepeatedAndCount(String texto){
        Map<String,Integer> wordsMap = new HashMap<>();
        Map<String,String> repeated = new HashMap<>();
        String[] wordsSplit = texto.split("[\\s,]");
        Integer countMax=0;
        String wordsMax = "";
        for(int i=0; i<wordsSplit.length;i++){
            Integer oldCount = wordsMap.get(wordsSplit[i]);
            String word = wordsSplit[i];
            if(oldCount != null && !word.equals("")){
                wordsMap.put(wordsSplit[i],oldCount+1);
                if(oldCount>countMax && !word.equals("")){
                    countMax = oldCount;
                    wordsMax = wordsSplit[i];
                }
            }else if(!word.equals("")){
                wordsMap.put(wordsSplit[i],1);
            }
        }
        repeated.put("word",wordsMax);
        repeated.put("count",Integer.toString(countMax));
        return repeated;
    }
}
