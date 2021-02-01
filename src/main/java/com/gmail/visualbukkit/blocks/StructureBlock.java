package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.annotations.BlockColor;
import com.gmail.visualbukkit.blocks.annotations.Category;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

@Category(Category.STRUCTURES)
@BlockColor("#b460ebff")
public abstract class StructureBlock extends StatementBlock {

    public StructureBlock() {
        normalBackground = new Background(new BackgroundFill(getDefinition().getBlockColor(), new CornerRadii(20, 20, 10, 10, false), Insets.EMPTY));
        normalBorder = new Border(new BorderStroke(getDefinition().getBlockColor().darker(), BorderStrokeStyle.SOLID, new CornerRadii(20, 20, 10, 10, false), BorderStroke.THIN));
        invalidatedBackground = new Background(new BackgroundFill(Color.RED, new CornerRadii(20, 20, 10, 10, false), Insets.EMPTY));
        /*highlightedBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20, 20, 10, 10, false), new BorderWidths(2)), new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, new CornerRadii(20, 20, 10, 10, false), new BorderWidths(1)));*/
        highlightedBorder = new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, new CornerRadii(20, 20, 10, 10, false), BorderStroke.THIN));
        syntaxBox.setPadding(new Insets(3));
        syntaxBox.setBorder(normalBorder);
        syntaxBox.setBackground(normalBackground);
    }

    @Override
    public final String toJava() {
        return "";
    }

    public String getChildJava() {
        StringBuilder builder = new StringBuilder();
        StatementBlock child = next;
        while (child != null) {
            builder.append(child.toJava());
            child = child.getNext();
        }
        return builder.toString();
    }

    @Override
    public StructureBlock getStructure() {
        return this;
    }
}
