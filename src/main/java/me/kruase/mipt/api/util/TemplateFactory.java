package me.kruase.mipt.api.util;


import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import me.kruase.mipt.Main;
import spark.template.freemarker.FreeMarkerEngine;


public final class TemplateFactory {
    public static FreeMarkerEngine freeMarkerEngine() {
        Configuration freeMarkerConfiguration = new Configuration(Configuration.VERSION_2_3_0);
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(freeMarkerConfiguration);
        freeMarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(Main.class, "/"));
        return freeMarkerEngine;
    }
}
