//package commands;
//
//
//import collection.DAO;
//import dragon.Dragon;
//import io.request.Properties;
//
//import java.util.List;
//
///**
// * Класс, предназначенный для обновления существующего элемента по его <b>ID</b> (<i>обязательный аргумент команды</i>)<br>
// * При вводе данных в консоль пользователю будет показываться приглашение к вводу<br>
// * При вводе данных в файл все характеристики элемента нужно вводить последовательно через пробел
// */
//public class Update extends Command {
//
//    public Update(List<String> args) {
//        super(args);
//    }
//
//    @Override
//    public int execute(DAO dao) {
//        if (args.size() != 1) {
//            //outPuter.outPut("Неверное количество параметров");
//            return -1;
//        }
//        int id;
//        try{
//            id = Integer.parseInt(args.get(0));
//        }
//        catch (RuntimeException e){
//            //outPuter.outPut("Нецелочисленный тип данных id");
//            return -1;
//        }
//        boolean found = false;
//        for(Dragon d: dao.getAll()) {
//            if(d.getId() == id) {
//                found = true;
//                break;
//            }
//        }
//        if (!found){
//            //outPuter.outPut("Элемент с id %d не существует".formatted(id));
//            return -1;
//        }
//
//        int exitCode;
//        Properties properties;
//        if (askForInput){}
//            //properties = requester.request();
//        else {
//            try{
//                properties = Properties.parseProperties(args, 1);
//            } catch (RuntimeException e){
//                //outPuter.outPut(e.getMessage());
//                return -1;
//            }
//        }
//        exitCode = dao.update(id, properties);
//        //outPuter.outPut("Элемент успешно обновлён");
//        return exitCode;
//    }
//}
