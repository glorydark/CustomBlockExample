package cn.glorydark.nukkit.test.blocks;

import cn.nukkit.block.custom.CustomBlockDefinition;
import cn.nukkit.block.custom.CustomBlockManager;
import cn.nukkit.block.custom.container.CustomBlock;
import cn.nukkit.block.custom.container.data.Materials;
import cn.nukkit.math.Vector3f;
import cn.nukkit.network.protocol.types.inventory.creative.CreativeItemCategory;

/**
 * @author glorydark
 */
public class TestCustomBlock extends CustomBlock {

    public static final int ID = 14000; // numeric id: 255-14000= -13745

    public static final String NAME = "test:test_block";

    public static void register() {
        Materials materials = Materials.builder()
                .any(Materials.RenderMethod.BLEND, "test_block");

        CustomBlockManager.get().registerCustomBlock(
                NAME, ID,
                CustomBlockDefinition.builder(new TestCustomBlock())
                        .creativeCategory(CreativeItemCategory.CONSTRUCTION)
                        .materials(materials)
                        .geometry("geometry.test_block")
                        .collisionBox(
                                new Vector3f(-0.8f, 0.0f, -0.8f),
                                new Vector3f(16.0f, 1.0f, 16.0f)
                        )
                        .selectionBox(
                                new Vector3f(-0.8f, 0.0f, -0.8f),
                                new Vector3f(16.0f, 1.0f, 16.0f)
                        )
                        .build(),
                TestCustomBlock::new
        );
    }

    public TestCustomBlock() {
        super(NAME, ID);
    }
}
