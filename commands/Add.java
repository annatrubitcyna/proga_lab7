package commands;

import collectionClass.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedList;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Add extends Command {
    public Add(String arg) {
        this.args=arg;
        name = "add";
        help = "добавить новый элемент в коллекцию";
    }

    /**
     * Добавляет новый элемент в коллекцию
     *
     * @param collection коллекция
     */
    @Override
    public String work( LinkedList<HumanBeing> collection) throws ArrayIndexOutOfBoundsException{
        String otv = "";
        try {
            String[] element = args.split(" ");
            int id = 0;
            String name = "abv"; //Поле не может быть null, Строка не может быть пустой
            int coordinatesX = 0;
            Long coordinatesY = Long.valueOf(0);
            Coordinates coordinates = new Coordinates(coordinatesX, coordinatesY); //Поле не может быть null
            Date creationDate = new Date(1999,1,8); //Поле не может быть null, Значение этого поля должно генерироваться автоматически
            boolean realHero = FALSE;
            boolean hasToothpick = FALSE;
            float impactSpeed = 0.0f;
            String soundtrackName = ""; //Поле не может быть null
            WeaponType weaponType = WeaponType.BAT; //Поле может быть null
            Mood mood = Mood.CALM; //Поле не может быть null
            Car car = new Car(TRUE); //Поле не может быть null
            try {
                id = Integer.parseInt(element[11]);
            } catch (NumberFormatException e) {
            }
            name = element[1];
            try {
                coordinatesX = Integer.parseInt(element[2]);
            } catch (NumberFormatException e) {
            }
            try {
                coordinatesY = new Long(element[3]);
                coordinates = new Coordinates(coordinatesX, coordinatesY);
            } catch (NumberFormatException e) {
            }
            try {
                realHero = new Boolean(element[4]);
            } catch (NumberFormatException e) {
            }
            try {
                hasToothpick = new Boolean(element[5]);
            } catch (NumberFormatException e) {
            }
            try {
                impactSpeed = new Float(element[6]);
            } catch (NumberFormatException e) {
            }
            soundtrackName = element[7];
            if (element[8].equals("KNIFE")) {
                weaponType = WeaponType.KNIFE;
            }
            if (element[8].equals("AXE")) {
                weaponType = WeaponType.AXE;
            }
            if (element[8].equals("SHOTGUN")) {
                weaponType = WeaponType.SHOTGUN;
            }
            if (element[8].equals("RIFLE")) {
                weaponType = WeaponType.RIFLE;
            }
            if (element[8].equals("BAT")) {
                weaponType = WeaponType.BAT;
            }
            if (element[9].equals("LONGING")) {
                mood = Mood.LONGING;
            }
            if (element[9].equals("GLOOM")) {
                mood = Mood.GLOOM;
            }
            if (element[9].equals("CALM")) {
                mood = Mood.CALM;
            }
            if (element[9].equals("RAGE")) {
                mood = Mood.RAGE;
            }
            if (element[9].equals("FRENZY")) {
                mood = Mood.FRENZY;
            }
            try {
                car = new Car(new Boolean(element[10]));
            } catch (NumberFormatException e) {
            }
            HumanBeing human = new HumanBeing(id, name, coordinates, creationDate, realHero, hasToothpick, impactSpeed, soundtrackName, weaponType, mood, car);
            collection.add(human);
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "12345");
            Statement statement = connection.createStatement();
            String[] e=element;
            String SqlSelectTask = "insert into data(name, coordinatex, coordinatey, creation_date, real_hero, has_tooth_pick, impackt_speed, soundtrack_name, weapon_type, mood, car_cool, add_user) ";
            SqlSelectTask+="values (\'"+e[1]+"\',"+e[2]+","+e[3]+", '1999-01-08',"+e[4]+", "+e[5]+", "+e[6]+", \'"+e[7]+"\', \'"+e[8]+"\', \'"+e[9]+"\',"+e[10]+",\'"+e[11]+"\' );";
            statement.executeUpdate(SqlSelectTask);

            //add Vasya 10 35 true true 90.0 Name1 BAT CALM true
            otv+="\n"+"Новый элемент добавлен"+"\n";
        }
        catch(ArrayIndexOutOfBoundsException | SQLException a){
            otv+="Неверный формат данных"+"\n";
        }
        return otv;
    }
}
