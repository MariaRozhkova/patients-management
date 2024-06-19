package patients.generator.enums;

import java.util.Random;

/**
 * Enum represents supported genders.
 */
public enum Gender {
    FEMALE, MALE;

    /**
     * Fetches random gender.
     */
    public static Gender randomGender() {
        var genders = values();
        var random = new Random();

        return genders[random.nextInt(genders.length)];
    }
}
