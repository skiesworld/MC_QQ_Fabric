package com.github.theword.mcqq.utils;

import com.github.theword.mcqq.returnBody.returnModle.MyBaseComponent;
import com.github.theword.mcqq.returnBody.returnModle.MyHoverEntity;
import com.github.theword.mcqq.returnBody.returnModle.MyHoverItem;
import com.github.theword.mcqq.returnBody.returnModle.MyTextComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ParseJsonToEvent {

    public MutableText parseMessages(List<? extends MyBaseComponent> myBaseComponentList) {
        MutableText mutableText = parsePerMessageToMultiText(myBaseComponentList.getFirst());
        for (int i = 1; i < myBaseComponentList.size(); i++) {
            mutableText.append(parsePerMessageToMultiText(myBaseComponentList.get(i)));
        }
        return mutableText;
    }

    public MutableText parsePerMessageToMultiText(MyBaseComponent myBaseComponent) {
        MutableText mutableText = Text.literal(myBaseComponent.getText());
        Identifier identifier = null;
        if (myBaseComponent.getFont() != null) {
            identifier = Identifier.of(myBaseComponent.getFont());
        }

        Style style = Style.EMPTY.
                withColor(Formatting.byName(myBaseComponent.getColor()))
                .withBold(myBaseComponent.isBold())
                .withItalic(myBaseComponent.isItalic())
                .withUnderline(myBaseComponent.isUnderlined())
                .withStrikethrough(myBaseComponent.isStrikethrough())
                .withObfuscated(myBaseComponent.isObfuscated())
                .withInsertion(myBaseComponent.getInsertion())
                .withFont(identifier);


        // 配置 TextComponent 额外属性
        if (myBaseComponent instanceof MyTextComponent myTextComponent) {
            if (myTextComponent.getClickEvent() != null) {
                ClickEvent clickEvent = getClickEvent(myTextComponent);
                style.withClickEvent(clickEvent);
            }
            if (myTextComponent.getHoverEvent() != null) {
                HoverEvent hoverEvent = null;
                switch (myTextComponent.getHoverEvent().getAction()) {
                    case "show_text" -> {
                        if (myTextComponent.getHoverEvent().getBaseComponentList() != null && !myTextComponent.getHoverEvent().getBaseComponentList().isEmpty()) {
                            MutableText textComponent = parseMessages(myTextComponent.getHoverEvent().getBaseComponentList());
                            hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, textComponent);
                        }
                    }
                    case "show_item" -> {
                        MyHoverItem myHoverItem = myTextComponent.getHoverEvent().getItem();
                        Item item = Item.byRawId(myHoverItem.getId());
                        ItemStack itemStack = new ItemStack(item, myHoverItem.getCount());
                        HoverEvent.ItemStackContent itemStackContent = new HoverEvent.ItemStackContent(itemStack);
                        hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_ITEM, itemStackContent);
                    }
                    case "show_entity" -> {
                        MyHoverEntity myHoverEntity = myTextComponent.getHoverEvent().getEntity();
                        Optional<EntityType<?>> entityType = EntityType.get(myHoverEntity.getType());
                        if (entityType.isPresent()) {
                            MutableText tempHoverText = Text.literal(myHoverEntity.getName().getFirst().getText());
                            for (int i = 1; i < myHoverEntity.getName().size(); i++) {
                                tempHoverText.append(parsePerMessageToMultiText(myHoverEntity.getName().get(i)));
                            }
                            HoverEvent.EntityContent entityTooltipInfo = new HoverEvent.EntityContent(entityType.get(), UUID.randomUUID(), tempHoverText);
                            hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_ENTITY, entityTooltipInfo);
                        }
                    }
                    default -> {
                    }
                }
                style.withHoverEvent(hoverEvent);
            }
        }
        mutableText.setStyle(style);
        return mutableText;
    }

    @NotNull
    private ClickEvent getClickEvent(MyTextComponent myTextComponent) {
        ClickEvent.Action tempAction;
        switch (myTextComponent.getClickEvent().getAction()) {
            case "open_url" -> tempAction = ClickEvent.Action.OPEN_URL;
            case "open_file" -> tempAction = ClickEvent.Action.OPEN_FILE;
            case "run_command" -> tempAction = ClickEvent.Action.RUN_COMMAND;
            case "suggest_command" -> tempAction = ClickEvent.Action.SUGGEST_COMMAND;
            case "change_page" -> tempAction = ClickEvent.Action.CHANGE_PAGE;
            case "copy_to_clipboard" -> tempAction = ClickEvent.Action.COPY_TO_CLIPBOARD;
            default -> tempAction = null;
        }
        return new ClickEvent(tempAction, myTextComponent.getClickEvent().getValue());
    }
}
