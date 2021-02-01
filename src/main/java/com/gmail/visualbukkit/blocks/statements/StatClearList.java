package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.util.List;

@Description("Removes all elements from a specified list.")
public class StatClearList extends StatementBlock {

    public StatClearList() {
        init("clear ", List.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".clear();";
    }
}
