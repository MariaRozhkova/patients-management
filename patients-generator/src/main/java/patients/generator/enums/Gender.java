package patients.generator.enums;

import java.util.Random;

public enum Gender {
    FEMALE, MALE;

    public static Gender randomGender() {
        var genders = values();
        var random = new Random();

        return genders[random.nextInt(genders.length)];
    }
}
