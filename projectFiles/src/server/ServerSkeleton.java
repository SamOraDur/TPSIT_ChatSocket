package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import interfaces.ChatFeat;

public class ServerSkeleton implements ChatFeat{

	HashMap<String,ObjectOutputStream> addrMap;
	Semaphore MapSem;
	Server serverReale;

	public ServerSkeleton(Server serverReale){
		addrMap = new HashMap<String,ObjectOutputStream>();
		MapSem = new Semaphore(1);
		this.serverReale = serverReale;
	}

    public void skeleton(){
        try{
			// Socket di benvenuto
			ServerSocket SocketServer = new ServerSocket ( 6969 );

			//TBD IN GUI (creazione di una gui con terminale del server)
			
			this.AddMsgTerminal("[Server]: in attesa su porta 6969.");

			while(true)
			{
				//connessione al client
				Socket clientsock = SocketServer.accept();

				//TBD GUI
				this.AddMsgTerminal("[Server]: nuovo client." );

				//start di un nuovo thread per ogni connessione al client con una porta definita dal protocollo delle socket in java 
				//(come parametri abbiamo il numero della porta, lo skeleton che avvia il thread, il riferimento alla'hash map e il semaforo per l'utilizzo della map)
				ServerThread sThread = new ServerThread(clientsock,this,addrMap,MapSem);
				sThread.start();
			}

			
		}catch ( IOException e ){
			e.printStackTrace();
		}
    }

	@Override
    //metodo da server reale
    public int login(String usr,String psw){
        return serverReale.login(usr,psw);
    }

	@Override
    //metodo da server reale
    public int signUp(String usr,String psw){
        return serverReale.signUp(usr,psw);
    }

    @Override
    //metodo da server reale
    public NodeList LoadChat(String usr_a,String usr_b){
        return serverReale.LoadChat(usr_a,usr_b);
    }

    @Override
    //metodo da server reale
    public String SendMsg(Element msg){
        return serverReale.SendMsg(msg);
    }

    @Override
    //metodo da server reale
    public NodeList LoadContacts(String usr){
        return serverReale.LoadContacts(usr);
    }

    //metodo per utilizzo GUI
    public void AddMsgTerminal(String msg){
        serverReale.AddMsgTerminal(msg);
    }
}