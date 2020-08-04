package com.files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.files.models.Session;

public class FilesClient {

    private static final Logger log = LogManager.getLogger(FilesClient.class);
    private FilesConfig filesConfig = FilesConfig.getInstance();

    public FilesClient() {

    }

    public void setApiKey(String apiKey) {
        filesConfig.setApiKey(apiKey);
    }

    public void setSession(Session session) {

    }

    public void setProperty(String property, String value) {
        filesConfig.setProperty(property, value);
    }
}
