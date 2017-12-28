/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofx2csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author igora
 */
public class Conversor {
    public static void conversor(String file) throws FileNotFoundException, IOException, 
                                                     ParserConfigurationException, SAXException{
        
        String nomeArquivo = file;
        String baseArquivo = nomeArquivo.substring(0, nomeArquivo.length() -4);
        
        BufferedReader br = new BufferedReader(new FileReader(nomeArquivo));
        String  str, sufixo;
        
        PrintWriter writer = new PrintWriter(baseArquivo + ".xml", "UTF-8");
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        
        while(( str = br.readLine())!= null){
            if(str.trim().length() > 0){
                if(str.trim().substring(0, 1).equals("<")){
                    
                    if(!str.trim().endsWith(">")){
                        sufixo = "</" + str.trim().substring(1, str.trim().indexOf(">")+1);                        
                        str = str + sufixo;
                    }
                    System.out.println(str);
                    writer.println(str);
                }                          
            }
        }
        
        writer.close();
        br.close();
        
        File inputXML = new File(baseArquivo + ".xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputXML);
        doc.getDocumentElement().normalize();
        
        NodeList nList = doc.getElementsByTagName("STMTTRN");

        PrintWriter writerCSV = new PrintWriter(baseArquivo + ".csv", "ISO-8859-1");
        writerCSV.println("tipo;data;valor;ident;memo");


        String tipo, data, ident, memo, valor;
        
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               
               tipo = eElement.getElementsByTagName("TRNTYPE").item(0).getTextContent();
               data = eElement.getElementsByTagName("DTPOSTED").item(0).getTextContent().substring(0, 8);
               data = data.substring(6, 8) + "/" + data.substring(4, 6) + "/" + data.substring(0, 4);
               valor = eElement.getElementsByTagName("TRNAMT").item(0).getTextContent().replace(".", ",");
               ident = eElement.getElementsByTagName("FITID").item(0).getTextContent();
               memo = eElement.getElementsByTagName("MEMO").item(0).getTextContent();
               
               writerCSV.println(tipo + ";" + data + ";" + valor + ";" + ident + ";" + memo);
            }
         }
        writerCSV.close();
        inputXML.delete();
        
    }
    
}
