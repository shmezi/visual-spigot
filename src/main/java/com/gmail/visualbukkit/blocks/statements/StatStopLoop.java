package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;

@Name("Break")
@Description("Stops an actively running loop. \n\nWarning: This must be used inside a loop to work.")
public class StatStopLoop extends StatementBlock {

    public StatStopLoop() {
        init("break");
    }

    @Override
    public void update() {
        super.update();
        validateParent("Stop loop must be used in a loop", StatNumberLoop.class, StatListLoop.class, StatWhileLoop.class);
    }

    @Override
    public String toJava() {
        return "break;";
    }
}
