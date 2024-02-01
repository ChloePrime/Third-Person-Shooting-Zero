package mod.chloeprime.thirdpersonshooting.client.util;

public class Math2 {
    public static boolean is0(float v) {
        return Math.abs(v) < 1e-4;
    }

    public static boolean not0(float v) {
        return !is0(v);
    }

    private Math2() {
    }
}
