package com.github.yukkuritaku.littlemaidmodeldevelopment.mixin;

import com.github.yukkuritaku.littlemaidmodeldevelopment.LMMDMod;
import net.sistr.littlemaidmodelloader.LMMLMod;
import net.sistr.littlemaidmodelloader.resource.loader.LMFileLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;
import java.nio.file.Paths;

@Mixin(value = LMMLMod.class, remap = false)
public class LMMLModMixin {
    @Inject(method = "initFileLoader", at = @At(value = "HEAD", remap = false), remap = false)
    private static void injectInitFileLoader(CallbackInfo ci) {
        boolean success = true;
        String devClasses = System.getProperty("lmmd.dev.classes");
        String devResources = System.getProperty("lmmd.dev.resources");
        if (devClasses == null || devResources == null){
            LMMDMod.LOGGER.error("""

開発環境パスの参照に失敗しました。もしかしたらJVMプロパティーをbuild.gradleに実装していないかも...?
もし実装していないなら、build.gradleにあるloomスクリプトにこれを実装してください:
runs{
    client{
        property("lmmd.dev.classes", "${sourceSets.main.output.getClassesDirs().asPath}")
        property("lmmd.dev.resources", "${sourceSets.main.output.getClassesDirs().asPath}")
        client()
    }
}
詳しい説明についてはFabricのWikiを参照してください！
https://fabricmc.net/wiki/documentation:fabric_loom
""");
            return;
        }
        Path classesPath = Paths.get(devClasses);
        Path resourcePath = Paths.get(devResources);
        LMFileLoader fileLoader = LMFileLoader.INSTANCE;
        LMMDMod.LOGGER.info("開発環境パス: {}, {}",
                classesPath.toString().replace("\\", "/"),
                resourcePath.toString().replace("\\", "/"));
        Exception exception = null;
        try {
            fileLoader.addLoadFolderPath(classesPath);
            fileLoader.addLoadFolderPath(resourcePath);
        }catch (Exception e){
            success = false;
            exception = e;
        }
        if (success) {
            LMMDMod.LOGGER.info("開発環境パスの追加に成功しました！");
        }else {
            LMMDMod.LOGGER.error("開発環境パスの追加に失敗しました。");
            LMMDMod.LOGGER.error("スタックトレース:", exception);
        }
    }
}
