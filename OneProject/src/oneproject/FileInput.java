/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package oneproject;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 
 */



//public class FileUtility
//{
//    public static Object readObjectFromFile(String path)
//    {
//        try {
//               
//            FileInputStream fin=new FileInputStream(new File(path));
//            
//            ObjectInputStream in=new ObjectInputStream(fin);
//         
//            Object result= in.readObject();
//            System.err.println("rrrrrrrrr"+result);
//            in.close();
//            return result;
//           
//        } catch (FileNotFoundException ex) {
//           return null;
//        } catch (IOException ex) {
//              return null;
//        } catch (ClassNotFoundException ex) {
//             return null;
//        }
//    
//    }
    
//    public static void writeObjectToFile(String path,Company company)
//    {
//        try {
//            FileOutputStream fo=new FileOutputStream(new File(path));
//            ObjectOutputStream out=new ObjectOutputStream(fo);
//            out.writeObject(company);
//            out.close();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(FileUtility.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(FileUtility.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    
//    }
    
//}


//package MyProject;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This program reads a text file line by line and print to the console. It uses
 * FileOutputStream to read the file.
 * 
 */
public class FileInput {
 public static String d = null;
  public static String readObjectFromFile(String path) throws IOException {

    File file = new File("C:\\MyFile.txt");
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    DataInputStream dis = null;

    try {
      fis = new FileInputStream(file);

      // Here BufferedInputStream is added for fast reading.
      bis = new BufferedInputStream(fis);
      dis = new DataInputStream(bis);

      // dis.available() returns 0 if the file does not have more lines.
      while (dis.available() != 0) {

          // this statement reads the line from the file and print it to
          // the console.
          d = dis.readLine();
       // System.out.println(dis.readLine());
      }

      // dispose all the resources after using them.
      fis.close();
      bis.close();
      dis.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
     
     
      return d;
  }
  

  public static void writeObjectFromFile(String data) throws IOException {

    FileOutputStream fos; 
    DataOutputStream dos;

    try {

      File file= new File("C:\\MyFileDownload.txt");
      fos = new FileOutputStream(file);
      dos=new DataOutputStream(fos);
      dos.writeInt(2333);
      dos.writeChars(data);

    } catch (IOException e) {
      e.printStackTrace();
    }

  
  
  
  }}