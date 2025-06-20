package org.error1015.sorrywehavetooenoughfundstoprovideyouwithenderdaragon;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    static final ModConfigSpec SPEC;

    public static final ModConfigSpec.BooleanValue enable = BUILDER
            .comment("Enable the mod.")
            .define("enable", true);

    public static ModConfigSpec.IntValue funds = BUILDER
            .comment("we have too many funds so that you can't have no funds.", "1000 funds provide you a cute ender dragon.")
            .defineInRange("funds", 1_000, 1_000, 10_000);

    static {
        SPEC = BUILDER.build();
    }
}