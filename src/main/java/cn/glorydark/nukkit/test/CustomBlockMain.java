package cn.glorydark.nukkit.test;

import cn.glorydark.nukkit.test.blocks.TestCustomBlock;
import cn.nukkit.plugin.PluginBase;

/**
 * @author glorydark
 */
public class CustomBlockMain extends PluginBase {

    @Override
    public void onLoad() {
        TestCustomBlock.register();
    }

    @Override
    public void onEnable() {
        this.getLogger().info("TestCustomBlock Enabled");
    }
}