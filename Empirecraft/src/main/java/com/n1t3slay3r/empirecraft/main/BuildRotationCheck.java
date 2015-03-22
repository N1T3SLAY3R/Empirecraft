/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.main;

import java.util.Arrays;
import java.util.List;
import org.bukkit.CropState;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.SandstoneType;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import static org.bukkit.block.BlockFace.DOWN;
import static org.bukkit.block.BlockFace.EAST;
import static org.bukkit.block.BlockFace.NORTH;
import static org.bukkit.block.BlockFace.SOUTH;
import static org.bukkit.block.BlockFace.UP;
import static org.bukkit.block.BlockFace.WEST;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.material.Bed;
import org.bukkit.material.Button;
import org.bukkit.material.Chest;
import org.bukkit.material.Crops;
import org.bukkit.material.DetectorRail;
import org.bukkit.material.Dispenser;
import org.bukkit.material.Furnace;
import org.bukkit.material.Gate;
import org.bukkit.material.Ladder;
import org.bukkit.material.Lever;
import org.bukkit.material.PistonBaseMaterial;
import org.bukkit.material.PoweredRail;
import org.bukkit.material.Pumpkin;
import org.bukkit.material.Rails;
import org.bukkit.material.Sandstone;
import org.bukkit.material.Stairs;
import org.bukkit.material.Step;
import org.bukkit.material.Torch;
import org.bukkit.material.TrapDoor;
import org.bukkit.material.Tree;
import org.bukkit.material.TripwireHook;
import org.bukkit.material.WoodenStep;
import org.bukkit.material.Wool;

/**
 *
 * @author Dylan Malec
 */
public class BuildRotationCheck {

    //Blocks that might not have the right direction (ex. stairs)
    private static final List<String> blocks = Arrays.asList("ACACIA_STAIRS", "BIRCH_WOOD_STAIRS", "BRICK_STAIRS", "ACACIA_STAIRS", "BIRCH_WOOD_STAIRS", "BRICK_STAIRS", "COBBLESTONE_STAIRS", "JUNGLE_WOOD_STAIRS", "JUNGLE_WOOD_STAIRS", "JUNGLE_WOOD_STAIRS", "JUNGLE_WOOD_STAIRS", "NETHER_BRICK_STAIRS", "QUARTZ_STAIRS", "SANDSTONE_STAIRS", "SANDSTONE", "SMOOTH_STAIRS", "SPRUCE_WOOD_STAIRS", "WOOD_STAIRS", "CHEST", "ACTIVATOR_RAIL", "POWERED_RAIL", "DROPPER", "DISPENSER", "WOOD", "WOOL", "LOG", "DETECTOR_RAIL", "PISTON_BASE", "PISTON_STICKY_BASE", "STEP", "WOOD_STEP", "TORCH", "REDSTONE_TORCH_OFF", "REDSTONE_TORCH_ON", "LADDER", "FURNACE", "BURNING_FURNACE", "RAILS", "PUMPKIN", "JACK_O_LANTERN", "FENCE_GATE", "BED", "BED_BLOCK");

