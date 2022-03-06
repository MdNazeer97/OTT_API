package com.api.framework.util;

import java.io.File;

public interface Constant {

    String JSON_PATH = System.getProperty("user.dir") + File.separator + "target"  + File.separator;

    String ASSETS_PATH = System.getProperty("user.dir") + File.separator + "src/main/resources/assets" + File.separator;
}
