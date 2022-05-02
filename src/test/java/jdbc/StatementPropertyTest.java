package jdbc;

import exceptions.RequiredFieldNotSetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatementPropertyTest {

    @Test
    void builder() {
        try {
            StatementProperty p = new StatementProperty.Builder().build();
        } catch (RequiredFieldNotSetException e) {
            assertEquals(1, 1);
            return;
        }
        assertEquals(1, 0);
    }

}