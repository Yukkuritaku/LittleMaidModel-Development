package com.github.yukkuritaku.littlemaidmodeldevelopment.mixin;

import com.github.yukkuritaku.littlemaidmodeldevelopment.LMMDMod;
import dev.architectury.platform.Platform;
import net.sistr.littlemaidmodelloader.resource.classloader.MultiModelClassTransformer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(value = MultiModelClassTransformer.class, remap = false)
public class MultiModelClassTransformerMixin {

    @Shadow(remap = false) @Final private static Map<String, String> CODE_REPLACE_MAP;

    @Inject(method = "<clinit>", at = @At(
            value = "TAIL",
            remap = false
    ), remap = false)
    private static void injectStatic(CallbackInfo ci){
        if (Platform.isDevelopmentEnvironment()) {
            boolean isOfficialMapping = true;
            try {
                //net.minecraft.world.entity.Entityが存在しているかチェックする(Fabric側だとチェックする方法が無かった)
                Class.forName("net.minecraft.world.entity.Entity", false, MultiModelClassTransformer.class.getClassLoader());
            } catch (ClassNotFoundException e) {
                isOfficialMapping = false;
                LMMDMod.LOGGER.info("Official or Parchment mapping not found, detected yarn or other mapping.");
            }
            if (isOfficialMapping) {
                LMMDMod.LOGGER.info("Official or Parchment mapping found, replace some transformers.");
                //OfficialかParchmentマッピングだとEntityはnet/minecraft/entity/Entityにないからnet/minecraft/world/entity/Entityに変更させる
                //これをしないともしLMMLResourcesからZipでモデルを読み込むときにClassNotFoundExceptionが例外スローされてしまう(開発環境だとあまりないと思うけど一応ね！)
                LMMDMod.LOGGER.info("{} -> {}", "net/minecraft/entity/Entity", "net/minecraft/world/entity/Entity");
                CODE_REPLACE_MAP.put("net/minecraft/entity/Entity", "net/minecraft/world/entity/Entity");
                //以下同様ですの
                LMMDMod.LOGGER.info("{} -> {}", "net/minecraft/entity/EntityLivingBase", "net/minecraft/world/entity/LivingEntity");
                CODE_REPLACE_MAP.put("net/minecraft/entity/EntityLivingBase", "net/minecraft/world/entity/LivingEntity");
                LMMDMod.LOGGER.info("{} -> {}", "net/minecraft/entity/passive/EntityAnimal", "net/minecraft/world/entity/animal/Animal");
                CODE_REPLACE_MAP.put("net/minecraft/entity/passive/EntityAnimal", "net/minecraft/world/entity/animal/Animal");
                LMMDMod.LOGGER.info("{} -> {}", "net/minecraft/entity/player/EntityPlayer", "net/minecraft/world/entity/player/Player");
                CODE_REPLACE_MAP.put("net/minecraft/entity/player/EntityPlayer", "net/minecraft/world/entity/player/Player");
            }
        }
    }
}
