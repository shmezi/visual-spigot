package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.ParentBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.BinaryChoiceParameter;

@Category(Category.CONTROLS)
@Description("Repeats code while specified condition remains true/false. \n\nWarning: This block will halt the server until loop is no longer active.")
public class StatWhileLoop extends ParentBlock {

    private BinaryChoiceParameter booleanChoice = new BinaryChoiceParameter("true", "false");

    public StatWhileLoop() {
        init("while ", boolean.class, " is ", booleanChoice);
    }

    @Override
    public String toJava() {
        return booleanChoice.isFirst() ?
                "while (" + arg(0) + ") {" + getChildJava() + "}" :
                "while (!" + arg(0) + ") {" + getChildJava() + "}";
    }
}
