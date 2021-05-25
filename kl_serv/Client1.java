package kl_serv;

import collectionClass.HumanBeing;
import commands.*;

import javax.xml.transform.Result;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) {
        System.out.println("Starting client module.\nConnecting to server ...");
        try (Socket outcoming = new Socket(InetAddress.getLocalHost(), 7777)) {
            outcoming.setSoTimeout(10000);
            try (ObjectOutputStream toServer = new ObjectOutputStream(outcoming.getOutputStream());
                 ObjectInputStream fromServer = new ObjectInputStream(outcoming.getInputStream());
                 Scanner fromKeyboard = new Scanner(System.in)) {
                System.out.println((String) fromServer.readObject());
                String line = "";
                String commanda = "";
                String arg = "";
                LinkedList<HumanBeing> collection = new LinkedList<>();
                List<Integer> arrayId = new ArrayList<Integer>();
                int id = (int) (Math.random() * 1000);
                work:
                do {
                    System.out.println("для начала работы программы необходимо зарегестрироваться или авторизироваться: введите a или r, а после логин и пороль, например: \"a login password\" ");
                    String[] str = fromKeyboard.nextLine().split(" ");
                    if (str.length == 3) {
                        DataHasher dataHasher = new DataHasher();
                        String myUser = "User";
//                        if (str[0].equals("r")) {
//                            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "12345");
//                            //объект который умеет отравлять запросы в базу
//                            Statement statement = connection.createStatement();
//                            String SqlSelectTask = "insert into users(login, password) values (\'" + str[1] + "\',\'" + dataHasher.encryptStringMD2(str[2]) + "\');";
//                            //System.out.println(SqlSelectTask);
//                            statement.executeUpdate(SqlSelectTask);
//                            System.out.println("вы успешно зарегистрировались!");
//                            myUser = str[1];
////                      //id=result.getInt("id");
//                        }
                        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "12345");
                        //объект который умеет отравлять запросы в базу
                        Statement statement = connection.createStatement();
                        String SqlSelectTask = "select * from users";
                        ResultSet result = statement.executeQuery(SqlSelectTask);
                        boolean avt = false;
                        while (result.next()) {
                            if (result.getString("login").equals(str[1]) & result.getString("password").equals(dataHasher.encryptStringMD2(str[2]).toString())) {
                                if (str[0].equals("a")) {
                                    System.out.println("вы успешно авторизировались!");
                                    myUser = str[1];
                                    avt = true;
                                    break;
                                }
                                if (str[0].equals("r")) {
                                    System.out.println("такой пользователь уже существует");
                                    avt = true;
                                    continue work;
                                }
                            }
                        }
                        if (avt == false) {
                            if (str[0].equals("a")) {
                                System.out.println("Неверный логин или пароль");
                                continue work;
                            }
                            if (str[0].equals("r")) {
                                SqlSelectTask = "insert into users(login, password) values (\'" + str[1] + "\',\'" + dataHasher.encryptStringMD2(str[2]) + "\');";
                                //System.out.println(SqlSelectTask);
                                statement.executeUpdate(SqlSelectTask);
                                System.out.println("вы успешно зарегистрировались!");
                                myUser = str[1];
                            }
                        }
                        System.out.println("введите команду");
                        line = fromKeyboard.nextLine() + " " + myUser;
                        commanda = line.split(" ")[0];
                        if (line.split(" ").length != 2) {
                            arg = line.split(" ")[1];
                        } else {
                            arg = "";
                        }
                        //System.out.println(arg);
                        switch (commanda) {
                            case ("exit"):
                                Object exit = new Exit(arg);
                                toServer.writeObject(exit);
                                System.out.println((String) fromServer.readObject());
                                break;
                            case ("help"):
                                Object help = new Help(arg);
                                toServer.writeObject(help);
                                System.out.println((String) fromServer.readObject());
                                break;
                            case ("head"):
                                Head head = new Head(arg);
                                toServer.writeObject(head);
                                System.out.println((String) fromServer.readObject());
                                break;
                            case ("info"):
                                Info info = new Info(arg);
                                toServer.writeObject(info);
                                System.out.println((String) fromServer.readObject());
                                break;
                            case ("add"):
                                while (arrayId.contains(id)) {
                                    id = (int) (Math.random() * 1000);
                                }
                                arrayId.add(id);
                                Add add = new Add(line + " " + id);
                                toServer.writeObject(add);
                                System.out.println((String) fromServer.readObject());
                                break;
                            case ("averageOfImpactSpeed"):
                                AverageOfImpactSpeed averageOfImpactSpeed = new AverageOfImpactSpeed(arg);
                                toServer.writeObject(averageOfImpactSpeed);
                                System.out.println((String) fromServer.readObject());
                                break;
                            case ("clear"):
                                Clear clear = new Clear(arg);
                                toServer.writeObject(clear);
                                System.out.println((String) fromServer.readObject());
                                break;
                            case ("executeScript"):
                                ExecuteScript executeScript = new ExecuteScript(arg);
                                toServer.writeObject(executeScript);
                                System.out.println((String) fromServer.readObject());
                                break;
                            case ("filterContainsName"):
                                FilterContainsName filterContainsName = new FilterContainsName(arg);
                                toServer.writeObject(filterContainsName);
                                System.out.println((String) fromServer.readObject());
                                break;
                            case ("printUniqueSoundtrackName"):
                                PrintUniqueSoundtrackName printUniqueSoundtrackName = new PrintUniqueSoundtrackName(arg);
                                toServer.writeObject(printUniqueSoundtrackName);
                                System.out.println((String) fromServer.readObject());
                                break;
                            case ("removeById"):
                                RemoveById removeById = new RemoveById(arg);
                                toServer.writeObject(removeById);
                                System.out.println((String) fromServer.readObject());
                                break;
                            case ("removeFirst"):
                                RemoveFirst removeFirst = new RemoveFirst(arg);
                                toServer.writeObject(removeFirst);
                                System.out.println((String) fromServer.readObject());
                                break;
                            case ("removeHead"):
                                RemoveHead removeHead = new RemoveHead(arg);
                                toServer.writeObject(removeHead);
                                System.out.println((String) fromServer.readObject());
                                break;
                            case ("save"):
                                Save save = new Save(arg);
                                toServer.writeObject(save);
                                System.out.println((String) fromServer.readObject());
                                break;
                            case ("show"):
                                Show show = new Show(arg);
                                toServer.writeObject(show);
                                System.out.println((String) fromServer.readObject());
                                break;
                            case ("updateId"):
                                UpdateId updateId = new UpdateId(line);
                                toServer.writeObject(updateId);
                                System.out.println((String) fromServer.readObject());
                                break;
                            default:
                                System.out.println("Такой команды нет");
                        }
                    }
                } while (!commanda.equals("exit"));
//                while (!(command = fromKeyboard.nextLine()).equals("exit")) {
//                    toServer.writeObject(command);
//                    System.out.println((String) fromServer.readObject());
//                }
                System.out.println("Closing socket and terminating program.");
            } catch (ClassNotFoundException e) {
                e.getMessage();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Could not connect. Something went wrong.");
        }
    }
}
