package com.infra.freebalancer.utils;

import com.fasterxml.jackson.core.type.TypeReference;
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

  private static Logger logger = Logger.getLogger("ConfigLoader");
  private static String configFilePath = "config.json";
  private static JsonNode jsonFileContent = null;
  private static Config lbConfig = null;

  private static boolean readJsonFile(ClassPathResource clps) {
    try (InputStream istream = clps.getInputStream()) {
        ObjectMapper objMapper = new ObjectMapper();
      lbConfig = (objMapper.readValue(istream, new TypeReference<Config>(){}));
      if (lbConfig == null) {
        System.out.println("Unable to load JSON config. File seems empty");
        return false;
      }
      System.out.println("Loaded config from config.json");
      return true;
    } catch (IOException ioex) {
      System.out.println("Exception when attempting to read config file: " + ioex);
    }
    return false;
  }

  public static Config getConfig() {
    ClassPathResource configFile = new ClassPathResource(configFilePath);

    if (!configFile.exists() || !configFile.isFile()) {
      System.out.println("Invalid config file (config.json). File does not seem to exist or is not a valid file");
      return null;
    }

    if (!readJsonFile(configFile)) {
      System.out.println("Unable to read JSON config File");
      return null;
    }
    return lbConfig;
  }
}
