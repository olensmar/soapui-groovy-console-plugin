package com.smartbear.soapui.groovy;

import com.eviware.soapui.plugins.PluginAdapter;
import com.eviware.soapui.plugins.PluginConfiguration;

/**
 * Created by ole on 08/06/14.
 */

@PluginConfiguration(groupId = "com.smartbear.soapui.plugins", name = "Groovy Console Plugin", version = "1.1",
        autoDetect = true, description = "Adds an interactive Groovy Console to SoapUI",
        infoUrl = "https://github.com/olensmar/soapui-groovy-plugin")
public class PluginConfig extends PluginAdapter {

}