    public static void Set(String direction, Block block, Material mat, Integer cy, Integer x, Integer z, FileConfiguration tempyaml) {
        if (mat == Material.STEP) {
            Step type = new Step(0, block.getData());
            if (tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".dat")) {
                type.setInverted(true);
            } else {
                type.setInverted(false);
            }
            switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".typ")) {
                case "COBBLESTONE":
                    type.setMaterial(Material.COBBLESTONE);
                    break;
                case "STONE":
                    type.setMaterial(Material.STONE);
                    break;
                case "SANDSTONE":
                    type.setMaterial(Material.SANDSTONE);
                    break;
                case "BRICK":
                    type.setMaterial(Material.BRICK);
                    break;
                case "SMOOTH_BRICK":
                    type.setMaterial(Material.SMOOTH_BRICK);
                    break;
                case "QUARTZ":
                    type.setMaterial(Material.QUARTZ_BLOCK);
                    break;
                case "NETHER_BRICK":
                    type.setMaterial(Material.NETHER_BRICK);
                    break;
            }
            block.setData(type.getData());
        } else if (mat == Material.WOOD_STEP) {
            WoodenStep type = new WoodenStep(0, block.getData());
            if (tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".dat")) {
                type.setInverted(true);
            } else {
                type.setInverted(false);
            }
            switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".typ")) {
                case "ACACIA":
                    type.setSpecies(TreeSpecies.ACACIA);
                    break;
                case "BIRCH":
                    type.setSpecies(TreeSpecies.BIRCH);
                    break;
                case "DARK_OAK":
                    type.setSpecies(TreeSpecies.DARK_OAK);
                    break;
                case "GENERIC":
                    type.setSpecies(TreeSpecies.GENERIC);
                    break;
                case "JUNGLE":
                    type.setSpecies(TreeSpecies.JUNGLE);
                    break;
                case "REDWOOD":
                    type.setSpecies(TreeSpecies.REDWOOD);
                    break;
            }
            block.setData(type.getData());
        } else if (mat == Material.WOOL) {
            Wool type = new Wool(block.getData());
            switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".typ")) {
                case "BLACK":
                    type.setColor(DyeColor.BLACK);
                    break;
                case "BLUE":
                    type.setColor(DyeColor.BLUE);
                    break;
                case "BROWN":
                    type.setColor(DyeColor.BROWN);
                    break;
                case "CYAN":
                    type.setColor(DyeColor.CYAN);
                    break;
                case "GRAY":
                    type.setColor(DyeColor.GRAY);
                    break;
                case "GREEN":
                    type.setColor(DyeColor.GREEN);
                    break;
                case "LIGHT_BLUE":
                    type.setColor(DyeColor.LIGHT_BLUE);
                    break;
                case "LIME":
                    type.setColor(DyeColor.LIME);
                    break;
                case "MAGENTA":
                    type.setColor(DyeColor.MAGENTA);
                    break;
                case "ORANGE":
                    type.setColor(DyeColor.ORANGE);
                    break;
                case "PINK":
                    type.setColor(DyeColor.PINK);
                    break;
                case "PURPLE":
                    type.setColor(DyeColor.PURPLE);
                    break;
                case "RED":
                    type.setColor(DyeColor.RED);
                    break;
                case "SILVER":
                    type.setColor(DyeColor.SILVER);
                    break;
                case "WHITE":
                    type.setColor(DyeColor.WHITE);
                    break;
                case "YELLOW":
                    type.setColor(DyeColor.YELLOW);
                    break;
            }
            block.setData(type.getData());
        } else if (mat == Material.SANDSTONE) {
            Sandstone type = new Sandstone(block.getData());
            switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".typ")) {
                case "CRACKED":
                    type.setType(SandstoneType.CRACKED);
                    break;
                case "GLYPHED":
                    type.setType(SandstoneType.GLYPHED);
                    break;
                case "SMOOTH":
                    type.setType(SandstoneType.SMOOTH);
                    break;
            }
            block.setData(type.getData());
        } else if (mat == Material.CROPS) {
            Crops type = new Crops(block.getData());
            switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".typ")) {
                case "SEEDED":
                    type.setState(CropState.SEEDED);
                    break;
                case "GERMINATED":
                    type.setState(CropState.GERMINATED);
                    break;
                case "VERY_SMALL":
                    type.setState(CropState.VERY_SMALL);
                    break;
                case "SMALL":
                    type.setState(CropState.SMALL);
                    break;
                case "MEDIUM":
                    type.setState(CropState.MEDIUM);
                    break;
                case "TALL":
                    type.setState(CropState.TALL);
                    break;
                case "VERY_TALL":
                    type.setState(CropState.VERY_TALL);
                    break;
                case "RIPE":
                    type.setState(CropState.RIPE);
                    break;
            }
            block.setData(type.getData());
        } else {
            int r = 0;
            if (direction.equalsIgnoreCase("e")) {
                r = 1;
            } else if (direction.equalsIgnoreCase("s")) {
                r = 2;
            } else if (direction.equalsIgnoreCase("w")) {
                r = 3;
            }
            if (mat == Material.ACACIA_STAIRS || mat == (Material.BIRCH_WOOD_STAIRS) || mat == Material.BRICK_STAIRS || mat == Material.COBBLESTONE_STAIRS || mat == Material.JUNGLE_WOOD_STAIRS || mat == Material.NETHER_BRICK_STAIRS || mat == Material.QUARTZ_STAIRS || mat == Material.SANDSTONE_STAIRS || mat == Material.SMOOTH_STAIRS || mat == Material.SPRUCE_WOOD_STAIRS || mat == Material.WOOD_STAIRS) {
                Stairs type = new Stairs(0, block.getData());
                if (tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".inv")) {
                    type.setInverted(true);
                } else {
                    type.setInverted(false);
                }
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.CHEST) {
                Chest type = new Chest(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.ACTIVATOR_RAIL || mat == Material.POWERED_RAIL) {
                PoweredRail type = new PoweredRail(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setDirection(NORTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setDirection(EAST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setDirection(SOUTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                } else {
                    type.setDirection(WEST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                }
            } else if (mat == Material.DROPPER || mat == Material.DISPENSER) {
                Dispenser type = new Dispenser(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.WOOD || mat == Material.LOG) {
                Tree type = new Tree(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".typ")) {
                    case "ACACIA":
                        type.setSpecies(TreeSpecies.ACACIA);
                        break;
                    case "BIRCH":
                        type.setSpecies(TreeSpecies.BIRCH);
                        break;
                    case "DARK_OAK":
                        type.setSpecies(TreeSpecies.DARK_OAK);
                        break;
                    case "GENERIC":
                        type.setSpecies(TreeSpecies.GENERIC);
                        break;
                    case "JUNGLE":
                        type.setSpecies(TreeSpecies.JUNGLE);
                        break;
                    case "REDWOOD":
                        type.setSpecies(TreeSpecies.REDWOOD);
                        break;
                }
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                    case "UP":
                        r = 8;
                        break;
                    case "DOWN":
                        r = 9;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setDirection(SOUTH);
                    block.setData(type.getData());
                } else if (r == 3 || r == 7) {
                    type.setDirection(WEST);
                    block.setData(type.getData());
                } else if (r == 8) {
                    type.setDirection(UP);
                    block.setData(type.getData());
                } else if (r == 9) {
                    type.setDirection(DOWN);
                    block.setData(type.getData());
                }
            } else if (mat == Material.DETECTOR_RAIL) {
                DetectorRail type = new DetectorRail(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setDirection(NORTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setDirection(EAST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setDirection(SOUTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                } else {
                    type.setDirection(WEST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                }
            } else if (mat == Material.PISTON_BASE || mat == Material.PISTON_STICKY_BASE) {
                PistonBaseMaterial type = new PistonBaseMaterial(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                    case "UP":
                        r = 8;
                        break;
                    case "DOWN":
                        r = 9;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else if (r == 3 || r == 7) {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                } else if (r == 8) {
                    type.setFacingDirection(UP);
                    block.setData(type.getData());
                } else if (r == 9) {
                    type.setFacingDirection(DOWN);
                    block.setData(type.getData());
                }
            } else if (mat == Material.TORCH || mat == Material.REDSTONE_TORCH_OFF || mat == Material.REDSTONE_TORCH_ON) {
                Torch type = new Torch(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
                block.getState().update(true, false);
            } else if (mat == Material.LADDER) {
                Ladder type = new Ladder(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.FURNACE || mat == Material.BURNING_FURNACE) {
                Furnace type = new Furnace(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.RAILS) {
                Rails type = new Rails(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setDirection(NORTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setDirection(EAST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setDirection(SOUTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                } else {
                    type.setDirection(WEST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                }
            } else if (mat == Material.PUMPKIN || mat == Material.JACK_O_LANTERN) {
                Pumpkin type = new Pumpkin(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.FENCE_GATE) {
                Gate type = new Gate(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.BED || mat == Material.BED_BLOCK) {
                Bed type = new Bed(0, block.getData());
                if (tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".bed")) {
                    type.setHeadOfBed(true);
                }
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.WOOD_BUTTON || mat == Material.STONE_BUTTON) {
                Button type = new Button(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.TRAP_DOOR || mat == Material.IRON_TRAPDOOR) {
                TrapDoor type = new TrapDoor(0, block.getData());
                if (tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".ope")) {
                    type.setOpen(true);
                } else {
                    type.setOpen(false);
                }
                if (tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".inv")) {
                    type.setInverted(true);
                } else {
                    type.setInverted(false);
                }
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.TRIPWIRE_HOOK) {
                TripwireHook type = new TripwireHook(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.LEVER) {
                Lever type = new Lever(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
            }
        }
    }

    public static void Set(String direction, Block block, Material mat, Integer cy, Integer x, Integer z, FileConfiguration tempyaml, Integer cx, Integer cz) {
        if (mat == Material.STEP) {
            Step type = new Step(0, block.getData());
            if (tempyaml.getBoolean("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".dat")) {
                type.setInverted(true);
            } else {
                type.setInverted(false);
            }
            switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".typ")) {
                case "COBBLESTONE":
                    type.setMaterial(Material.COBBLESTONE);
                    break;
                case "STONE":
                    type.setMaterial(Material.STONE);
                    break;
                case "SANDSTONE":
                    type.setMaterial(Material.SANDSTONE);
                    break;
                case "BRICK":
                    type.setMaterial(Material.BRICK);
                    break;
                case "SMOOTH_BRICK":
                    type.setMaterial(Material.SMOOTH_BRICK);
                    break;
                case "QUARTZ":
                    type.setMaterial(Material.QUARTZ_BLOCK);
                    break;
                case "NETHER_BRICK":
                    type.setMaterial(Material.NETHER_BRICK);
                    break;
            }
            block.setData(type.getData());
        } else if (mat == Material.WOOD_STEP) {
            WoodenStep type = new WoodenStep(0, block.getData());
            if (tempyaml.getBoolean("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".dat")) {
                type.setInverted(true);
            } else {
                type.setInverted(false);
            }
            switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".typ")) {
                case "ACACIA":
                    type.setSpecies(TreeSpecies.ACACIA);
                    break;
                case "BIRCH":
                    type.setSpecies(TreeSpecies.BIRCH);
                    break;
                case "DARK_OAK":
                    type.setSpecies(TreeSpecies.DARK_OAK);
                    break;
                case "GENERIC":
                    type.setSpecies(TreeSpecies.GENERIC);
                    break;
                case "JUNGLE":
                    type.setSpecies(TreeSpecies.JUNGLE);
                    break;
                case "REDWOOD":
                    type.setSpecies(TreeSpecies.REDWOOD);
                    break;
            }
            block.setData(type.getData());
        } else if (mat == Material.WOOL) {
            Wool type = new Wool(block.getData());
            switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".typ")) {
                case "BLACK":
                    type.setColor(DyeColor.BLACK);
                    break;
                case "BLUE":
                    type.setColor(DyeColor.BLUE);
                    break;
                case "BROWN":
                    type.setColor(DyeColor.BROWN);
                    break;
                case "CYAN":
                    type.setColor(DyeColor.CYAN);
                    break;
                case "GRAY":
                    type.setColor(DyeColor.GRAY);
                    break;
                case "GREEN":
                    type.setColor(DyeColor.GREEN);
                    break;
                case "LIGHT_BLUE":
                    type.setColor(DyeColor.LIGHT_BLUE);
                    break;
                case "LIME":
                    type.setColor(DyeColor.LIME);
                    break;
                case "MAGENTA":
                    type.setColor(DyeColor.MAGENTA);
                    break;
                case "ORANGE":
                    type.setColor(DyeColor.ORANGE);
                    break;
                case "PINK":
                    type.setColor(DyeColor.PINK);
                    break;
                case "PURPLE":
                    type.setColor(DyeColor.PURPLE);
                    break;
                case "RED":
                    type.setColor(DyeColor.RED);
                    break;
                case "SILVER":
                    type.setColor(DyeColor.SILVER);
                    break;
                case "WHITE":
                    type.setColor(DyeColor.WHITE);
                    break;
                case "YELLOW":
                    type.setColor(DyeColor.YELLOW);
                    break;
            }
            block.setData(type.getData());
        } else if (mat == Material.SANDSTONE) {
            Sandstone type = new Sandstone(block.getData());
            switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".typ")) {
                case "CRACKED":
                    type.setType(SandstoneType.CRACKED);
                    break;
                case "GLYPHED":
                    type.setType(SandstoneType.GLYPHED);
                    break;
                case "SMOOTH":
                    type.setType(SandstoneType.SMOOTH);
                    break;
            }
            block.setData(type.getData());
        } else if (mat == Material.CROPS) {
            Crops type = new Crops(block.getData());
            switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".typ")) {
                case "SEEDED":
                    type.setState(CropState.SEEDED);
                    break;
                case "GERMINATED":
                    type.setState(CropState.GERMINATED);
                    break;
                case "VERY_SMALL":
                    type.setState(CropState.VERY_SMALL);
                    break;
                case "SMALL":
                    type.setState(CropState.SMALL);
                    break;
                case "MEDIUM":
                    type.setState(CropState.MEDIUM);
                    break;
                case "TALL":
                    type.setState(CropState.TALL);
                    break;
                case "VERY_TALL":
                    type.setState(CropState.VERY_TALL);
                    break;
                case "RIPE":
                    type.setState(CropState.RIPE);
                    break;
            }
            block.setData(type.getData());
        } else {
            int r = 0;
            if (direction.equalsIgnoreCase("e")) {
                r = 1;
            } else if (direction.equalsIgnoreCase("s")) {
                r = 2;
            } else if (direction.equalsIgnoreCase("w")) {
                r = 3;
            }
            if (mat == Material.ACACIA_STAIRS || mat == (Material.BIRCH_WOOD_STAIRS) || mat == Material.BRICK_STAIRS || mat == Material.COBBLESTONE_STAIRS || mat == Material.JUNGLE_WOOD_STAIRS || mat == Material.NETHER_BRICK_STAIRS || mat == Material.QUARTZ_STAIRS || mat == Material.SANDSTONE_STAIRS || mat == Material.SMOOTH_STAIRS || mat == Material.SPRUCE_WOOD_STAIRS || mat == Material.WOOD_STAIRS) {
                Stairs type = new Stairs(0, block.getData());
                if (tempyaml.getBoolean("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".inv")) {
                    type.setInverted(true);
                } else {
                    type.setInverted(false);
                }
                switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.CHEST) {
                Chest type = new Chest(0, block.getData());
                switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.ACTIVATOR_RAIL || mat == Material.POWERED_RAIL) {
                PoweredRail type = new PoweredRail(0, block.getData());
                switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setDirection(NORTH, tempyaml.getBoolean("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setDirection(EAST, tempyaml.getBoolean("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setDirection(SOUTH, tempyaml.getBoolean("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                } else {
                    type.setDirection(WEST, tempyaml.getBoolean("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                }
            } else if (mat == Material.DROPPER || mat == Material.DISPENSER) {
                Dispenser type = new Dispenser(0, block.getData());
                switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.WOOD || mat == Material.LOG) {
                Tree type = new Tree(0, block.getData());
                switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".typ")) {
                    case "ACACIA":
                        type.setSpecies(TreeSpecies.ACACIA);
                        break;
                    case "BIRCH":
                        type.setSpecies(TreeSpecies.BIRCH);
                        break;
                    case "DARK_OAK":
                        type.setSpecies(TreeSpecies.DARK_OAK);
                        break;
                    case "GENERIC":
                        type.setSpecies(TreeSpecies.GENERIC);
                        break;
                    case "JUNGLE":
                        type.setSpecies(TreeSpecies.JUNGLE);
                        break;
                    case "REDWOOD":
                        type.setSpecies(TreeSpecies.REDWOOD);
                        break;
                }
                switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                    case "UP":
                        r = 8;
                        break;
                    case "DOWN":
                        r = 9;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setDirection(SOUTH);
                    block.setData(type.getData());
                } else if (r == 3 || r == 7) {
                    type.setDirection(WEST);
                    block.setData(type.getData());
                } else if (r == 8) {
                    type.setDirection(UP);
                    block.setData(type.getData());
                } else if (r == 9) {
                    type.setDirection(DOWN);
                    block.setData(type.getData());
                }
            } else if (mat == Material.DETECTOR_RAIL) {
                DetectorRail type = new DetectorRail(0, block.getData());
                switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setDirection(NORTH, tempyaml.getBoolean("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setDirection(EAST, tempyaml.getBoolean("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setDirection(SOUTH, tempyaml.getBoolean("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                } else {
                    type.setDirection(WEST, tempyaml.getBoolean("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                }
            } else if (mat == Material.PISTON_BASE || mat == Material.PISTON_STICKY_BASE) {
                PistonBaseMaterial type = new PistonBaseMaterial(0, block.getData());
                switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                    case "UP":
                        r = 8;
                        break;
                    case "DOWN":
                        r = 9;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else if (r == 3 || r == 7) {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                } else if (r == 8) {
                    type.setFacingDirection(UP);
                    block.setData(type.getData());
                } else if (r == 9) {
                    type.setFacingDirection(DOWN);
                    block.setData(type.getData());
                }
            } else if (mat == Material.TORCH || mat == Material.REDSTONE_TORCH_OFF || mat == Material.REDSTONE_TORCH_ON) {
                Torch type = new Torch(0, block.getData());
                switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
                block.getState().update(true, false);
            } else if (mat == Material.LADDER) {
                Ladder type = new Ladder(0, block.getData());
                switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.FURNACE || mat == Material.BURNING_FURNACE) {
                Furnace type = new Furnace(0, block.getData());
                switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.RAILS) {
                Rails type = new Rails(0, block.getData());
                switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setDirection(NORTH, tempyaml.getBoolean("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setDirection(EAST, tempyaml.getBoolean("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setDirection(SOUTH, tempyaml.getBoolean("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                } else {
                    type.setDirection(WEST, tempyaml.getBoolean("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".cli"));
                    block.setData(type.getData());
                }
            } else if (mat == Material.PUMPKIN || mat == Material.JACK_O_LANTERN) {
                Pumpkin type = new Pumpkin(0, block.getData());
                switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.FENCE_GATE) {
                Gate type = new Gate(0, block.getData());
                switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.BED || mat == Material.BED_BLOCK) {
                Bed type = new Bed(0, block.getData());
                if (tempyaml.getBoolean("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".bed")) {
                    type.setHeadOfBed(true);
                }
                switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.WOOD_BUTTON || mat == Material.STONE_BUTTON) {
                Button type = new Button(0, block.getData());
                switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.TRAP_DOOR || mat == Material.IRON_TRAPDOOR) {
                TrapDoor type = new TrapDoor(0, block.getData());
                if (tempyaml.getBoolean("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".ope")) {
                    type.setOpen(true);
                } else {
                    type.setOpen(false);
                }
                if (tempyaml.getBoolean("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".inv")) {
                    type.setInverted(true);
                } else {
                    type.setInverted(false);
                }
                switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.TRIPWIRE_HOOK) {
                TripwireHook type = new TripwireHook(0, block.getData());
                switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
            } else if (mat == Material.LEVER) {
                Lever type = new Lever(0, block.getData());
                switch (tempyaml.getString("Scematic." + cx + "." + cz + "." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        r += 0;
                        break;
                    case "EAST":
                        r += 1;
                        break;
                    case "SOUTH":
                        r += 2;
                        break;
                    case "WEST":
                        r += 3;
                        break;
                }
                if (r == 0 || r == 4) {
                    type.setFacingDirection(NORTH);
                    block.setData(type.getData());
                } else if (r == 1 || r == 5) {
                    type.setFacingDirection(EAST);
                    block.setData(type.getData());
                } else if (r == 2 || r == 6) {
                    type.setFacingDirection(SOUTH);
                    block.setData(type.getData());
                } else {
                    type.setFacingDirection(WEST);
                    block.setData(type.getData());
                }
            }
        }
    }

    public static boolean cont(Block block, Material mat, Block chest, FileConfiguration tempyaml, String part, String direction) {
        if (!blocks.contains(mat.toString())) {
            return !block.getType().equals(mat) && !block.equals(chest);
        }
        if (block.equals(chest)) {
            return false;
        } else if (block.getType().equals(mat)) {
            if (mat == Material.STEP) {
                Step type = new Step(0, block.getData());
                return !(tempyaml.getBoolean(part + ".dat") == type.isInverted() && tempyaml.getString(part + ".typ").equals(type.getMaterial().toString()));
            } else if (mat == Material.WOOD_STEP) {
                WoodenStep type = new WoodenStep(0, block.getData());
                return !(tempyaml.getBoolean(part + ".dat") == type.isInverted() && tempyaml.getString(part + ".typ").equals(type.getSpecies().toString()));
            } else if (mat == Material.WOOL) {
                Wool type = new Wool(0, block.getData());
                return !(tempyaml.getString(part + ".typ").equals(type.getColor().toString()));
            } else if (mat == Material.SANDSTONE) {
                Sandstone type = new Sandstone(0, block.getData());
                return !(tempyaml.getString(part + ".typ").equals(type.getType().toString()));
            } else if (mat == Material.CROPS) {
                Crops type = new Crops(0, block.getData());
                return !(tempyaml.getString(part + ".typ").equals(type.getState().toString()));
            } else {
                int r = 1;
                if (direction.equalsIgnoreCase("e")) {
                    r = 2;
                } else if (direction.equalsIgnoreCase("s")) {
                    r = 3;
                } else if (direction.equalsIgnoreCase("w")) {
                    r = 4;
                }
                if (tempyaml.getString(part + ".dat").equalsIgnoreCase("EAST")) {
                    r *= 10;
                } else if (tempyaml.getString(part + ".dat").equalsIgnoreCase("SOUTH")) {
                    r *= 20;
                } else if (tempyaml.getString(part + ".dat").equalsIgnoreCase("WEST")) {
                    r *= 30;
                } else if (tempyaml.getString(part + ".dat").equalsIgnoreCase("UP")) {
                    r = 5;
                } else if (tempyaml.getString(part + ".dat").equalsIgnoreCase("DOWN")) {
                    r = 6;
                }
                String fdir = "";
                if (r == 1 || r == 60 || r == 60 || r == 40) {
                    fdir = "NORTH";
                } else if (r == 2 || r == 90 || r == 80 || r == 10) {
                    fdir = "EAST";
                } else if (r == 3 || r == 120 || r == 20 || r == 20) {
                    fdir = "SOUTH";
                } else if (r == 4 || r == 30 || r == 40 || r == 30) {
                    fdir = "WEST";
                } else if (r == 5) {
                    fdir = "UP";
                } else if (r == 6) {
                    fdir = "DOWN";
                }
                if (mat == Material.ACACIA_STAIRS || mat == (Material.BIRCH_WOOD_STAIRS) || mat == Material.BRICK_STAIRS || mat == Material.COBBLESTONE_STAIRS || mat == Material.JUNGLE_WOOD_STAIRS || mat == Material.NETHER_BRICK_STAIRS || mat == Material.QUARTZ_STAIRS || mat == Material.SANDSTONE_STAIRS || mat == Material.SMOOTH_STAIRS || mat == Material.SPRUCE_WOOD_STAIRS || mat == Material.WOOD_STAIRS) {
                    Stairs type = new Stairs(0, block.getData());
                    return !(tempyaml.getBoolean(part + ".inv") == type.isInverted() && fdir.equals(type.getFacing().toString()));
                } else if (mat == Material.CHEST) {
                    Chest type = new Chest(0, block.getData());
                    return !(fdir.equals(type.getFacing().toString()));
                } else if (mat == Material.ACTIVATOR_RAIL || mat == Material.POWERED_RAIL) {
                    PoweredRail type = new PoweredRail(0, block.getData());
                    return !(fdir.equals(type.getDirection().toString()));
                } else if (mat == Material.DROPPER || mat == Material.DISPENSER) {
                    Dispenser type = new Dispenser(0, block.getData());
                    return !(fdir.equals(type.getFacing().toString()));
                } else if (mat == Material.WOOD || mat == Material.LOG) {
                    Tree type = new Tree(0, block.getData());
                    return !(tempyaml.getString(part + ".typ").equals(type.getSpecies().toString()) && fdir.equals(type.getDirection().toString()));
                } else if (mat == Material.DETECTOR_RAIL) {
                    DetectorRail type = new DetectorRail(0, block.getData());
                    return !(fdir.equals(type.getDirection().toString()));
                } else if (mat == Material.PISTON_BASE || mat == Material.PISTON_STICKY_BASE) {
                    PistonBaseMaterial type = new PistonBaseMaterial(0, block.getData());
                    return !(fdir.equals(type.getFacing().toString()));
                } else if (mat == Material.TORCH || mat == Material.REDSTONE_TORCH_OFF || mat == Material.REDSTONE_TORCH_ON) {
                    Torch type = new Torch(0, block.getData());
                    return !(fdir.equals(type.getFacing().toString()));
                } else if (mat == Material.LADDER) {
                    Ladder type = new Ladder(0, block.getData());
                    return !(fdir.equals(type.getFacing().toString()));
                } else if (mat == Material.FURNACE || mat == Material.BURNING_FURNACE) {
                    Furnace type = new Furnace(0, block.getData());
                    return !(fdir.equals(type.getFacing().toString()));
                } else if (mat == Material.RAILS) {
                    Rails type = new Rails(0, block.getData());
                    return !(fdir.equals(type.getDirection().toString()));
                } else if (mat == Material.PUMPKIN || mat == Material.JACK_O_LANTERN) {
                    Pumpkin type = new Pumpkin(0, block.getData());
                    return !(fdir.equals(type.getFacing().toString()));
                } else if (mat == Material.FENCE_GATE) {
                    Gate type = new Gate(0, block.getData());
                    return !(fdir.equals(type.getFacing().toString()));
                } else if (mat == Material.BED || mat == Material.BED_BLOCK) {
                    Bed type = new Bed(0, block.getData());
                    return !(fdir.equals(type.getFacing().toString()));
                } else if (mat == Material.WOOD_BUTTON || mat == Material.STONE_BUTTON) {
                    Button type = new Button(0, block.getData());
                    return !(fdir.equals(type.getFacing().toString()));
                } else if (mat == Material.TRAP_DOOR || mat == Material.IRON_TRAPDOOR) {
                    TrapDoor type = new TrapDoor(0, block.getData());
                    return !(tempyaml.getBoolean(part + ".ope") == type.isOpen() && tempyaml.getBoolean(part + ".inv") == type.isInverted() && fdir.equals(type.getFacing().toString()));
                } else if (mat == Material.TRIPWIRE_HOOK) {
                    Button type = new Button(0, block.getData());
                    return !(fdir.equals(type.getFacing().toString()));
                } else if (mat == Material.LEVER) {
                    Button type = new Button(0, block.getData());
                    return !(fdir.equals(type.getFacing().toString()));
                } else {
                    return true;
                }
            }
        } else {
            return true;
        }
    }
}
