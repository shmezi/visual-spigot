package com.gmail.visualbukkit.gui;

import com.gmail.visualbukkit.VisualBukkit;
import com.gmail.visualbukkit.blocks.BlockRegistry;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.StatementDefinition;
import com.gmail.visualbukkit.blocks.StatementLabel;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.util.CenteredHBox;
import com.gmail.visualbukkit.util.TreeNode;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.json.JSONArray;

import javax.swing.*;
import java.util.*;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER;

public class BlockSelector extends TabPane {

    private Map<String, CategoryTab> categoryTabs = new HashMap<>();
    private Set<StatementDefinition<?>> favoriteStatements = new TreeSet<>();
    private TreeNode favoriteTree = new TreeNode("Favorites");

    public BlockSelector() {
        setSide(Side.LEFT);
        setStyle("-fx-background-color: rgb(255,0,0)");
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> ((CategoryTab) newValue).content.getChildren().add(1, favoriteTree));
        setStyle("-fx-border-width: 0 1 0 0; -fx-border-color: #000;");

        setOnDragOver(e -> {
            Object source = e.getGestureSource();
            if (source instanceof StatementBlock) {
                e.acceptTransferModes(TransferMode.ANY);
            }
            e.consume();
        });

        setOnDragDropped(e -> {
            UndoManager.run(new UndoManager.RevertableAction() {
                UndoManager.RevertableAction disconnectAction;
                @Override
                public void run() {
                    disconnectAction = ((StatementBlock) e.getGestureSource()).disconnect();
                }
                @Override
                public void revert() {
                    disconnectAction.revert();
                }
            });
            e.setDropCompleted(true);
            e.consume();
        });

        setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                VisualBukkit.getInstance().getElementInspector().uninspect();
                e.consume();
            }
        });

        categoryTabs.put(Category.STATEMENTS, createTab(Category.STATEMENTS));
    }

    public void loadFavorites() {
        JSONArray favoriteArray = VisualBukkit.getDataFile().getJson().optJSONArray("favorites");
        if (favoriteArray != null) {
            for (Object obj : favoriteArray) {
                if (obj instanceof String) {
                    StatementDefinition<?> statement = BlockRegistry.getStatement((String) obj);
                    if (statement != null) {
                        favoriteStatements.add(statement);
                    }
                }
            }
        }
        updateFavorites();
    }

    public void add(StatementDefinition<?> statement) {
        for (String category : statement.getCategories()) {
            CategoryTab tab = categoryTabs.computeIfAbsent(category, this::createTab);
            StatementLabel label = new StatementLabel(statement);
            tab.add(label);
            ContextMenu contextMenu = new ContextMenu();
            label.setContextMenu(contextMenu);
            MenuItem favoriteItem = new MenuItem("Favorite");
            contextMenu.getItems().add(favoriteItem);
            favoriteItem.setOnAction(e -> {
                favoriteStatements.add(statement);
                saveFavorites();
                updateFavorites();
            });
        }
    }

    private CategoryTab createTab(String category) {
        CategoryTab tab = new CategoryTab(category);
        int i = 0;
        while (i < getTabs().size() && tab.getText().compareTo(getTabs().get(i).getText()) > 0) {
            i++;
        }
        getTabs().add(i, tab);
        return tab;
    }

    private void updateFavorites() {
        favoriteTree.clear();
        for (StatementDefinition<?> statement : favoriteStatements) {
            StatementLabel label = new StatementLabel(statement);
            favoriteTree.add(label);
            ContextMenu contextMenu = new ContextMenu();
            label.setContextMenu(contextMenu);
            MenuItem removeItem = new MenuItem("Remove Favorite");
            contextMenu.getItems().add(removeItem);
            removeItem.setOnAction(e -> {
                favoriteStatements.remove(statement);
                saveFavorites();
                updateFavorites();
            });
        }
    }

    private void saveFavorites() {
        JSONArray favoriteArray = new JSONArray();
        favoriteStatements.stream().map(BlockRegistry::getIdentifier).forEach(favoriteArray::put);
        VisualBukkit.getDataFile().getJson().put("favorites", favoriteArray);
    }

    private static class CategoryTab extends Tab {

        private VBox content = new VBox(15);
        private VBox labelBox = new VBox(10);
        private Set<StatementLabel> labels = new HashSet<>();

        public CategoryTab(String category) {
            super(category);

            Label titleLabel = new Label(category);
            titleLabel.setUnderline(false);
            titleLabel.setScaleX(1.5);
            titleLabel.setScaleY(1.5);
            titleLabel.setTranslateX(15);

            TextField searchField = new TextField();
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                String search = searchField.getText().toLowerCase();
                for (StatementLabel label : labels) {
                    boolean state = label.getText().toLowerCase().contains(search);
                    label.setVisible(state);
                    label.setManaged(state);
                }
            });

            ScrollPane scrollPane = new ScrollPane(labelBox);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);

            content.getChildren().addAll(new VBox(10, titleLabel, new CenteredHBox(5, new Label("Search"), searchField)), scrollPane);
            content.setPadding(new Insets(10, 0, 10, 10));
            scrollPane.getContent().setStyle("-fx-border-radius: 15; -fx-background-radius: 15;");
            setContent(content);
        }

        public void add(StatementLabel label) {
            labels.add(label);
            int i = 0;
            while (i < labelBox.getChildren().size() && label.getText().compareTo(((StatementLabel) labelBox.getChildren().get(i)).getText()) > 0) {
                i++;
            }
            labelBox.getChildren().add(i, label);
        }
    }
}
