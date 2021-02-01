package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.ParentBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Category(Category.CONTROLS)
@Description("Runs code if the previous 'If' or 'Else If' statements are false.")
public class StatElseStatement extends ParentBlock {

    public StatElseStatement() {
        init("else");
    }

    @Override
    public void update() {
        super.update();
        if (hasPrevious() && !(previous instanceof StatIfStatement) && !(previous instanceof StatElseIfStatement)) {
            setInvalid("'else' must be placed directly after an 'if' or 'else if'");
        }
    }

    @Override
    public String toJava() {
        return "else {" + getChildJava() + "}";
    }
}
