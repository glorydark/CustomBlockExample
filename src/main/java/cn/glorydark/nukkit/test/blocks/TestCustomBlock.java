package cn.glorydark.nukkit.test.blocks;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.custom.CustomBlockDefinition;
import cn.nukkit.block.custom.CustomBlockManager;
import cn.nukkit.block.custom.container.CustomBlockMeta;
import cn.nukkit.block.custom.container.data.*;
import cn.nukkit.block.custom.properties.BlockProperties;
import cn.nukkit.block.custom.properties.IntBlockProperty;
import cn.nukkit.item.Item;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.Vector3f;
import cn.nukkit.network.protocol.types.inventory.creative.CreativeItemCategory;

/**
 * @author glorydark
 */
public class TestCustomBlock extends CustomBlockMeta {

    protected static final IntBlockProperty LINK_DIRECTION =
            new IntBlockProperty("glorydark:link_direction", false, 5);

    protected static final BlockProperties PROPERTIES = new BlockProperties(LINK_DIRECTION);

    public static final int ID = 14000; // numeric id: 255-14000= -13745

    public static final String NAME = "test:test_block";

    public TestCustomBlock() {
        super(NAME, ID, PROPERTIES);
    }

    public TestCustomBlock(int meta) {
        super(NAME, ID, PROPERTIES, meta);
    }


    public static void register() {
        Materials materials = Materials.builder()
                .any(Materials.RenderMethod.BLEND, "gold_block");

        CustomBlockManager.get().registerCustomBlock(
                NAME, ID,
                CustomBlockDefinition.builder(new TestCustomBlock())
                        .creativeCategory(CreativeItemCategory.CONSTRUCTION)
                        .materials(materials)
                        .geometry("geometry.test_block")
                        // 默认碰撞箱和选择箱（用于地面放置）
                        .collisionBox(
                                new Vector3f(-4f, 0.0f, -4f),
                                new Vector3f(8.0f, 8.0f, 8.0f)
                        )
                        .selectionBox(
                                new Vector3f(-4f, 0.0f, -4f),
                                new Vector3f(8.0f, 8.0f, 8.0f)
                        )
                        .permutations(
                                // 0: 放置在地面上（默认情况）
                                new Permutation(
                                        Component.builder()
                                                .collisionBox(
                                                        new CollisionBox(
                                                                -4f, 0.0f, -4f,
                                                                8.0f, 8.0f, 8.0f
                                                        )
                                                )
                                                .selectionBox(
                                                        new SelectionBox(
                                                                -4f, 0.0f, -4f,
                                                                8.0f, 8.0f, 8.0f
                                                        )
                                                )
                                                .build(),
                                        "query.block_property('glorydark:link_direction') == 0"
                                ),

                                // 1: 放置在天花板上（上下翻转）
                                new Permutation(
                                        Component.builder()
                                                .collisionBox(
                                                        new CollisionBox(
                                                                -4f, -8.0f, -4f,  // Y轴下移8个单位
                                                                8.0f, 8.0f, 8.0f
                                                        )
                                                )
                                                .selectionBox(
                                                        new SelectionBox(
                                                                -4f, -8.0f, -4f,
                                                                8.0f, 8.0f, 8.0f
                                                        )
                                                )
                                                .build(),
                                        "query.block_property('glorydark:link_direction') == 1"
                                ),

                                // 2: 贴在朝北的墙面上（NORTH）
                                new Permutation(
                                        Component.builder()
                                                .collisionBox(
                                                        new CollisionBox(
                                                                -4f, -4f, 0.0f,  // Z轴向前（北方向）
                                                                8.0f, 8.0f, 8.0f
                                                        )
                                                )
                                                .selectionBox(
                                                        new SelectionBox(
                                                                -4f, -4f, 0.0f,
                                                                8.0f, 8.0f, 8.0f
                                                        )
                                                )
                                                .build(),
                                        "query.block_property('glorydark:link_direction') == 2"
                                ),

                                // 3: 贴在朝南的墙面上（SOUTH）
                                new Permutation(
                                        Component.builder()
                                                .collisionBox(
                                                        new CollisionBox(
                                                                -4f, -4f, -8.0f,  // Z轴向后（南方向）
                                                                8.0f, 8.0f, 8.0f
                                                        )
                                                )
                                                .selectionBox(
                                                        new SelectionBox(
                                                                -4f, -4f, -8.0f,
                                                                8.0f, 8.0f, 8.0f
                                                        )
                                                )
                                                .build(),
                                        "query.block_property('glorydark:link_direction') == 3"
                                ),

                                // 4: 贴在朝西的墙面上（WEST）
                                new Permutation(
                                        Component.builder()
                                                .collisionBox(
                                                        new CollisionBox(
                                                                0.0f, -4f, -4f,  // X轴向右（西方向）
                                                                8.0f, 8.0f, 8.0f
                                                        )
                                                )
                                                .selectionBox(
                                                        new SelectionBox(
                                                                0.0f, -4f, -4f,
                                                                8.0f, 8.0f, 8.0f
                                                        )
                                                )
                                                .build(),
                                        "query.block_property('glorydark:link_direction') == 4"
                                ),

                                // 5: 贴在朝东的墙面上（EAST）
                                new Permutation(
                                        Component.builder()
                                                .collisionBox(
                                                        new CollisionBox(
                                                                -8.0f, -4f, -4f,  // X轴向左（东方向）
                                                                8.0f, 8.0f, 8.0f
                                                        )
                                                )
                                                .selectionBox(
                                                        new SelectionBox(
                                                                -8.0f, -4f, -4f,
                                                                8.0f, 8.0f, 8.0f
                                                        )
                                                )
                                                .build(),
                                        "query.block_property('glorydark:link_direction') == 5"
                                )
                        )
                        .build(),
                TestCustomBlock::new
        );
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        // 根据点击的面确定方向
        int direction;

        switch (face) {
            case UP:    // 点击方块的上表面
                direction = 0;  // 放在地面上
                break;
            case DOWN:  // 点击方块的下表面
                direction = 1;  // 放在天花板上
                break;
            case NORTH: // 点击方块的北面
                direction = 2;  // 贴在朝北的墙上
                break;
            case SOUTH: // 点击方块的南面
                direction = 3;  // 贴在朝南的墙上
                break;
            case WEST:  // 点击方块的西面
                direction = 4;  // 贴在朝西的墙上
                break;
            case EAST:  // 点击方块的东面
                direction = 5;  // 贴在朝东的墙上
                break;
            default:
                direction = 0;
        }

        // 设置方块状态
        this.setPropertyValue(LINK_DIRECTION, direction);

        return super.place(item, block, target, face, fx, fy, fz, player);
    }

    @Override
    public Item[] getDrops(Item item) {
        Item dropItem = this.toItem();
        dropItem.setDamage(0);
        return new Item[]{ dropItem };
    }
}