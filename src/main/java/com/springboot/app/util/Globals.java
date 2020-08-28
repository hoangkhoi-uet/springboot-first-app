package com.springboot.app.util;

import java.io.File;
import javax.servlet.http.HttpServletRequest;

public class Globals {
    public static final String DOC = "doc";

    public static final String DOC_PATH = ClassLoaderUtil.getClassPath() + File.separator + DOC;


}
