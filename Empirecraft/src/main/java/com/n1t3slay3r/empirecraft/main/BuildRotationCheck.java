/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.n1t3slay3r.empirecraft.main;

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
import org.bukkit.material.Chest;
import org.bukkit.material.DetectorRail;
import org.bukkit.material.Dispenser;
import org.bukkit.material.Furnace;
import org.bukkit.material.Gate;
import org.bukkit.material.Ladder;
import org.bukkit.material.PistonBaseMaterial;
import org.bukkit.material.PoweredRail;
import org.bukkit.material.Pumpkin;
import org.bukkit.material.Rails;
import org.bukkit.material.Sandstone;
import org.bukkit.material.Stairs;
import org.bukkit.material.Step;
import org.bukkit.material.Torch;
import org.bukkit.material.Tree;
import org.bukkit.material.WoodenStep;
import org.bukkit.material.Wool;

/**
 *
 * @author Dylan Malec
 */
public class BuildRotationCheck {

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
        } else if (direction.equalsIgnoreCase("n")) {
            if (mat == Material.ACACIA_STAIRS || mat == (Material.BIRCH_WOOD_STAIRS) || mat == Material.BRICK_STAIRS || mat == Material.COBBLESTONE_STAIRS || mat == Material.JUNGLE_WOOD_STAIRS || mat == Material.NETHER_BRICK_STAIRS || mat == Material.QUARTZ_STAIRS || mat == Material.SANDSTONE_STAIRS || mat == Material.SMOOTH_STAIRS || mat == Material.SPRUCE_WOOD_STAIRS || mat == Material.WOOD_STAIRS) {
                Stairs type = new Stairs(0, block.getData());
                if (tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".inv")) {
                    type.setInverted(true);
                } else {
                    type.setInverted(false);
                }
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.CHEST) {
                Chest type = new Chest(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.ACTIVATOR_RAIL || mat == Material.POWERED_RAIL) {
                PoweredRail type = new PoweredRail(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setDirection(NORTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setDirection(EAST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setDirection(SOUTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setDirection(WEST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.DROPPER || mat == Material.DISPENSER) {
                Dispenser type = new Dispenser(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
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
                        type.setDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "UP":
                        type.setDirection(UP);
                        block.setData(type.getData());
                        break;
                    case "DOWN":
                        type.setDirection(DOWN);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.DETECTOR_RAIL) {
                DetectorRail type = new DetectorRail(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setDirection(NORTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setDirection(EAST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setDirection(SOUTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setDirection(WEST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.PISTON_BASE || mat == Material.PISTON_STICKY_BASE) {
                PistonBaseMaterial type = new PistonBaseMaterial(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "UP":
                        type.setFacingDirection(UP);
                        block.setData(type.getData());
                        break;
                    case "DOWN":
                        type.setFacingDirection(DOWN);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.TORCH || mat == Material.REDSTONE_TORCH_OFF || mat == Material.REDSTONE_TORCH_ON) {
                Torch type = new Torch(0, block.getData()); 
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.LADDER) {
                Ladder type = new Ladder(0, block.getData()); 
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.FURNACE || mat == Material.BURNING_FURNACE) {
                Furnace type = new Furnace(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.RAILS) {
                Rails type = new Rails(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setDirection(NORTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setDirection(EAST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setDirection(SOUTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setDirection(WEST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.PUMPKIN || mat == Material.JACK_O_LANTERN) {
                Pumpkin type = new Pumpkin(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.FENCE_GATE) {
                Gate type = new Gate(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.BED || mat == Material.BED_BLOCK) {
                Bed type = new Bed(0, block.getData());
                if (tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".bed")) {
                    type.setHeadOfBed(true);
                }
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                }
            }
        } else if (direction.equalsIgnoreCase("e")) {
            if (mat == Material.ACACIA_STAIRS || mat == (Material.BIRCH_WOOD_STAIRS) || mat == Material.BRICK_STAIRS || mat == Material.COBBLESTONE_STAIRS || mat == Material.JUNGLE_WOOD_STAIRS || mat == Material.NETHER_BRICK_STAIRS || mat == Material.QUARTZ_STAIRS || mat == Material.SANDSTONE_STAIRS || mat == Material.SMOOTH_STAIRS || mat == Material.SPRUCE_WOOD_STAIRS || mat == Material.WOOD_STAIRS) {
                Stairs type = new Stairs(0, block.getData());
                if (tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".inv")) {
                    type.setInverted(true);
                } else {
                    type.setInverted(false);
                }
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.CHEST) {
                Chest type = new Chest(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.ACTIVATOR_RAIL || mat == Material.POWERED_RAIL) {
                PoweredRail type = new PoweredRail(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setDirection(EAST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setDirection(SOUTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setDirection(WEST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setDirection(NORTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.DROPPER || mat == Material.DISPENSER) {
                Dispenser type = new Dispenser(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
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
                        type.setDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "UP":
                        type.setDirection(UP);
                        block.setData(type.getData());
                        break;
                    case "DOWN":
                        type.setDirection(DOWN);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.DETECTOR_RAIL) {
                DetectorRail type = new DetectorRail(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setDirection(EAST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setDirection(SOUTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setDirection(WEST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setDirection(NORTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.PISTON_BASE || mat == Material.PISTON_STICKY_BASE) {
                PistonBaseMaterial type = new PistonBaseMaterial(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "UP":
                        type.setFacingDirection(UP);
                        block.setData(type.getData());
                        break;
                    case "DOWN":
                        type.setFacingDirection(DOWN);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.TORCH || mat == Material.REDSTONE_TORCH_OFF || mat == Material.REDSTONE_TORCH_ON) {
                Torch type = new Torch(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.LADDER) {
                Ladder type = new Ladder(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.FURNACE || mat == Material.BURNING_FURNACE) {
                Furnace type = new Furnace(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.RAILS) {
                Rails type = new Rails(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setDirection(EAST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setDirection(SOUTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setDirection(WEST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setDirection(NORTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.PUMPKIN || mat == Material.JACK_O_LANTERN) {
                Pumpkin type = new Pumpkin(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.FENCE_GATE) {
                Gate type = new Gate(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.BED || mat == Material.BED_BLOCK) {
                Bed type = new Bed(0, block.getData());
                if (tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".bed")) {
                    type.setHeadOfBed(true);
                } else {
                    type.setHeadOfBed(false);
                }
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                }
            }
        } else if (direction.equalsIgnoreCase("s")) {
            if (mat == Material.ACACIA_STAIRS || mat == (Material.BIRCH_WOOD_STAIRS) || mat == Material.BRICK_STAIRS || mat == Material.COBBLESTONE_STAIRS || mat == Material.JUNGLE_WOOD_STAIRS || mat == Material.NETHER_BRICK_STAIRS || mat == Material.QUARTZ_STAIRS || mat == Material.SANDSTONE_STAIRS || mat == Material.SMOOTH_STAIRS || mat == Material.SPRUCE_WOOD_STAIRS || mat == Material.WOOD_STAIRS) {
                Stairs type = new Stairs(0, block.getData());
                if (tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".inv")) {
                    type.setInverted(true);
                } else {
                    type.setInverted(false);
                }
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.CHEST) {
                Chest type = new Chest(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.ACTIVATOR_RAIL || mat == Material.POWERED_RAIL) {
                PoweredRail type = new PoweredRail(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setDirection(SOUTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setDirection(WEST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setDirection(NORTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setDirection(EAST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.DROPPER || mat == Material.DISPENSER) {
                Dispenser type = new Dispenser(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
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
                        type.setDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "UP":
                        type.setDirection(UP);
                        block.setData(type.getData());
                        break;
                    case "DOWN":
                        type.setDirection(DOWN);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.DETECTOR_RAIL) {
                DetectorRail type = new DetectorRail(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setDirection(SOUTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setDirection(WEST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setDirection(NORTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setDirection(EAST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.PISTON_BASE || mat == Material.PISTON_STICKY_BASE) {
                PistonBaseMaterial type = new PistonBaseMaterial(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "UP":
                        type.setFacingDirection(UP);
                        block.setData(type.getData());
                        break;
                    case "DOWN":
                        type.setFacingDirection(DOWN);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.TORCH || mat == Material.REDSTONE_TORCH_OFF || mat == Material.REDSTONE_TORCH_ON) {
                Torch type = new Torch(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.LADDER) {
                Ladder type = new Ladder(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.FURNACE || mat == Material.BURNING_FURNACE) {
                Furnace type = new Furnace(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.RAILS) {
                Rails type = new Rails(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setDirection(SOUTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setDirection(WEST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setDirection(NORTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setDirection(EAST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.PUMPKIN || mat == Material.JACK_O_LANTERN) {
                Pumpkin type = new Pumpkin(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.FENCE_GATE) {
                Gate type = new Gate(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.BED || mat == Material.BED_BLOCK) {
                Bed type = new Bed(0, block.getData());
                if (tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".bed")) {
                    type.setHeadOfBed(true);
                } else {
                    type.setHeadOfBed(false);
                }
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                }
            }
        } else {
            if (mat == Material.ACACIA_STAIRS || mat == (Material.BIRCH_WOOD_STAIRS) || mat == Material.BRICK_STAIRS || mat == Material.COBBLESTONE_STAIRS || mat == Material.JUNGLE_WOOD_STAIRS || mat == Material.NETHER_BRICK_STAIRS || mat == Material.QUARTZ_STAIRS || mat == Material.SANDSTONE_STAIRS || mat == Material.SMOOTH_STAIRS || mat == Material.SPRUCE_WOOD_STAIRS || mat == Material.WOOD_STAIRS) {
                Stairs type = new Stairs(0, block.getData());
                if (tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".inv")) {
                    type.setInverted(true);
                } else {
                    type.setInverted(false);
                }
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.CHEST) {
                Chest type = new Chest(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.ACTIVATOR_RAIL || mat == Material.POWERED_RAIL) {
                PoweredRail type = new PoweredRail(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setDirection(WEST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setDirection(SOUTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setDirection(EAST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setDirection(NORTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.DROPPER || mat == Material.DISPENSER) {
                Dispenser type = new Dispenser(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
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
                        type.setDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "UP":
                        type.setDirection(UP);
                        block.setData(type.getData());
                        break;
                    case "DOWN":
                        type.setDirection(DOWN);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.DETECTOR_RAIL) {
                DetectorRail type = new DetectorRail(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setDirection(WEST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setDirection(SOUTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setDirection(EAST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setDirection(NORTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.PISTON_BASE || mat == Material.PISTON_STICKY_BASE) {
                PistonBaseMaterial type = new PistonBaseMaterial(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "UP":
                        type.setFacingDirection(UP);
                        block.setData(type.getData());
                        break;
                    case "DOWN":
                        type.setFacingDirection(DOWN);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.TORCH || mat == Material.REDSTONE_TORCH_OFF || mat == Material.REDSTONE_TORCH_ON) {
                Torch type = new Torch(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.LADDER) {
                Ladder type = new Ladder(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.FURNACE || mat == Material.BURNING_FURNACE) {
                Furnace type = new Furnace(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.RAILS) {
                Rails type = new Rails(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setDirection(WEST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setDirection(SOUTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setDirection(EAST, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setDirection(NORTH, tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".cli"));
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.PUMPKIN || mat == Material.JACK_O_LANTERN) {
                Pumpkin type = new Pumpkin(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.FENCE_GATE) {
                Gate type = new Gate(0, block.getData());
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                }
            } else if (mat == Material.BED || mat == Material.BED_BLOCK) {
                Bed type = new Bed(0, block.getData());
                if (tempyaml.getBoolean("Scematic." + cy + "." + x + "." + z + ".bed")) {
                    type.setHeadOfBed(true);
                } else {
                    type.setHeadOfBed(false);
                }
                switch (tempyaml.getString("Scematic." + cy + "." + x + "." + z + ".dat")) {
                    case "NORTH":
                        type.setFacingDirection(WEST);
                        block.setData(type.getData());
                        break;
                    case "EAST":
                        type.setFacingDirection(SOUTH);
                        block.setData(type.getData());
                        break;
                    case "SOUTH":
                        type.setFacingDirection(EAST);
                        block.setData(type.getData());
                        break;
                    case "WEST":
                        type.setFacingDirection(NORTH);
                        block.setData(type.getData());
                        break;
                }
            }
        }
    }
}
