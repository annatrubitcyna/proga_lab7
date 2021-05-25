package kl_serv;

import collectionClass.HumanBeing;
import commands.Command;
import commands.Help;
import commands.ParcerBd;
import commands.Save;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;


public class Server2 {
    static ForkJoinPool pool = new ForkJoinPool(2);
    static ExecutorService executeIt = Executors.newFixedThreadPool(2);
    public static Command command;
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, SQLException {
        try (ServerSocket ss = new ServerSocket(7777)) {
            System.out.println("ServerSocket awaiting connections...");
            ParcerBd parcer = new ParcerBd();
            LinkedList<HumanBeing> collection = parcer.work();
            CopyOnWriteArrayList<HumanBeing> safeCollection;
            while (!ss.isClosed()) {
                Socket socket = ss.accept();
                // после получения запроса на подключение сервер создаёт сокет
                // для общения с клиентом и отправляет его в отдельную нить
                // в Runnable(при необходимости можно создать Callable)
                // монопоточную нить = сервер - MonoThreadClientHandler и тот
                // продолжает общение от лица сервера
                System.out.println("Connection from " + socket + "!");
                //pool.execute(new Reader(socket, collection));
                executeIt.execute(new MonoThreadClientHandler(socket, collection));
                System.out.print("Connection accepted.");
            }
            // закрытие пула нитей после завершения работы всех нитей
            executeIt.shutdown();
        }
        catch (SocketException e){
            System.out.println("no connection");
        }
    }
}
