package com.demo.flowchart.util;

import com.demo.flowchart.drawing.model.DecisionDrawingBlock;
import com.demo.flowchart.drawing.model.DrawingBlock;
import com.demo.flowchart.drawing.model.DrawingFlowline;
import com.demo.flowchart.drawing.model.IODrawingBlock;
import com.demo.flowchart.drawing.model.PredefinedProcessDrawingBlock;
import com.demo.flowchart.drawing.model.ProcessDrawingBlock;
import com.demo.flowchart.drawing.model.TerminalDrawingBlock;
import com.demo.flowchart.model.Block;
import com.demo.flowchart.model.BlockType;
import com.demo.flowchart.model.Flowline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Converters {

    public static Map<Long, DrawingBlock> blocksToDrawingBlocks(List<Block> blocks) {
        Map<Long, DrawingBlock> drawingBlockMap = new HashMap<>();
        for (Block block : blocks) {
            drawingBlockMap.put(block.getId(), blockToDrawingBlock(block));
        }
        for (Block block : blocks) {
            drawingBlockMap.get(block.getId()).setFlowlines(
                    flowlinesToDrawingFlowlines(block.getFlowlines(), drawingBlockMap)
            );
        }
        return drawingBlockMap;
    }

    public static List<Block> drawingBlocksToBlocks(Map<Long, DrawingBlock> drawingBlockMap) {
        List<Block> blocks = new ArrayList<>();
        for (DrawingBlock drawingBlock : drawingBlockMap.values()) {
            blocks.add(drawingBlockToBlock(drawingBlock));
        }
        return blocks;
    }

    private static DrawingBlock blockToDrawingBlock(Block block) {
        DrawingBlock drawingBlock = null;

        long id = block.getId();
        BlockType type = block.getType();
        int startX = block.getStartX();
        int startY = block.getStartY();
        int width = block.getWidth();
        int height = block.getHeight();
        String text = block.getText();

        switch (type) {
            case TERMINAL: {
                drawingBlock = new TerminalDrawingBlock(id, startX, startY, width, height, text);
                break;
            }
            case PROCESS: {
                drawingBlock = new ProcessDrawingBlock(id, startX, startY, width, height, text);
                break;
            }
            case PREDEFINED_PROCESS: {
                drawingBlock = new PredefinedProcessDrawingBlock(id, startX, startY, width, height, text);
                break;
            }
            case DECISION: {
                drawingBlock = new DecisionDrawingBlock(id, startX, startY, width, height, text);
                break;
            }
            case IO: {
                drawingBlock = new IODrawingBlock(id, startX, startY, width, height, text);
                break;
            }
        }

        return drawingBlock;
    }

    private static Block drawingBlockToBlock(DrawingBlock drawingBlock) {
        BlockType type = null;
        if (drawingBlock instanceof TerminalDrawingBlock) {
            type = BlockType.TERMINAL;
        } else if (drawingBlock instanceof PredefinedProcessDrawingBlock) {
            type = BlockType.PREDEFINED_PROCESS;
        } else if (drawingBlock instanceof ProcessDrawingBlock) {
            type = BlockType.PROCESS;
        } else if (drawingBlock instanceof DecisionDrawingBlock) {
            type = BlockType.DECISION;
        } else if (drawingBlock instanceof IODrawingBlock) {
            type = BlockType.IO;
        }


        return new Block(
                drawingBlock.getId(),
                type,
                drawingBlock.getStartX(),
                drawingBlock.getStartY(),
                drawingBlock.getWidth(),
                drawingBlock.getHeight(),
                drawingBlock.getText(),
                drawingFlowlinesToFlowlines(drawingBlock.getFlowlines())
        );
    }

    private static Flowline[] drawingFlowlinesToFlowlines(DrawingFlowline[] drawingFlowlines) {
        Flowline[] flowlines = new Flowline[drawingFlowlines.length];
        for (int i = 0; i < flowlines.length; i++) {
            if (drawingFlowlines[i] != null) {
                flowlines[i] = new Flowline(
                        drawingFlowlines[i].getStartDrawingBlock().getId(),
                        drawingFlowlines[i].getEndDrawingBlock().getId(),
                        drawingFlowlines[i].getDecision()
                );
            }
        }
        return flowlines;
    }

    private static DrawingFlowline[] flowlinesToDrawingFlowlines(
            Flowline[] flowlines, Map<Long, DrawingBlock> drawingBlockMap
    ) {
        DrawingFlowline[] drawingFlowlines = new DrawingFlowline[flowlines.length];
        for (int i = 0; i < flowlines.length; i++) {
            if (flowlines[i] != null) {
                drawingFlowlines[i] = new DrawingFlowline(
                        drawingBlockMap.get(flowlines[i].getStartBlockId()),
                        drawingBlockMap.get(flowlines[i].getEndBlockId()),
                        flowlines[i].getDecision()
                );
            }
        }
        return drawingFlowlines;
    }
}
