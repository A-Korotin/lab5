package dragon;


/**
 * Класс пещеры элементов коллекции*/
public class DragonCave {

    public DragonCave(double depth, Integer numberOfTreasures) {
        this.depth = depth;
        this.numberOfTreasures = numberOfTreasures;
    }

    private double depth;
    private Integer numberOfTreasures; //Поле может быть null, Значение поля должно быть больше 0

    public double getDepth() {
        return depth;
    }

    public Integer getNumberOfTreasures() {
        return numberOfTreasures;
    }

    @Override
    public String toString() {
        return "dragon.DragonCave{" +
                "depth=" + depth +
                ", numberOfTreasures=" + numberOfTreasures +
                '}';
    }
}
