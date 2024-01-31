package com.github.theword.parse;

import com.github.theword.returnBody.returnModle.MyBaseComponent;
import com.github.theword.returnBody.returnModle.MyTextComponent;
import net.minecraft.text.*;
import net.minecraft.util.Identifier;

import java.util.List;

public class ParseJsonToEvent {

    public static MutableText parseMessages(List<? extends MyBaseComponent> myBaseComponentList) {
        MutableText mutableText = parsePerMessageToMultiText(myBaseComponentList.get(0));
        for (int i = 1; i < myBaseComponentList.size(); i++) {
            mutableText.append(parsePerMessageToMultiText(myBaseComponentList.get(i)));
        }
        return mutableText;
    }

    public static MutableText parsePerMessageToMultiText(MyBaseComponent myBaseComponent) {
        LiteralTextContent literalTextContent = new LiteralTextContent(myBaseComponent.getText());
        Identifier identifier = null;
        if (myBaseComponent.getFont() != null) {
            identifier = new Identifier(Identifier.DEFAULT_NAMESPACE, myBaseComponent.getFont());
        }

        Style style = Style.EMPTY.
                withColor(TextColor.parse(myBaseComponent.getColor()))
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
                ClickEvent.Action tempAction = ClickEvent.Action.byName(myTextComponent.getClickEvent().getAction());
                ClickEvent clickEvent = new ClickEvent(tempAction, myTextComponent.getClickEvent().getValue());
                style.withClickEvent(clickEvent);
            }
            // TODO 悬浮事件待完善
            if (myTextComponent.getHoverEvent() != null) {
                HoverEvent hoverEvent = null;
                switch (myTextComponent.getHoverEvent().getAction()) {
                    case "show_text":
                        MutableText textComponent = parseMessages(myTextComponent.getHoverEvent().getBaseComponentList());
                        hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, textComponent);
                        break;
                    case "show_item":
//                            hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_ITEM, new Item());
                        break;
                    case "show_entity":
//                            hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new Entity());
                        break;
                    default:
                        break;
                }
                style.withHoverEvent(hoverEvent);
            }
        }
        MutableText tempMutableText = MutableText.of(literalTextContent);
        tempMutableText.setStyle(style);
        return tempMutableText;
    }
}
