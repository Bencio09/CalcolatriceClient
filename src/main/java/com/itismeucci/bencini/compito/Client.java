package com.itismeucci.bencini.compito;

import java.net.*;
import java.io.*;

public class Client {
    String nomeServer = "localhost";
    int portaServer = 4335;
    Socket miosocket ;
    BufferedReader tastiera;
    String stringaUtente;
    String stringaRicevutaDalServer;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;
    String cheFai = null;

    public Socket connetti(){
        System.out.println("C -> CLIENT partito in esecuzione ...");
        try {
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            miosocket = new  Socket(nomeServer, portaServer);

            outVersoServer = new DataOutputStream(miosocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(miosocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Host sconosciuto");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione");
            System.exit(1);
        }
        return miosocket;
    }

    public void comunica(){
        try{
            System.out.println("Benvenuto io sono un server calcolatrice!\n");
            do {
                System.out.println("S -> Inserisci il primo valore\n");
                outVersoServer.writeBytes(tastiera.readLine());
                System.out.println("S -> Inserisci il secondo valore\n");
                outVersoServer.writeBytes(tastiera.readLine());
                System.out.println("S -> Inserisci il segno (+,-,*,/)\n");
                outVersoServer.writeBytes(tastiera.readLine());
                System.out.println("Il risultato dell'operazione richiesta è " + inDalServer.readLine() + "\n");
                System.out.println("S -> Ti servo ancora? Y/N\n");
                cheFai = tastiera.readLine();
                outVersoServer.writeBytes(cheFai);
            } while (cheFai.equalsIgnoreCase("Y"));
            close();
        }catch(Exception e){}
    }

    public void close(){
        try {
            miosocket.close();
            inDalServer.close();
            outVersoServer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
