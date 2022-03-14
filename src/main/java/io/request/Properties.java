package io.request;

import dragon.Color;
import dragon.DragonCharacter;
import dragon.DragonType;
import java.util.List;

/**
* Класс присваивания полям элементов значений, полученных с консоли или из файла
*/
public class Properties {
    public String name;
    public Float xCoord;
    public Integer yCoord;
    public Long age;
    public Color color;
    public DragonType type;
    public DragonCharacter character;
    public double depth;
    public Integer numberOfTreasures;

    public static Properties parseProperties(List<String> input, int indexShift) throws Exception {
        if (input.size() != 9 + indexShift)
            throw new Exception(String.format("Ошибка. Неверное количество параметров. Ожидалось %d, получено %d", 9 + indexShift, input.size()));

        Properties properties = new Properties();

        try {
            properties.name = input.get(0 + indexShift);
            properties.xCoord = Float.parseFloat(input.get(1 + indexShift));
            properties.yCoord = Integer.parseInt(input.get(2 + indexShift));
            properties.age = Long.parseLong(input.get(3 + indexShift));
            properties.color = input.get(4 + indexShift).equals("null") ? null: Color.valueOf(input.get(4 + indexShift).toUpperCase());
            properties.type = DragonType.valueOf(input.get(5 + indexShift).toUpperCase());
            properties.character = input.get(6 + indexShift).equals("null") ? null : DragonCharacter.valueOf(input.get(6 + indexShift).toUpperCase());
            properties.depth = Double.parseDouble(input.get(7 + indexShift));
            properties.numberOfTreasures = input.get(8 + indexShift).equals("null") ? null: Integer.parseInt(input.get(8 + indexShift));
        } catch (RuntimeException e) {
            throw new Exception("Ошибка. Типы данных несовместимы.");
        }

        if (properties.name.isEmpty())
            throw new Exception("Ошибка. Параметр ИМЯ не может быть пустым");

        if (properties.yCoord > 998)
            throw new Exception("Ошибка. Параметр КООРДИНАТА_Y не может быть >998");

        if (properties.age <= 0)
            throw new Exception("Ошибка. Параметр ВОЗРАСТ не может быть <= 0");

        if (properties.numberOfTreasures != null && properties.numberOfTreasures <= 0)
            throw new Exception("Ошибка. Параметр КОЛИЧЕСТВО_СОКРОВИЩ_В_ПЕЩЕРЕ не может быть <= 0");

        return properties;
    }
}