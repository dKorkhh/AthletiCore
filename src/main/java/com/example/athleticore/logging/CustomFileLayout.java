package com.example.athleticore.logging;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Plugin(name = "CustomFileLayout", category = "Core", elementType = "layout", printObject = true)
public class CustomFileLayout extends AbstractStringLayout {

    protected CustomFileLayout() {
        super(StandardCharsets.UTF_8);
    }

    @PluginFactory
    public static CustomFileLayout createLayout() {
        return new CustomFileLayout();
    }

    @Override
    public String toSerializable(LogEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"timestamp\":\"").append(event.getTimeMillis()).append("\",");
        sb.append("\"level\":\"").append(event.getLevel()).append("\",");
        sb.append("\"thread\":\"").append(event.getThreadName()).append("\",");
        sb.append("\"message\":\"").append(event.getMessage().getFormattedMessage()).append("\",");

        Map<String, String> mdc = event.getContextMap();
        if (!mdc.isEmpty()) {
            sb.append("\"mdc\":{");
            mdc.forEach((key, value) -> sb.append("\"").append(key).append("\":\"").append(value).append("\","));
            sb.deleteCharAt(sb.length() - 1);
            sb.append("},");
        }

        if (event.getMarker() != null) {
            sb.append("\"marker\":\"").append(event.getMarker().getName()).append("\",");
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append("}\n");
        return sb.toString();
    }
}