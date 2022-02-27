//package commands;
//
//
//import collection.DAO;
//import collection.DaoElement;
//import dragon.Dragon;
//import io.request.Properties;
//
//import java.util.List;
//
///**
// * Класс, предназначенный для добавления элемента в коллекцию, если <b>возраст</b> нового элемента больше возраста всех существующих элементов<br>
// * При вводе данных в консоль пользователю будет показываться приглашение к вводу<br>
// * При вводе данных в файл все характеристики элемента нужно вводить последовательно через пробел
// */
//public class AddIfMax extends Command {
//
//    public AddIfMax(List<String> args) {
//        super(args);
//    }
//
//    @Override
//    public int execute(DAO dao) {
//        Long ageMax = -1L;
//        for (Dragon dragon : dao.getAll()) {
//            if (dragon.getAge() > ageMax) {
//                ageMax = dragon.getAge();
//            }
//        }
//        Properties properties;
//        if (askForInput) {
//            if (args.size() > 0) {
//                //outPuter.outPut("Неверное количество параметров");
//                return -1;
//            }
//            properties = requester.request();
//        }
//        else {
//            try {
//                properties = Properties.parseProperties(args, 0);
//            } catch (RuntimeException e) {
//                //outPuter.outPut(e.getMessage());
//                return -1;
//            }
//        }
//
//
//        if (properties.age > ageMax){
//            dao.create(properties);
//            //outPuter.outPut("Элемент успешно добавлен");
//        }
//        else {
//            //outPuter.outPut("Значение этого элемента меньше максимального в коллекции. Элемент не добавлен");
//        }
//        return 0;
//    }
//}
