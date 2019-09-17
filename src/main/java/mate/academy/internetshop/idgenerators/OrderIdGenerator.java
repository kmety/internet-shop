package mate.academy.internetshop.idgenerators;

public class OrderIdGenerator {
    private static long idValue = 0;

    public static long generateId() {
        return idValue++;
    }
}
