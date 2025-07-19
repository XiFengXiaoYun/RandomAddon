package com.xifeng.apoth_addon.champion;

import c4.champions.common.affix.IAffix;
import c4.champions.common.affix.core.AffixCategory;
import stanhebben.zenscript.annotations.ZenGetter;

public class CustomAffixRepresentation {
    private final IAffix affix;
    public CustomAffixRepresentation(IAffix affix) {
        this.affix = affix;
    }
    @ZenGetter
    String getId() {
        return affix.getIdentifier();
    }

    @ZenGetter
    int getTier() {
        return affix.getTier();
    }

    @ZenGetter
    AffixCategory getCategory() {
        return affix.getCategory();
    }
}
