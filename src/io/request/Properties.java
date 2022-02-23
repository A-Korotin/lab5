package io.request;

import dragon.Color;
import dragon.DragonCharacter;
import dragon.DragonType;

/**
* Класс присваивания полям элементов значений, полученных с консоли или из файла
*/
public class Properties {
    String name;
    Float xCoord;
    Integer yCoord;
    Long age;
    Color color;
    DragonType type;
    DragonCharacter character;
    double depth;
    Integer numberOfTreasures;

}
//
//
//    public static DragonProperties parseProperties(List<String> input, int indexShift){
//        DragonProperties properties = new DragonProperties();
//        if (input.size() != 9 + indexShift)
//            throw new RuntimeException("ОШИБКА! Неверное количество параметров");
//        try {
//            properties.name = input.get(0+indexShift);
//            properties.xCoord = Float.parseFloat(input.get(1 + indexShift));
//            properties.yCoord = Integer.parseInt(input.get(2 + indexShift));
//            properties.age = Long.parseLong(input.get(3 + indexShift));
//            properties.color = input.get(4 + indexShift).equals("null") ? null: Color.valueOf(input.get(4 + indexShift));
//            properties.type = DragonType.valueOf(input.get(5 + indexShift));
//            properties.character = input.get(6 + indexShift).equals("null") ? null : DragonCharacter.valueOf(input.get(6 + indexShift));
//            properties.depth = Double.parseDouble(input.get(7 + indexShift));
//            properties.numberOfTreasures = input.get(8 + indexShift).equals("null") ? null: Integer.parseInt(input.get(8 + indexShift));
//        } catch (RuntimeException e) {
//            throw new RuntimeException("ОШИБКА! Типы данных несовместимы");
//        }
//        if (properties.name.isEmpty()) // redundant
//            throw new RuntimeException("ОШИБКА! Параметр ИМЯ не может быть пустым");
//        if (properties.yCoord > 998)
//            throw new RuntimeException("ОШИБКА! Параметр КООРДИНАТА_Y не может быть >998");
//        if (properties.age <= 0)
//            throw new RuntimeException("ОШИБКА !Параметр ВОЗРАСТ не может быть <= 0");
//        if (properties.numberOfTreasures != null && properties.numberOfTreasures <= 0)
//            throw new RuntimeException("ОШИБКА! Параметр КОЛИЧЕСТВО_СОКРОВИЩ_В_ПЕЩЕРЕ не может быть <= 0");
//        return properties;
//    }