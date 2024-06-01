package mod.chloeprime.thirdpersonshooting.client;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.BooleanValue CONSTANT_AIMING_ZOOM_SCALE;
    public static final ForgeConfigSpec.DoubleValue CONSTANT_AIMING_ZOOM_SCALE_VALUE;
    public static final ForgeConfigSpec.DoubleValue CONSTANT_AIMING_ADS_SPEED;
    public static final ForgeConfigSpec.BooleanValue DYNAMIC_AIM_MODE;

    static {
        var builder = new ForgeConfigSpec.Builder();

        CONSTANT_AIMING_ZOOM_SCALE = builder
                .comment("Make fov scale at shoulder surfing view constant, ignoring scopes on current gun")
                .define("constant_aiming_zoom_scale", true);

        CONSTANT_AIMING_ZOOM_SCALE_VALUE = builder
                .comment("FOV scale for constant_aiming_fov_scale")
                .defineInRange("constant_aiming_zoom_scale_value", 3.125, 0, Double.MAX_VALUE);

        CONSTANT_AIMING_ADS_SPEED = builder
                .comment("(Locked) ads speed for constant_aiming_fov_scale")
                .defineInRange("constant_aiming_ads_speed", 1, 0, Double.MAX_VALUE);

//                .comment("""
//                        Set this to true to make Free Look only applies to yaw,
//                        and always keep the pitch move with mouse""")

        DYNAMIC_AIM_MODE = builder
                .comment("""
                        If enabled, tapping the aim button will put you in/out of first person view
                        This only works while TAC's Config.CLIENT.controls.holdToAim is set to true""")
                .define("dynamic_aim_mode", true);

        SPEC = builder.build();
    }

    private ClientConfig() {}
}
