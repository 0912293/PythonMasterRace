package com.steen.Util;

import org.apache.velocity.app.VelocityEngine;
import com.steen.velocity.VelocityTemplateEngine;

/**
 * Created by jesse on 31-10-2016.
 */

public class ViewUtil {

    public static VelocityTemplateEngine strictVelocityEngine() {
        VelocityEngine configuredEngine = new VelocityEngine();
        configuredEngine.setProperty("runtime.references.strict", true);
        configuredEngine.setProperty("resource.loader", "class");
        configuredEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return new VelocityTemplateEngine(configuredEngine);
    }
}
