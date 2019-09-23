package mate.academy.internetshop.idgenerators;

public class RoleIdGenerator {
    private static long idValue = 0;

    public static long generateId() {
        return idValue++;
    }
}
