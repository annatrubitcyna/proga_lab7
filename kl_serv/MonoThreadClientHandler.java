package kl_serv;

import collectionClass.HumanBeing;
import commands.Command;
import commands.Save;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;

public class MonoThreadClientHandler implements Runnable {

    private static Socket socket;
    private LinkedList<HumanBeing> collection;

    public MonoThreadClientHandler(Socket socket, LinkedList<HumanBeing> collection) {
        MonoThreadClientHandler.socket = socket;
        this.collection = collection;
    }


    @Override
    public void run() {

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // основная рабочая часть //
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            objectOutputStream.writeObject("Connection establish.");
            while (true) {
                Object message = objectInputStream.readObject();
                Command command=null;
                String arg = "";
                if (message instanceof Command)
                    command = (Command)message;
                if (command.name.equals("exit")){
                    Save save=new Save("");
                    objectOutputStream.writeObject(save.work(collection)+" Конец работы");
                    System.out.println("Client initialize connections suicide ...");
                    objectOutputStream.writeUTF("Server reply - "+message + " - OK");
                    objectOutputStream.flush();
                    Thread.sleep(3000);
                    break;
                }
                else{
                    objectOutputStream.writeObject(command.work(collection));
                }
                System.out.println("Received [" + message + "] from: " + socket);
//                        message = "Answer ::: " + message + "\ntest string";
//                        objectOutputStream.writeObject(message);
            }
        } catch (SocketException e){
            System.out.println("\n No connection");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}