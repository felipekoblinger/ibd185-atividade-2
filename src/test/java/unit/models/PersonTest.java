package unit.models;

import br.gov.sp.fatec.models.Person;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class PersonTest {
    @Test
    public void testPojo() {
        final Class<?> classUnderTest = Person.class;

        assertPojoMethodsFor(classUnderTest)
                .testing(Method.GETTER)
                .areWellImplemented();
        assertPojoMethodsFor(classUnderTest)
                .testing(Method.SETTER)
                .areWellImplemented();
    }
}
