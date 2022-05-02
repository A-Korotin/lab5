package commands;


import commands.dependencies.Instances;
import dragon.Dragon;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Класс, предназначенный для очищения коллекции (<i>безвозвратного удаления всех элементов</i>)
 */
public final class Clear extends Command {

    public Clear(List<String> args) {
        super(args, 0);
    }

    @Override
    public int execute(Instances instances) {
        int[] idsToDelete = instances.dao.getAll()
                .stream()
                .filter(d -> d.getCreatorName().equals(user.login))
                .mapToInt(Dragon::getId).toArray();
        Arrays.stream(idsToDelete).forEach(instances.dao::delete);
        instances.outPutter.output("Элементы, созданные пользователем '%s', были удалены".formatted(user.login));
        return 0;
    }
}