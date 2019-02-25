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

/**
 *
 * @author Dell
 */
public class PeerToPeerServer extends Thread
{
    public static int cnt = 0, port = 8000;
    public static BufferedReader ins[];
    public static PrintWriter outs[];
    ServerSocket server = null;
    Socket socs[];
    int op1, op2;
    public PeerToPeerServer(int port)
    {
        try 
        {
            socs = new Socket[1000];
            ins = new BufferedReader[1000];
            outs = new PrintWriter[1000];
            server = new ServerSocket(port);
            //server.setSoTimeout(10000000);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    public void run()
    {
            System.out.println("Starting server!!");
            
            
            while (true)
            {
                try {
                    socs[cnt] = server.accept();
                    ins[cnt] = new BufferedReader(new InputStreamReader(socs[cnt].getInputStream()));
                    outs[cnt] = new PrintWriter(socs[cnt].getOutputStream(), true);       
                    System.out.println("Done");
                    if (cnt % 2 == 1)
                    {
                        System.out.println(socs[cnt - 1].getRemoteSocketAddress().toString());
                        System.out.println(socs[cnt].getRemoteSocketAddress().toString());
                        outs[cnt].print(socs[cnt - 1].getRemoteSocketAddress().toString() + "\r\n");                        
                        outs[cnt].flush();
                        outs[cnt].print(port + "\r\n");
                        outs[cnt].flush();
                        outs[cnt].print(port + 50 + "\r\n");
                        outs[cnt].flush();
                        outs[cnt - 1].print(socs[cnt].getRemoteSocketAddress().toString() + "\r\n");
                        outs[cnt - 1].flush();
                        port += 50;
                        outs[cnt - 1].print(port + "\r\n");
                        outs[cnt - 1].flush();
                        outs[cnt - 1].print(port - 50 + "\r\n");
                        outs[cnt - 1].flush();
                    }
                    cnt++;
                    port += 50;
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
    }
    public static void main(String args[])
    {
        PeerToPeerServer serv = new PeerToPeerServer(21030);
        serv.start();
    }
 }