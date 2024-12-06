package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.codewizz.gfx.gui.elements.UIIconButton;
import dev.codewizz.gfx.gui.elements.UIImageButton;
import dev.codewizz.gfx.gui.elements.UILabel;
import dev.codewizz.gfx.gui.elements.UITextField;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.gfx.gui.layers.Layer;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.items.Item;

public class SettlementMenu extends Menu implements IUpdateDataMenu {

    private TextField searchField;
    private InputListener clickListener;
    private InputListener escapeKeyListener;

    private Table list;
    private Table left;

    private Table right;
    private Item show;
    private UILabel showName;
    private UILabel showAmount;

    public SettlementMenu(Stage stage, GameLayer layer) {
        super(stage, layer);
    }

    @Override
    protected void setup() {
        Table main = new Table();
        base.add(main).expand().fill().size(850 * (Layer.scale / 2f), 478 * (Layer.scale / 2f));
        main.setBackground(new Image(Assets.getSprite("settlement-menu")).getDrawable());

        Table top = new Table();
        main.add(top).expandX().fillX().top().height(22 * (Layer.scale / 2f)).padTop(2).colspan(2);
        main.row();

        Label title = UILabel.create("Settlement Menu");
        top.add(title).expandX().left().padLeft(10).padBottom(4);

        Button closeButton = UIIconButton.create("close-icon", UIIconButton.smallStyle);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                close();
            }
        });
        top.add(closeButton).expandX().right()
                .size(22 * (Layer.scale / 2f), 24 * (Layer.scale / 2f)).padRight(1);

        left = new Table();
        main.add(left).expand().fillY().left().width(Value.percentWidth(0.787f, main));

        Table search = new Table();
        left.add(search).expandX().fillX().top().height(22 * (Layer.scale / 2f)).padTop(2);
        left.row();

        searchField = UITextField.create("Name . . .");
        search.add(searchField).expand().left().fillY().width(Value.percentWidth(0.36f, search))
                .padLeft(7 * Layer.scale / 2f);

        list = new Table();

        // Create a ScrollPane and add the list table to it
        ScrollPane scrollPane = new ScrollPane(list);
        scrollPane.setScrollingDisabled(true,
                false); // Disable horizontal scrolling, enable vertical scrolling
        scrollPane.setFadeScrollBars(false); // Optional: disable fade effect on scrollbars
        scrollPane.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                stage.setScrollFocus(scrollPane);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                stage.setScrollFocus(null);
            }
        });


        left.add(scrollPane).expand().fill().padLeft(4 * (Layer.scale / 2f))
                .padTop(2 * (Layer.scale / 2f)).padBottom(4 * (Layer.scale / 2f));

        right = new Table();
        main.add(right).expand().fill().right().width(Value.percentWidth(0.213f, main));

        right.top();

        showName = UILabel.create("", UILabel.defaultStyle);
        right.add(showName).center().top();
        right.row();

        showAmount = UILabel.create("", UILabel.smallStyle);
        right.add(showAmount).center().top();
        right.row();

        clickListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Actor target = event.getTarget();
                if (!(target instanceof TextField)) {
                    stage.setKeyboardFocus(null);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        };

        escapeKeyListener = new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    stage.setKeyboardFocus(null);
                    stage.setScrollFocus(null);
                    return false;
                }
                populateListTable();
                return false;
            }
        };
    }

    @Override
    public void onOpen() {
        stage.addListener(clickListener);
        stage.addListener(escapeKeyListener);

        updateData();
    }

    @Override
    public void onClose() {
        stage.removeListener(clickListener);
        stage.removeListener(escapeKeyListener);
        stage.setKeyboardFocus(null);
        stage.setScrollFocus(null);

        searchField.setText("");
        right.setVisible(false);
    }

    private void populateListTable() {
        list.clear();
        list.top().left();

        int amount = 4;
        float size = (left.getWidth() - 4 * (Layer.scale / 2f)) / amount;

        int i = 0;

        for (Item item : Main.inst.world.settlement.inventory.getItems().values()) {
            if (searchField.getText().isBlank() || item.item.getName().toLowerCase().contains(searchField.getText().toLowerCase())) {
                i++;

                Table card = new Table();
                list.add(card).size(size, size / 2).left().top();

                ImageButton button = UIImageButton.create(UIImageButton.buySlotStyle, item.item.getSprite());
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        show = item;
                        showItem();
                        right.setVisible(true);
                    }
                });

                // Create a Stack to hold the button and the text
                Stack stack = new Stack();
                stack.add(button);

                // Create a table for the text labels with padding
                Table textTable = new Table();
                textTable.setFillParent(true);

                // Add the hermit's name with padding
                UILabel nameLabel = UILabel.create(item.item.getName(), UILabel.mediumStyle);
                nameLabel.setTouchable(Touchable.disabled);  // Disable input for label
                textTable.add(nameLabel).expandX().right().top().pad(0, 0, 0, 10);
                textTable.row();

                UILabel amountLabel = UILabel.create("" + item.size, UILabel.smallStyle);
                amountLabel.setTouchable(Touchable.disabled);
                textTable.add(amountLabel).expand().right().bottom().pad(0, 0, 10, 10);

                stack.add(textTable);

                card.add(stack).expand().fill();

                if (i % amount == 0) {
                    list.row();
                }
            }
        }
    }



    private void showItem() {
        if (show != null) {
            showName.setText(show.item.getName());
            showAmount.setText("" + show.size);
        }
    }

    @Override
    public void updateData() {
        showItem();
        populateListTable();
    }
}
