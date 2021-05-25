package commands;

import collectionClass.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ParcerBd {
    public LinkedList<HumanBeing> work() throws SQLException {
        LinkedList<HumanBeing> collection = new LinkedList<>();
        int id = (int) (Math.random() * 1000);
        String name = "abv"; //Поле не может быть null, Строка не может быть пустой
        int coordinatesX = 0;
        Long coordinatesY = Long.valueOf(0);
        Coordinates coordinates = new Coordinates(coordinatesX, coordinatesY); //Поле не может быть null
        LocalDate localDate = LocalDate.of((int) Math.random() * 2021 + 1, (int) Math.random() * 12 + 1, (int) Math.random() * 31 + 1);
        LocalTime localTime = LocalTime.of((int) Math.random() * 25, (int) Math.random() * 61, (int) Math.random() * 61);
        ZoneId zoneId = ZoneId.of("UTC+1");
        Date creationDate = new Date(1999,1,8); //Поле не может быть null, Значение этого поля должно генерироваться автоматически
        //ZonedDateTime creationDate = ZonedDateTime.of(localDate, localTime, zoneId); //Поле не может быть null, Значение этого поля должно генерироваться автоматически
        boolean realHero = FALSE;
        boolean hasToothpick = FALSE;
        float impactSpeed = 0.0f;
        String soundtrackName = ""; //Поле не может быть null
        WeaponType weaponType = WeaponType.BAT; //Поле может быть null
        Mood mood = Mood.CALM; //Поле не может быть null
        Car car = new Car(TRUE); //Поле не может быть null
        HumanBeing human = new HumanBeing(id, name, coordinates, creationDate, realHero, hasToothpick, impactSpeed, soundtrackName, weaponType, mood, car);
        List<Integer> arrayId = new ArrayList<Integer>();


        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "12345");
        //объект который умеет отравлять запросы в базу
        Statement statement = connection.createStatement();
        String SqlSelectTask = "select * from data order by id desc";
        ResultSet result = statement.executeQuery(SqlSelectTask);
        while (result.next()) {
            id=result.getInt("id");
            name=result.getString("name");
            coordinatesX=result.getInt("coordinatex");
            coordinatesY=result.getLong("coordinatey");
            creationDate=result.getDate("creation_date");
            realHero=result.getBoolean("real_hero");
            hasToothpick=result.getBoolean("has_tooth_pick");
            impactSpeed=result.getFloat("impackt_speed");
            soundtrackName=result.getString("soundtrack_name");
            String y=result.getString("weapon_type");
            if (y.equals("KNIFE")) {
                weaponType = WeaponType.KNIFE;
            }
            if (y.equals("AXE")) {
                weaponType = WeaponType.AXE;
            }
            if (y.equals("SHOTGUN")) {
                weaponType = WeaponType.SHOTGUN;
            }
            if (y.equals("RIFLE")) {
                weaponType = WeaponType.RIFLE;
            }
            if (y.equals("BAT")) {
                weaponType = WeaponType.BAT;
            }
            y=result.getString("mood");
            if (y.equals("LONGING")) {
                mood = Mood.LONGING;
            }
            if (y.equals("GLOOM")) {
                mood = Mood.GLOOM;
            }
            if (y.equals("CALM")) {
                mood = Mood.CALM;
            }
            if (y.equals("RAGE")) {
                mood = Mood.RAGE;
            }
            if (y.equals("FRENZY")) {
                mood = Mood.FRENZY;
            }
            car=new Car(result.getBoolean("car_cool"));
            human = new HumanBeing(id, name, coordinates, creationDate, realHero, hasToothpick, impactSpeed, soundtrackName, weaponType, mood, car);
            //System.out.println("id:" + human.id + ", " + "name:" + human.name + ", " + "coordinate x:" + human.coordinates.x + ", " + "coordinate y:" + human.coordinates.y + ", " + "creation date:" + human.creationDate + ", " + "real hero:" + human.realHero + ", " + "has tooth pick:" + human.hasToothpick + ", " + "impackt speed:" + human.impactSpeed + ", " + "soundtrack name:" + human.soundtrackName + ", " + "weapon type:" + human.weaponType + ", " + "mood:" + human.mood + ", " + "car cool:" + human.car.cool);
            collection.add(human);
        }
        System.out.println("Коллекция загружена");
        return collection;
    }
}
