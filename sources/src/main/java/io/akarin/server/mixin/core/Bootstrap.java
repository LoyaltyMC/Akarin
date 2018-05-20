package io.akarin.server.mixin.core;

import java.io.PrintStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.craftbukkit.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Main.class, remap = false)
public class Bootstrap {
    private final static Logger logger = LogManager.getLogger("Akarin");
    
    @Inject(method = "main([Ljava/lang/String;)V", at = @At("HEAD"))
    private static void configureMixin(CallbackInfo info) {
        ;
    }
    
    /*
     * Notify message
     */
    @Redirect(method = "main", at = @At(
            value = "INVOKE_STRING",
            target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V",
            args = "ldc=*** Warning, you've not updated in a while! ***"
    ))
    private static void notifyUpdate(PrintStream stream, String text) {
        logger.warn("You've not updated in a while, the current version may outdated");
    }
    
    @Redirect(method = "main", at = @At(
            value = "INVOKE_STRING",
            target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V",
            args = "ldc=*** Please download a new build as per instructions from https://paperci.emc.gs/ ***"
    ))
    private static void notifyWebsite(PrintStream stream, String text) {
        logger.warn("Visit our website for latest information https://akarin.io/");
    }
    
    @Redirect(method = "main", at = @At(
            value = "INVOKE_STRING",
            target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V",
            args = "ldc=Loading libraries, please wait..."
    ))
    private static void notifyLoading(PrintStream stream, String text) {
        logger.info("Loading libraries, please wait..");
    }
}