package commands;

import java.util.List;

/**
 * Класс, предназначенный для выведения всех возможных команд и их аргументов в ранее заданный поток вывода
 */
public class Help extends Command {

    public Help(List<String> args) {
        super(args, 0);
    }

    @Override
    public int execute(Instances instances) {
        instances.outPutter.output("""
                help : вывести справку по доступным командам
                
                info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
                
                show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
                
                add {element} : добавить новый элемент в коллекцию (при вводе в консоль не передавать аргументы,
                при считывании из файла данные вводить в порядке: имя координата_X координата_Y возраст цвет тип характер глубина_пещеры количество_сокровищ_в_пещере)
                
                update id {element} : обновить значение элемента коллекции, id которого равен заданному (при вводе в консоль не передавать аргументы,
                при считывании из файла данные вводить в порядке: имя координата_X координата_Y возраст цвет тип характер глубина_пещеры количество_сокровищ_в_пещере)
                
                remove_by_id id : удалить элемент из коллекции по его id
                
                clear : очистить коллекцию
                
                save : сохранить коллекцию в файл
                
                execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
                
                exit : завершить программу (без сохранения в файл)
                
                add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции (при вводе в консоль не передавать аргументы,
                при считывании из файла данные вводить в порядке: имя координата_X координата_Y возраст цвет тип характер глубина_пещеры количество_сокровищ_в_пещере)
                
                sort : отсортировать коллекцию в естественном порядке
                
                history : вывести последние 6 команд (без их аргументов)
                
                min_by_id : вывести любой объект из коллекции, значение поля id которого является минимальным
                
                count_by_age age : вывести количество элементов, значение поля age которых равно заданному
                
                filter_greater_than_character character : вывести элементы, значение поля character которых больше заданного
                
                ya_alex_egoshin_postavlu_12_balov : вывести прекрасную пасхалку""");
        return 0;
    }

}
