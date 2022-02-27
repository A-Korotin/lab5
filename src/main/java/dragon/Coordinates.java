package dragon;


/**
 * Класс координат элементов коллекции
 */

public class Coordinates {
    public Coordinates(Float x, Integer y) {
        this.x = x;
        this.y = y;
    }

    private Float x; //Поле не может быть null
    private Integer y; //Максимальное значение поля: 998, Поле не может быть null

    public Float getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public String toString() {
        return "dragon.Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
