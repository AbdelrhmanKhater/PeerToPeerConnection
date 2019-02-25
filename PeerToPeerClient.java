/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeerserver;

/**
 *
 * @author Dell
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Dell
 */
public class PeerToPeerClient extends Thread 
{
    Socket soc = null, soc1 = null, soc2 = null;
    BufferedReader in = null, in1 = null;
    BufferedReader scan = null, scan1 = null; 
    PrintWriter out = null, out1 = null;
    String host;
    String service;
    ServerSocket sersoc = null;
    int port, op1, op2, port1;
    public PeerToPeerClient()
    {
        try 
        {
            
            scan = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please, enter host");
            host = scan.readLine();
            System.out.println("Please, enter port of service");
            port = Integer.parseInt(scan.readLine());
            soc = new Socket(host, port);
            System.out.println("Establish Connection");
        } 
        catch (IOException ex) 
        {
            System.out.println(ex.getMessage());
        }
    }
    public void run()
    {
        
        try 
        {
           
            in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            out = new PrintWriter(soc.getOutputStream());
            host = in.readLine();
            port = Integer.parseInt(in.readLine());
            port1 = Integer.parseInt(in.readLine());
            sersoc = new ServerSocket(port);
            new Thread()
            {
                public void run()
                {
                    try {    
                        soc1 = sersoc.accept();
                        out1 = new PrintWriter(soc1.getOutputStream());
                    } catch (IOException ex) {
                        Logger.getLogger(PeerToPeerClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.start();
            soc2 = new Socket("localhost", port1);
            System.out.println("Done");
            in1 = new BufferedReader(new InputStreamReader(soc2.getInputStream()));
            Thread thread1 = new Thread()
            {
                
                public void run()
                {
                    while (true)
                    {
                        try {
                            service = scan.readLine();
                            out1.print(service + "\r\n");
                            out1.flush();
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
            };
            Thread thread2 = new Thread()
            {
                
                public void run()
                {
                    while (true)
                    {
                        try 
                        {
                            System.out.println(in1.readLine());
                        } 
                        catch 
                        (IOException ex)
                        {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
            };
            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();
        } 
        catch (IOException ex) 
        {
            System.out.println(ex.getMessage());
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
        finally
        {
             try 
             {
                 out.close();
                 in.close();
                 soc.close();
             } 
             catch (IOException | NullPointerException ex) 
             {
                 System.out.println(ex.getMessage());
             }
        }
    }
    public static void main(String args[])
    {
        PeerToPeerClient c = new PeerToPeerClient();
        c.start();
    }
}