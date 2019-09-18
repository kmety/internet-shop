package mate.academy.internetshop.idgenerators;

public class ItemIdGenerator {
    private static long idValue = 0;

    public static long generateId() {
        return idValue++;
    }
}
