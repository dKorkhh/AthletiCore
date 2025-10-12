package com.example.athleticore.logging;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Plugin(name = "CustomFileAppender", category = "Core", elementType = "appender", printObject = true)
public class CustomFileAppender extends AbstractAppender {

    private final String fileName;
    private static final org.apache.logging.log4j.Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(CustomFileAppender.class);

    protected CustomFileAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, String fileName) {
        super(name, filter, layout, ignoreExceptions, null);
        this.fileName = fileName;
    }

    @PluginFactory
    public static CustomFileAppender createAppender(
            @PluginAttribute("name") @Required String name,
            @PluginElement("Filter") Filter filter,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginAttribute("fileName") @Required String fileName) {
        if (name == null || fileName == null || layout == null) {
            return null;
        }
        return new CustomFileAppender(name, filter, layout, true, fileName);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void append(LogEvent event) {
        try {
            byte[] bytes = getLayout().toByteArray(event);
            Files.write(Paths.get(fileName), bytes, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            LOGGER.error("Write failed", e);
        }
    }
}