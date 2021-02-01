package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.ParentBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.BinaryChoiceParameter;

@Category(Category.CONTROLS)
@Description("Runs code if the specified condition is true.")
public class StatIfStatement extends ParentBlock {

    private BinaryChoiceParameter booleanChoice = new BinaryChoiceParameter("true", "false");

    public StatIfStatement() {
        init("if ", boolean.class, " is ", booleanChoice);
    }

    @Override
    public String toJava() {
        return booleanChoice.isFirst() ?
                "if (" + arg(0) + ") {" + getChildJava() + "}" :
                "if (!" + arg(0) + ") {" + getChildJava() + "}";
    }
}
