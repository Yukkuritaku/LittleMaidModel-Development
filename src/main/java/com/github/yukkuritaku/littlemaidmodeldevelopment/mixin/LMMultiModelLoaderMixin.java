package com.github.yukkuritaku.littlemaidmodeldevelopment.mixin;

import com.github.yukkuritaku.littlemaidmodeldevelopment.LMMDMod;
import net.sistr.littlemaidmodelloader.resource.loader.LMMultiModelLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.InputStream;
import java.nio.file.Path;

@Mixin(value = LMMultiModelLoader.class, remap = false)
public class LMMultiModelLoaderMixin {

    @Unique
    private boolean mixin_isArchive;

    @Inject(method = "load",
            at = @At(value = "HEAD", remap = false),
            remap = false
    )
    private void injectLoad(String path, Path folderPath, InputStream inputStream, boolean isArchive, CallbackInfo ci){
        String logPath = path;
        String subStr = logPath.substring(0, 1);
        if (subStr.equals("\\") || subStr.equals("/")){
            logPath = logPath.substring(1);

        }
        LMMDMod.LOGGER.info("[LMMultiModelLoaderMixin] path: {}, folderPath: {}, inputStream: {}, isArchive: {}", logPath.replace("\\", "/"), folderPath, inputStream.toString(), isArchive);
        mixin_isArchive = isArchive;
    }

    @ModifyVariable(method = "load",
            at = @At(value = "HEAD", remap = false),
            remap = false, argsOnly = true)
    private String injectVariable(String value){
        if (!mixin_isArchive){
            //初動の開始位置がバックスラッシュorスラッシュなのかチェックする
            //開発環境で読み込もうとするとなぜか無駄についてきてしまうっぽい
            //対処法は今の所わかってない...
            var subStr = value.substring(0, 1);
            if (subStr.equals("\\") || subStr.equals("/")){
                value = value.substring(1);
            }
        }
        //クラスとして読み込むためにバックスラッシュをスラッシュに変換
        return value.replace("\\", "/");
    }
}
