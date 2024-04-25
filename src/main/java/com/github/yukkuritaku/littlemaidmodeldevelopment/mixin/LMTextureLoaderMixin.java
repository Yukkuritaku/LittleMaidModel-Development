package com.github.yukkuritaku.littlemaidmodeldevelopment.mixin;

import com.github.yukkuritaku.littlemaidmodeldevelopment.LMMDMod;
import net.sistr.littlemaidmodelloader.resource.loader.LMTextureLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.InputStream;
import java.nio.file.Path;

@Mixin(value = LMTextureLoader.class, remap = false)
public class LMTextureLoaderMixin {

    @Inject(method = "load", at = @At(value = "HEAD", remap = false), remap = false)
    private void injectLoad(String path, Path folderPath, InputStream inputStream, boolean isArchive, CallbackInfo ci){
        LMMDMod.LOGGER.info("[LMTextureLoaderMixin] path: {}, folderPath: {}, inputStream: {}, isArchive: {}",
                path.replace("\\", "/"), folderPath, inputStream, isArchive);
    }
}
