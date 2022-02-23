package io;

import dragon.Color;
import dragon.DragonCharacter;
import dragon.DragonType;

import java.util.List;
import java.util.Scanner;
/**
* Интерфейс для запроса информации у пользователя*/

public interface PropertiesRequester {
    DragonProperties request();
}

