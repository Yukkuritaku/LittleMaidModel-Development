package com.github.yukkuritaku.littlemaidmodeldevelopment;

import dev.architectury.platform.Platform;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//LittleMaidModelLoader 4.5.0まで対応
public class LMMDMod implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("littlemaidmodeldevelopment");

    @Override
    public void onInitialize() {
        if (!Platform.isDevelopmentEnvironment()) {
            LOGGER.warn("このModは開発環境専用のModです。通常環境で使用しても効果はありません！");
        }
    }
}