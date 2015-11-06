package com.test.op;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import cpdetector.CharsetPrinter;

public class FileCode {
	// Input file name
    private String inputFileName;
 
    // The encoding when reading input file
    private String inputFileCode;
 
    // Output file name
    private String outputFileName;
 
    // The encoding when write to a file
    private String outputFileCode;
//    public static void main(String[] args) {
//        
//        // Convert file's encoding to 'utf8'.
//        write("G:\\test\\history\\HC3ABW902598\\com.film.news.mobile.test-2014-12-16-09-48-20-680/TestTicket_Movie_004.txt", "utf-8",
//                read("G:\\test\\history\\HC3ABW902598\\com.film.news.mobile.test-2014-12-15-16-57-13-107/TestTicket_Movie_005.txt", 
//                    guessEncoding("G:\\test\\history\\HC3ABW902598\\com.film.news.mobile.test-2014-12-15-16-57-13-107/TestTicket_Movie_005.txt")));
//         
//         output new encoding
//        System.out.println(guessEncoding("G:\\test\\history\\HC3ABW902598\\com.film.news.mobile.test-2014-12-16-09-48-20-680/TestTicket_Movie_004.txt"));
//         
//      
//    }
    /**
     * @describe Guess the encoding of the file specified by filename 
     * @param filename
     * @return the encoding of the file
     */
    public static String guessEncoding(String filename) {
        try {
            CharsetPrinter charsetPrinter = new CharsetPrinter();
            String encode = charsetPrinter.guessEncoding(new File(filename));
            return encode;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
 
    
 
    /**
     * @describe write str to fileName with specified encode
     * @param fileName
     * @param encoding
     * @param str
     * @return null
     */
    public static void write(String fileName, String encoding, String str) {
        try {
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName), encoding));
            out.write(str);
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
 
    public String getInputFileName() {
        return inputFileName;
    }
 
    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }
 
    public String getInputFileCode() {
        return inputFileCode;
    }
 
    public void setInputFileCode(String inputFileCode) {
        this.inputFileCode = inputFileCode;
    }
 
    public String getOutputFileName() {
        return outputFileName;
    }
 
    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }
 
    public String getOutputFileCode() {
        return outputFileCode;
    }
 
    public void setOutputFileCode(String outputFileCode) {
        this.outputFileCode = outputFileCode;
    }
}
