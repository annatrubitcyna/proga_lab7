package commands;

import collectionClass.HumanBeing;

import java.sql.*;
import java.util.LinkedList;

public class UpdateId extends Command {
    public UpdateId(String arg) {
        this.args = arg;
        name = "update id";
        help = " обновить значение элемента коллекции, id которого равен заданному";
    }

    /**
     * Обновляет значение элемента коллекции, id которого равен заданному
     *
     * @param collection коллекция
     */
    @Override
    public String work(LinkedList<HumanBeing> collection) {
        String otv = "";
        if (args.equals("")) {
            otv += "Введите дополнительные аргументы" + "\n";
        } else {
            try {
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "12345");
                Statement statement = connection.createStatement();
                String SqlSelectTask = "select * from data";
                ResultSet result = statement.executeQuery(SqlSelectTask);
                String[] e=args.split(" ");
                SqlSelectTask = "UPDATE data SET name=\'"+e[1]+"\', coordinatex="+e[2]+",coordinatey= "+e[3]+",creation_date= '1999-01-08',real_hero="+e[4]+",  has_tooth_pick="+e[5]+", impackt_speed="+e[6]+", soundtrack_name=\'"+e[7]+"\',weapon_type= \'"+e[8]+"\',mood= \'"+e[9]+"\',car_cool="+e[10]+", add_user=\'"+e[e.length-1]+"\' "+"WHERE id = "+e[e.length - 2]+";";
                //otv+=SqlSelectTask+"\n";
                //otv+=e[e.length-1]+" "+e[e.length-2];
                boolean avt=false;
                table: while (result.next()) {
                    //otv+="2\n";
                    //otv+=result.getString("add_user")+" "+result.getInt("id")+"\n";
                    String strId= String.valueOf(result.getInt("id"));
                    if (e[e.length - 1].equals(result.getString("add_user")) & e[e.length - 2].equals(strId)) {
                        //otv+="!"+"\n";
                        RemoveById removeById = new RemoveById(args.split(" ")[args.split(" ").length - 2]);
                        //otv += removeById.work(collection);
                        OldAdd add = new OldAdd(args);
                        //otv += add.work(collection);
                        statement.executeUpdate(SqlSelectTask);
                        avt=true;
                        otv += "Элемент обновлен" + "\n";
                        break table;
                    }
                }
                if (avt==false){
                    otv+="Нет элемента с таким id или этот элемент принадлежит другому пользователю";
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return otv;
        //updateId Vas 10 35 True True 90.0 Name1 BAT CALM True 36
    }
}