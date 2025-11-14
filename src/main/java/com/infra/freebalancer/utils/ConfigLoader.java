package com.infra.freebalancer.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.logging.*;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.core.io.ClassPathResource;

/* config file location: com/infra/freebalancer/resources/config.json
 * Sample Config: 
 * {
 *    "type": "roundrobin",
 *    "nodes": [
 *      {"address": "127.0.0.1", "port": 80},
 *      {"address": "127.0.0.2", "port": 80}
 *    ]
 * }
 *
 * Environment variable: FREEBALANCER_CONFIG_FILE=<path>/<to>/<config>/<file>
 * Default: com/infra/freebalancer/resources/config.json
 *
 * */

@AllArgsConstructor
@Data
public class ConfigLoader {

  private static Logger logger;
  private static String configFilePath = "config.json";
  private static String jsonFileContent = null;

  private static boolean readJsonFile(ClassPathResource clps) {
    try (InputStream istream = clps.getInputStream()) {
      jsonFileContent = new String(istream.readAllBytes());
      if (jsonFileContent.isEmpty()) {
        logger.fine("Unable to load JSON config. File seems empty");
        return false;
      }
      logger.fine("Loaded config from config.json");
      return true;
    } catch (IOException ioex) {
      logger.fine("Exception when attempting to read config file: " + ioex);
    }
    return false;
  }

  public static Config getConfig() {
    ClassPathResource configFile = new ClassPathResource(configFilePath);
    Config config = null;

    if (!configFile.exists() || !configFile.isFile()) {
      logger.fine("Invalid config file (config.json). File does not seem to exist or is not a valid file");
      return null;
    }

    if (!readJsonFile(configFile)) {
      logger.fine("Unable to read JSON config File");
      return null;
    }

    ObjectMapper objMapper = new ObjectMapper();
    try {
      config = objMapper.readValue(jsonFileContent, Config.class);
      logger.fine("Generated Config class");
    } catch (Exception jsonmex) {
      logger.finest("Failed to properly map JSON config to JAVA object: " + jsonmex);
    }

    return config;
  }
}
